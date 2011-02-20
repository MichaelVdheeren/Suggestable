package view;

import java.util.ArrayList;

import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;

import view.universe.Suggestion;
import view.widgets.OrbButton;
import view.widgets.OrbWidget;
import view.widgets.orbactions.ButtonKeywords;
import view.widgets.orbactions.ButtonTimeline;
import application.Suggestable;
import bookshelf.AbstractBook;
import bookshelf.apis.google.GoogleBookshelf;
import bookshelf.exceptions.BookshelfUnavailableException;

public class SuggestableScene extends AbstractScene {
	private Suggestable application;
	
	public SuggestableScene(Suggestable application, String name) {
		super(application, name);
		this.application = application;
		this.setClearColor(new MTColor(146, 150, 188));
	}
	
	@Override
	public void init() {
		MTBackgroundImage background = new MTBackgroundImage(application, application.loadImage("data/images/stripes.png"), false);
		this.getCanvas().addChild(background);
		
		initializeOrb();
	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
	}
	
	public void initializeOrb() {
		OrbWidget orb = new OrbWidget(application.getWidth()/2, application.getHeight()/2, application);
		orb.addButton(new ButtonTimeline(application, getCanvas()));
		orb.addButton(new ButtonKeywords(application, getCanvas()));
		
		OrbButton btnSearch = new OrbButton(application,"Zoeken");
		btnSearch.registerInputProcessor(new TapProcessor(application));
		btnSearch.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent e) {
				if (e.getId() != MTGestureEvent.GESTURE_ENDED)
					return false;
				
				GoogleBookshelf googleBooks = new GoogleBookshelf();
				try {
					ArrayList<AbstractBook> books = googleBooks.getBooks("Blue Ocean Strategy");
					for (AbstractBook b : books)
						getCanvas().addChild(new Suggestion(application, 300, 300, 100, b));
				} catch (BookshelfUnavailableException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				
				return true;
			}
		});

		orb.addButton(btnSearch);
		this.getCanvas().addChild(orb);
	}

}
