package view;

import java.util.ArrayList;

import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.input.gestureAction.DefaultPanAction;
import org.mt4j.input.inputProcessors.componentProcessors.panProcessor.PanProcessorTwoFingers;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.sceneManagement.IPreDrawAction;
import org.mt4j.util.MTColor;

import view.listeners.RelatedListener;
import view.listeners.UnrelatedListener;
import view.observers.SuggestionObserver;
import view.universe.Placeholder;
import view.universe.Suggestion;
import view.widgets.custom.KeywordWidget;
import view.widgets.custom.TimelineWidget;
import application.ModelController;
import application.Suggestable;
import bookshelf.apis.google.GoogleBook;
import bookshelf.apis.google.GoogleBookProcessor;
import bookshelf.exceptions.BookshelfUnavailableException;

public class SuggestableScene extends AbstractScene {
	private ModelController controller = new ModelController();
	private ArrayList<Placeholder> booksInPosession = new ArrayList<Placeholder>();
	private ArrayList<Suggestion> booksRelated = new ArrayList<Suggestion>();
	
	private KeywordWidget keywordWidget;
	private TimelineWidget timelineWidget;
	
	public WidgetLayer widgetLayer = new WidgetLayer(this);
	
	public SuggestableScene(Suggestable application) {
		super(application, "Suggestable Scene");
	}
	
	@Override
	public void init() {
		MTBackgroundImage background = 
			new MTBackgroundImage(getMTApplication(), 
					getMTApplication().loadImage("data/images/stripes.png"), false);

		getCanvas().addChild(background);
		
		this.keywordWidget = new KeywordWidget(getMTApplication(), 500, 500, 400, 200);
		this.timelineWidget = new TimelineWidget(getMTApplication(), 500, 500, 400, 200);
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

	public ArrayList<Placeholder> getBooksInPosession() {
		return booksInPosession;
	}

	protected ArrayList<Suggestion> getBooksRelated() {
		return booksRelated;
	}

	
	public ModelController getController() {
		return controller;
	}

	protected void setController(ModelController controller) {
		this.controller = controller;
	}
	
	protected KeywordWidget getKeywordWidget() {
		return this.keywordWidget;
	}
	
	private TimelineWidget getTimelineWidget() {
		return this.timelineWidget;
	}

	public void removeAllBooks() {
		for (Placeholder p : booksInPosession)
			p.destroy();
		
		for (Suggestion s : booksRelated)
			s.destroy();
		
		booksInPosession.clear();
		booksRelated.clear();
	}

	public void showKeywordWidget() {
		ArrayList<String> keywords = new ArrayList<String>();
		
		for (Suggestion s : booksRelated) {
			GoogleBook book = s.getBook();
			keywords.addAll(book.getWords());
		}
		
		getKeywordWidget().setKeywords(keywords);
		getKeywordWidget().setVisible(true);
	}
	
	public void showTimelineWidget() {
		ArrayList <Integer> years = new ArrayList<Integer>();
		
		for (Suggestion s : booksRelated) {
			GoogleBook book = s.getBook();
			years.add(book.getPublishingYear());
		}
		
		getTimelineWidget().setValues(years);
		getTimelineWidget().setVisible(true);
	}

	public void addSuggestion(Suggestion s, Placeholder p) {
		booksRelated.add(s);
		getCanvas().addChild(s);
		IPreDrawAction action = new RelatedListener(p, s);
		s.registerPreDrawAction(action);
		registerPreDrawAction(action);
		
		for (Suggestion so : this.booksRelated) {
			if (so.equals(s))
				continue;
			
			IPreDrawAction action2 = new UnrelatedListener(so, s);
			s.registerPreDrawAction(action2);
			so.registerPreDrawAction(action2);
			registerPreDrawAction(action2);
		}
	}
	
	public void removeSuggestion(Suggestion s) {
		for (IPreDrawAction action : s.getPreDrawActions())
			this.unregisterPreDrawAction(action);
		
		s.destroy();
		booksRelated.remove(s);
	}

	public void addPlaceholder(Placeholder p) {
		booksInPosession.add(p);
		getCanvas().addChild(p);
		
		try {
			GoogleBookProcessor gp = getController().getRelatedBooks(p.getBook());
			gp.addObserver(new SuggestionObserver(this,p));
			gp.setLimit(7);
			Thread thread = new Thread(gp);
			thread.start();
		} catch (BookshelfUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
