package controllers;

import java.util.ArrayList;

import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.sceneManagement.AbstractScene;

import view.elements.AbstractElement;
import view.elements.RetrievedElement;
import view.elements.SuggestedElement;
import view.elements.actions.CreatedElementPreDrawAction;
import view.elements.actions.ElementPreDrawAction;
import view.elements.actions.RelatedElementPreDrawAction;
import view.elements.actions.UnrelatedElementPreDrawAction;
import view.elements.observers.SuggestedElementBirthObserver;
import view.layers.PannLayer;
import view.layers.WidgetLayer;
import view.widgets.actions.WidgetDistancePreDrawAction;
import view.widgets.custom.InformationWidget;
import view.widgets.custom.OrbWidget;
import view.widgets.facets.KeywordWidget;
import view.widgets.facets.TimelineWidget;
import bookshelf.apis.google.GoogleBookProcessor;
import bookshelf.exceptions.BookshelfUnavailableException;

public class SuggestableScene extends AbstractScene {
	private final BookshelfController controller = new BookshelfController();
	private final ArrayList<RetrievedElement> retrievedElements = new ArrayList<RetrievedElement>();
	private final ArrayList<SuggestedElement> suggestedElements = new ArrayList<SuggestedElement>();
	private final ArrayList<ElementPreDrawAction> associatedActions = new ArrayList<ElementPreDrawAction>();
	
	private KeywordWidget keywordWidget;
	private TimelineWidget timelineWidget;
	
	public WidgetLayer widgetLayer = new WidgetLayer(this);
	
	public SuggestableScene(SuggestableApplication application) {
		super(application, "Suggestable Scene");
	}
	
	@Override
	public void init() {
		MTBackgroundImage background = 
			new MTBackgroundImage(getMTApplication(), getMTApplication().loadImage("data/images/stripes.png"), false);
		getCanvas().addChild(background);
		
		this.keywordWidget = new KeywordWidget(this, 500, 500, 400, 400);
		this.timelineWidget = new TimelineWidget(this, 500, 500, 400, 200);
		getKeywordWidget().setVisible(false);
		getTimelineWidget().setVisible(false);
		widgetLayer.addChild(getKeywordWidget());
		widgetLayer.addChild(getTimelineWidget());
		
		this.registerPreDrawAction(new WidgetDistancePreDrawAction(getTimelineWidget(), getKeywordWidget()));
		
		this.getCanvas().addChild(widgetLayer);
	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
	}
	
	protected KeywordWidget getKeywordWidget() {
		return this.keywordWidget;
	}
	
	private TimelineWidget getTimelineWidget() {
		return this.timelineWidget;
	}
	
	public OrbWidget getOrbWidget() {
		return this.widgetLayer.getOrbWidget();
	}

	public BookshelfController getController() {
		return controller;
	}

	public void removeAllElements() {
		for (RetrievedElement p : retrievedElements) {
			p.destroy();
			unregisterAssociatedActions(p);
		}
		
		for (SuggestedElement s : suggestedElements) {
			s.destroy();
			unregisterAssociatedActions(s);
		}
		
		timelineWidget.removeValues();
		keywordWidget.removeKeywords();
		retrievedElements.clear();
		suggestedElements.clear();
	}

	public void showKeywordWidget() {
		getKeywordWidget().setVisible(true);
	}
	
	public void showTimelineWidget() {
		getTimelineWidget().setVisible(true);
	}

	public synchronized void addElement(SuggestedElement s, RetrievedElement element) {
		// Nasty solution to prevent adding suggestions for deleted items
		// Should in fact stop the thread, need to look more in depth for that
		if (!retrievedElements.contains(element))
			return;
		
		int i = suggestedElements.indexOf(s);
		
		if (i >= 0)
			s = suggestedElements.get(i);
		else {
			suggestedElements.add(s);
			if (s.getBook().hasPublishingYear())
				timelineWidget.addValue(s.getBook().getPublishingYear());
			if (s.getBook().hasKeywords())
				keywordWidget.addKeywords(s.getBook().getKeywords());
			
			getCanvas().addChild(s);
			updateElement(s);
		}
		
		s.addAssociatedElement(element);
		registerAssiciatedAction(new RelatedElementPreDrawAction(element, s));
		
		for (SuggestedElement so : this.suggestedElements) {
			registerAssiciatedAction(new UnrelatedElementPreDrawAction(so, s));
		}
	}

	public synchronized void addElement(RetrievedElement element) {
		retrievedElements.add(element);
		getCanvas().addChild(element);
		registerAssiciatedAction(new CreatedElementPreDrawAction(getOrbWidget(), element));
		
		try {
			GoogleBookProcessor gp = this.controller.getRelatedBooks(element.getBook());
			gp.addObserver(new SuggestedElementBirthObserver(this,element));
			gp.setLimit(20);
			Thread thread = new Thread(gp);
			thread.start();
		} catch (BookshelfUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeElement(RetrievedElement element) {
		int i=0;
		
		while (i<suggestedElements.size()) {
			suggestedElements.get(i).removeAssociatedElement(element);
			
			if (!suggestedElements.get(i).hasAssociatedElements()) {
				removeElement(suggestedElements.get(i));
			} else {
				i++;
			}		
		}
		
		element.destroy();
		retrievedElements.remove(element);
		unregisterAssociatedActions(element);
	}
	
	public void removeElement(SuggestedElement element) {
		if (element.getBook().hasPublishingYear())
			timelineWidget.removeValue(element.getBook().getPublishingYear());
		keywordWidget.removeKeywords(element.getBook().getKeywords());

		element.removeAllAssociatedElements();
		
		element.destroy();
		suggestedElements.remove(element);
		unregisterAssociatedActions(element);
	}
	
	public void updateElements() {
		for (SuggestedElement element : suggestedElements) {
			updateElement(element);
		}
	}
	
	public void updateElement(SuggestedElement element) {
		boolean evaluation = true;
		
		if (element.getBook().hasPublishingYear())
			evaluation &= timelineWidget.withinSelection(element);
		
		evaluation &= keywordWidget.withinSelection(element);
		
		element.setVisible(evaluation);
	}
	
	public void showInformationWindow(SuggestedElement element) {
		InformationWidget widget = new InformationWidget(getMTApplication(), 0, 0, 600, 400, element.getBook());
		getCanvas().addChild(widget);
		widget.setPositionGlobal(element.getCenterPointGlobal());
	}
	
	public void registerAssiciatedAction(ElementPreDrawAction action) {
		super.registerPreDrawAction(action);
		associatedActions.add(action);
	}
	
	public void unregisterAssociatedActions(AbstractElement element) {
		int i=0;
		
		while(i<associatedActions.size()) {
			ElementPreDrawAction action = associatedActions.get(i);
			if (action.getAssociatedElements().contains(element)) {
				unregisterPreDrawAction(action);
				associatedActions.remove(i);
			} else {
				i++;
			}
		}
	}
}
