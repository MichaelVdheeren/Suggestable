package controllers;

import java.util.ArrayList;

import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.sceneManagement.IPreDrawAction;

import view.elements.RetrievedElement;
import view.elements.SuggestedElement;
import view.elements.listeners.RelatedElementListener;
import view.elements.listeners.UnrelatedElementListener;
import view.elements.observers.SuggestedElementBirthObserver;
import view.widgets.WidgetLayer;
import view.widgets.custom.KeywordWidget;
import view.widgets.custom.TimelineWidget;
import bookshelf.apis.google.GoogleBookProcessor;
import bookshelf.exceptions.BookshelfUnavailableException;
import bookshelf.filters.KeywordFilter;
import bookshelf.filters.PublishingYearFilter;

public class SuggestableScene extends AbstractScene {
	private final BookshelfController controller = new BookshelfController();
	private final ArrayList<RetrievedElement> retrievedElements = new ArrayList<RetrievedElement>();
	private final ArrayList<SuggestedElement> suggestedElements = new ArrayList<SuggestedElement>();
	
	private KeywordWidget keywordWidget;
	private TimelineWidget timelineWidget;
	
	private KeywordFilter keywordFilter = new KeywordFilter(new ArrayList<String>());
	private PublishingYearFilter publishingYearFilter = new PublishingYearFilter(0, 2012);
	
	public WidgetLayer widgetLayer = new WidgetLayer(this);
	
	public SuggestableScene(SuggestableApplication application) {
		super(application, "Suggestable Scene");
	}
	
	@Override
	public void init() {
		MTBackgroundImage background = 
			new MTBackgroundImage(getMTApplication(), 
					getMTApplication().loadImage("data/images/stripes.png"), false);
		background.setUseDirectGL(false);
		getCanvas().addChild(background);
		
		this.keywordWidget = new KeywordWidget(this, 500, 500, 400, 400);
		this.timelineWidget = new TimelineWidget(this, 500, 500, 400, 200);
		getKeywordWidget().setVisible(false);
		getTimelineWidget().setVisible(false);
		widgetLayer.addChild(getKeywordWidget());
		widgetLayer.addChild(getTimelineWidget());
		
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

	public BookshelfController getController() {
		return controller;
	}

	public void removeAllElements() {
		for (RetrievedElement p : retrievedElements)
			p.destroy();
		
		for (SuggestedElement s : suggestedElements) {
			s.destroy();
		}
		
		timelineWidget.removeValues();
		keywordWidget.removeKeywords();
		retrievedElements.clear();
		suggestedElements.clear();
		// TODO: unregister listeners
	}

	public void showKeywordWidget() {
		getKeywordWidget().setVisible(true);
	}
	
	public void showTimelineWidget() {
		getTimelineWidget().setVisible(true);
	}

	public void addSuggestedElement(SuggestedElement s, RetrievedElement p) {
		suggestedElements.add(s);
		timelineWidget.addValue(s.getBook().getPublishingYear());
		keywordWidget.addKeywords(s.getBook().getKeywords());
		getCanvas().addChild(s);
		IPreDrawAction action = new RelatedElementListener(p, s);
		s.registerPreDrawAction(action);
		registerPreDrawAction(action);
		
		for (SuggestedElement so : this.suggestedElements) {
			if (so.equals(s))
				continue;
			
			IPreDrawAction action2 = new UnrelatedElementListener(so, s);
			s.registerPreDrawAction(action2);
			so.registerPreDrawAction(action2);
			registerPreDrawAction(action2);
		}
	}
	
	public void removeRetrievedElement(RetrievedElement s) {
//		for (IPreDrawAction action : s.getPreDrawActions()) {
//			this.unregisterPreDrawAction(action);
//		}
		
		s.destroy();
		retrievedElements.remove(s);
	}
	
	public void removeSuggestedElement(SuggestedElement s) {
		for (IPreDrawAction action : s.getPreDrawActions()) {
			this.unregisterPreDrawAction(action);
		}
		
		s.destroy();
		suggestedElements.remove(s);
	}

	public void addRetrievedElement(RetrievedElement p) {
		retrievedElements.add(p);
		getCanvas().addChild(p);
		
		try {
			GoogleBookProcessor gp = this.controller.getRelatedBooks(p.getBook());
			gp.addObserver(new SuggestedElementBirthObserver(this,p));
			gp.setLimit(5);
			Thread thread = new Thread(gp);
			thread.start();
		} catch (BookshelfUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public KeywordFilter getKeywordFilter() {
		return keywordFilter;
	}

	public void setKeywordFilter(KeywordFilter keywordFilter) {
		this.keywordFilter = keywordFilter;
		updateFilters();
	}

	public PublishingYearFilter getPublishingYearFilter() {
		return publishingYearFilter;
	}

	public void setPublishingYearFilter(PublishingYearFilter publishingYearFilter) {
		this.publishingYearFilter = publishingYearFilter;
		updateFilters();
	}

	public void updateFilters() {
		for (SuggestedElement element : suggestedElements) {
			applyFilters(element);
		}
	}
	
	public void applyFilters(SuggestedElement element) {
		boolean filterPY = getPublishingYearFilter().applyTo(element.getBook());
		boolean filterK = getKeywordFilter().applyTo(element.getBook());
		
		// TODO: add keyword filter
		if (filterPY)
			element.setVisible(false);
		else
			element.setVisible(true);
	}
}
