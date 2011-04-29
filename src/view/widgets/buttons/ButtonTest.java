package view.widgets.buttons;

import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;

import controllers.SuggestableScene;

import view.components.specific.OrbButton;
import view.elements.observers.RetrievedElementBirthObserver;
import bookshelf.apis.libis.LibisBookProcessor;
import bookshelf.exceptions.BookNotFoundException;
import bookshelf.exceptions.BookshelfUnavailableException;
import bookshelf.exceptions.InvalidBarcodeException;

public class ButtonTest extends OrbButton {
	private int count = 0;
	private int max = 5;
	
	public ButtonTest(final SuggestableScene scene) {
		super(scene.getMTApplication(),"Test It!");
		registerInputProcessor(new TapProcessor(scene.getMTApplication()));
		addGestureListener(TapProcessor.class, new IGestureEventListener() {
			

			@Override
			public boolean processGestureEvent(MTGestureEvent e) {
				// Check if gestured ended on top of this
				// Then check if we are dropping a suggestion on this
				if (e.getId() == MTGestureEvent.GESTURE_ENDED) {
					try {
						LibisBookProcessor lp = null;
						switch ((count++)%max) {
									// An introduction to OO programming with Java
							case 0: lp = scene.getController().getBookByBarcode("009906485");
									break;
									// Understanding OO programming with Java
							case 1: lp = scene.getController().getBookByISBN("0-201-61273-9");
									break;
									// Learning Java
							case 2: lp = scene.getController().getBookByISBN("978-0-596-00873-4");
									break;
									// Object-oriented programming with Java : an introduction. 
							case 3: lp = scene.getController().getBookByISBN("0-13-086900-7");
									break;
									// Data structures and abstractions with Java.
							case 4: lp = scene.getController().getBookByISBN("0-13-204367-X");
									break;
						}
						lp.addObserver(new RetrievedElementBirthObserver(scene));
						lp.setLimit(1);
						Thread thread = new Thread(lp,"Book Processor");
						thread.start();
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
}
