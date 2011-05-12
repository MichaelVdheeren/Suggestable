package controllers;

import java.util.ArrayList;
import java.util.Random;

import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import view.components.MTMessage;
import view.components.actions.ComponentDistancePreDrawAction;
import view.components.specific.MTInformationWindow;
import view.components.specific.MTTrashCan;
import view.components.widgets.BarcodeWidget;
import view.components.widgets.KeywordWidget;
import view.components.widgets.TimelineWidget;
import view.elements.AbstractElement;
import view.elements.RetrievedElement;
import view.elements.SuggestedElement;
import view.elements.actions.AbstractElementPreDrawAction;
import view.elements.actions.CreatedElementPreDrawAction;
import view.elements.actions.RelatedElementPreDrawAction;
import view.elements.actions.UnrelatedElementPreDrawAction;
import view.elements.observers.SuggestedElementBirthObserver;
import view.layers.PanLayer;
import view.layers.WidgetLayer;
import bookshelf.apis.google.GoogleBookProcessor;
import bookshelf.exceptions.BookshelfUnavailableException;

public class SuggestableScene extends AbstractScene {
	private final BookshelfController controller = new BookshelfController();
	private final ArrayList<RetrievedElement> retrievedElements = new ArrayList<RetrievedElement>();
	private final ArrayList<SuggestedElement> suggestedElements = new ArrayList<SuggestedElement>();
	private final ArrayList<AbstractElementPreDrawAction> associatedActions = new ArrayList<AbstractElementPreDrawAction>();
	
	private WidgetLayer widgetLayer;
	private PanLayer panLayer;
	
	private KeywordWidget keywordWidget;
	private TimelineWidget timelineWidget;
	private BarcodeWidget barcodeWidget;
	
	private MTMessage bookNeededMessage;
	
	public SuggestableScene(SuggestableApplication application) {
		super(application, "Suggestable Scene");
	}
	
	@Override
	public void init() {
		// Add the background
		MTBackgroundImage background = 
			new MTBackgroundImage(getMTApplication(), getMTApplication().loadImage("data/images/stripes.png"), false);
		getCanvas().addChild(background);
		
		// Create the layers
		initializePanLayer();
		widgetLayer = new WidgetLayer(this);
		getCanvas().addChild(widgetLayer);
		
		float x = getMTApplication().getWidth()/2;
		float y = getMTApplication().getHeight()/2;
		
		initializeWidgets();
		
		// Messages
		bookNeededMessage = new MTMessage(this, "Place a book to scan it and start, \nyou can take it away afterwards.");
		getCanvas().addChild(bookNeededMessage);
		bookNeededMessage.removeAllGestureEventListeners();
		bookNeededMessage.setPositionRelativeToParent(new Vector3D(x,y));
		
		// Fonts
		FontManager.getInstance().createFont(getMTApplication(), "fonts/Trebuchet MS.ttf", 
				12, 							// Font size
				new MTColor(255,255,255));	// Font color
	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
	}
	
	public void initializeWidgets() {
		float x = getMTApplication().getWidth()/2;
		float y = getMTApplication().getHeight()/2;
		
		if (keywordWidget != null)
			keywordWidget.destroy();
		
		if (timelineWidget != null)
			timelineWidget.destroy();
		
		if (barcodeWidget != null)
			barcodeWidget.destroy();
		
		// Create the facet widgets
		keywordWidget = new KeywordWidget(this, 300, y/2, 400, 400);
		timelineWidget = new TimelineWidget(this, 300, y+y/2, 400, 200);
		barcodeWidget = new BarcodeWidget(this, x, y, 400, 400);
		// Hide the facet widgets
		getKeywordWidget().setVisible(false);
		getTimelineWidget().setVisible(false);
		getBarcodeWidget().setVisible(false);
		// Add the widgets to the widget layer
		widgetLayer.addChild(getKeywordWidget());
		widgetLayer.addChild(getTimelineWidget());
		widgetLayer.addChild(getBarcodeWidget());
		// Keep the widgets separate
		registerPreDrawAction(new ComponentDistancePreDrawAction(getTimelineWidget(), getKeywordWidget()));
	}
	
	public void initializePanLayer() {
		if (panLayer != null)
			panLayer.destroy();
		
		panLayer = new PanLayer(this);
		getCanvas().addChild(panLayer);
	}
	
	protected KeywordWidget getKeywordWidget() {
		return this.keywordWidget;
	}
	
	private TimelineWidget getTimelineWidget() {
		return this.timelineWidget;
	}
	
	private BarcodeWidget getBarcodeWidget() {
		return this.barcodeWidget;
	}
	
	public MTTrashCan getTrashcan() {
		return widgetLayer.getTrashcan();
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
		bookNeededMessage.setVisible(true);
	}

	public void showKeywordWidget() {
		getKeywordWidget().setVisible(true);
	}
	
	public void showTimelineWidget() {
		getTimelineWidget().setVisible(true);
	}
	
	public boolean containsElement(RetrievedElement element) {
		return retrievedElements.contains(element);
	}

	public synchronized void addElement(SuggestedElement s, RetrievedElement element) {
//		// Nasty solution to prevent adding suggestions for deleted items
//		// Should in fact stop the thread, need to look more in depth for that
//		if (!retrievedElements.contains(element))
//			return;
		
		
		
		int i = suggestedElements.indexOf(s);
		
		if (i >= 0)
			s = suggestedElements.get(i);
		else {
			suggestedElements.add(s);
			if (s.getBook().hasPublishingYear())
				timelineWidget.addValue(s.getBook().getPublishingYear());
			if (s.getBook().hasKeywords())
				keywordWidget.addKeywords(s.getBook().getKeywords());
			
			panLayer.addChild(s);
			updateElement(s);
			
			Vector3D position = new Vector3D(50, 50);
			Random r = new Random();
			position.rotateZ(r.nextInt(360));
			
			s.setPositionRelativeToOther(element, position);
		}

		s.addAssociatedElement(element);
		registerAssociatedAction(new RelatedElementPreDrawAction(element, s));
		
		for (SuggestedElement so : this.suggestedElements) {
			registerAssociatedAction(new UnrelatedElementPreDrawAction(so, s));
		}
	}

	public synchronized void addElement(RetrievedElement element) {		
		retrievedElements.add(element);
		panLayer.addChild(element);
		bookNeededMessage.setVisible(false);
		
		Vector3D anchor = new Vector3D(0,getMTApplication().getHeight()/2);
		
		element.setPositionGlobal(new Vector3D(1,getMTApplication().getHeight()/2));
		registerAssociatedAction(new CreatedElementPreDrawAction(panLayer.globalToLocal(anchor),element));
		
		try {
			GoogleBookProcessor gp = this.controller.getRelatedBooks(element.getBook());
			gp.addObserver(new SuggestedElementBirthObserver(this,element));
			gp.setLimit(20);
			Thread thread = new Thread(gp);
			element.setLoading(true);
			thread.start();
		} catch (BookshelfUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void removeElement(RetrievedElement element) {
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
		
		if (retrievedElements.isEmpty())
			bookNeededMessage.setVisible(true);
	}
	
	public synchronized void removeElement(SuggestedElement element) {
		if (element.getBook().hasPublishingYear())
			timelineWidget.removeValue(element.getBook().getPublishingYear());
		if (element.getBook().hasKeywords())
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
	
	public ArrayList<AbstractElement> getElements() {
		ArrayList<AbstractElement> result = new ArrayList<AbstractElement>();
		result.addAll(retrievedElements);
		result.addAll(suggestedElements);
		return result;
	}
	
	public void showInformationWindow(SuggestedElement element) {
		MTInformationWindow widget = new MTInformationWindow(getMTApplication(), 0, 0, 850, 600, element.getBook());
		widgetLayer.addChild(widget);
		widget.setPositionGlobal(new Vector3D(getMTApplication().getWidth()/2, getMTApplication().getHeight()/2));
	}
	
	public void registerAssociatedAction(AbstractElementPreDrawAction action) {
		super.registerPreDrawAction(action);
		associatedActions.add(action);
	}
	
	public void unregisterAssociatedActions(AbstractElement element) {
		int i=0;
		
		while(i<associatedActions.size()) {
			AbstractElementPreDrawAction action = associatedActions.get(i);
			if (action.getAssociatedElements().contains(element)) {
				unregisterPreDrawAction(action);
				associatedActions.remove(i);
			} else {
				i++;
			}
		}
	}

	public void showBarcodeWidget() {
		getBarcodeWidget().setVisible(true);
	}
}
