package view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;

import view.universe.Placeholder;
import view.universe.Suggestion;
import view.widgets.custom.OrbWidget;
import view.widgets.listeners.ButtonClearTable;
import view.widgets.listeners.ButtonKeywords;
import view.widgets.listeners.ButtonRemove;
import view.widgets.listeners.ButtonTest;
import view.widgets.listeners.ButtonTimeline;
import application.Suggestable;
import application.ModelController;
import bookshelf.apis.google.GoogleBook;
import bookshelf.apis.google.GoogleBookProcessor;
import bookshelf.apis.libis.LibisBook;
import bookshelf.apis.libis.LibisBookProcessor;
import bookshelf.exceptions.BookshelfUnavailableException;

public class SuggestableScene extends AbstractScene implements Observer {
	private ModelController controller = new ModelController();
	private ArrayList<Placeholder> booksInPosession = new ArrayList<Placeholder>();
	private ArrayList<Suggestion> booksRelated = new ArrayList<Suggestion>();
	
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
		orb.addButton(new ButtonTimeline(getMTApplication(), getCanvas()));
		orb.addButton(new ButtonKeywords(getMTApplication(), getCanvas()));
		orb.addButton(new ButtonRemove(getMTApplication(), getCanvas()));
		orb.addButton(new ButtonClearTable(this));
		orb.addButton(new ButtonTest(getMTApplication(), this, getCanvas()));
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

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof GoogleBookProcessor) {
			GoogleBook book = (GoogleBook) arg;
			Suggestion s = new Suggestion(getMTApplication(), 100, 100, 50, book);
			booksRelated.add(s);
			getCanvas().addChild(s);
		} else if (o instanceof LibisBookProcessor) {
			LibisBook book = (LibisBook) arg;
			Placeholder p = new Placeholder(getMTApplication(), 200, 200, 50, book);
			booksInPosession.add(p);
			getCanvas().addChild(p);
			try {
				GoogleBookProcessor gp = getController().getRelatedBooks(book);
				gp.addObserver(this);
				gp.setLimit(5);
				Thread thread = new Thread(gp,"Book Processor");
				thread.start();
			} catch (BookshelfUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void removeAllBooks() {
		for (Placeholder p : booksInPosession)
			p.destroy();
		
		for (Suggestion s : booksRelated)
			s.destroy();
		
		booksInPosession.clear();
		booksRelated.clear();
	}
}
