package view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import view.universe.Placeholder;
import view.widgets.custom.OrbWidget;
import view.widgets.listeners.ButtonKeywords;
import view.widgets.listeners.ButtonRemove;
import view.widgets.listeners.ButtonTimeline;
import application.Suggestable;
import application.SuggestableController;
import bookshelf.AbstractBook;
import bookshelf.exceptions.BookNotFoundException;
import bookshelf.exceptions.BookshelfUnavailableException;
import bookshelf.exceptions.InvalidBarcodeException;

public class SuggestableScene extends AbstractScene implements Observer {
	private SuggestableController controller = new SuggestableController();
	private ArrayList<Placeholder> books = new ArrayList<Placeholder>();
	
	public SuggestableScene(Suggestable application, String name) {
		super(application, name);
		
		this.setClearColor(new MTColor(146, 150, 188));
	}
	
	@Override
	public void init() {
		MTBackgroundImage background = 
				new MTBackgroundImage(getMTApplication(), 
						getMTApplication().loadImage("data/images/stripes.png"), false);
		this.getCanvas().addChild(background);
		
		initializeOrb();
		
		MTTextArea text = new MTTextArea(getMTApplication());
		text.setText("Test");
		text.setPositionGlobal(new Vector3D(40,40));
		this.getCanvas().addChild(text);
		
		text.removeAllGestureEventListeners();
		
		text.registerInputProcessor(new TapProcessor(getMTApplication()));
		text.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent e) {
				// Check if gestured ended on top of this
				// Then check if we are dropping a suggestion on this
				if (e.getId() == MTGestureEvent.GESTURE_ENDED) {
					AbstractBook book;
					try {
						book = controller.getBook("009906485");
						Placeholder p = new Placeholder(getMTApplication(), new Vector3D(100,100), 100, book);
						books.add(p);
						getCanvas().addChild(p);
					} catch (InvalidBarcodeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BookshelfUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BookNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				return true;
			}
		});
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
		this.getCanvas().addChild(orb);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
