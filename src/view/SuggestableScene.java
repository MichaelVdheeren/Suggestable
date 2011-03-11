package view;

import java.util.ArrayList;

import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;

import view.observers.SuggestionObserver;
import view.universe.Placeholder;
import view.universe.Suggestion;
import view.widgets.buttons.ButtonClearTable;
import view.widgets.buttons.ButtonKeywords;
import view.widgets.buttons.ButtonRemove;
import view.widgets.buttons.ButtonTest;
import view.widgets.buttons.ButtonTimeline;
import view.widgets.custom.KeywordWidget;
import view.widgets.custom.OrbWidget;
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
	
	public SuggestableScene(Suggestable application) {
		super(application, "Suggestable Scene");
		this.setClearColor(new MTColor(146, 150, 188));
	}
	
	@Override
	public void init() {
		MTBackgroundImage background = 
				new MTBackgroundImage(getMTApplication(), 
						getMTApplication().loadImage("data/images/stripes.png"), false);
		this.getCanvas().addChild(background);
		
		this.keywordWidget = new KeywordWidget(getMTApplication(), 500, 500, 400, 200);
		this.timelineWidget = new TimelineWidget(getMTApplication(), 500, 500, 400, 200);
		getKeywordWidget().setVisible(false);
		getTimelineWidget().setVisible(false);
		getCanvas().addChild(getKeywordWidget());
		getCanvas().addChild(getTimelineWidget());
		
		initializeOrb();
	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
	}
	
	public void initializeOrb() {
		float x = getMTApplication().getWidth()/2;
		float y = getMTApplication().getHeight()/2;
		
		OrbWidget orb = new OrbWidget(x, y, getMTApplication());
		orb.addButton(new ButtonTimeline(this));
		orb.addButton(new ButtonKeywords(this));
		orb.addButton(new ButtonRemove(this));
		orb.addButton(new ButtonClearTable(this));
		orb.addButton(new ButtonTest(this));
		this.getCanvas().addChild(orb);
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
	}

	public void addPlaceholder(Placeholder p) {
		booksInPosession.add(p);
		getCanvas().addChild(p);
		
		try {
			GoogleBookProcessor gp = getController().getRelatedBooks(p.getBook());
			gp.addObserver(new SuggestionObserver(this,p));
			gp.setLimit(5);
			Thread thread = new Thread(gp);
			thread.start();
		} catch (BookshelfUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
