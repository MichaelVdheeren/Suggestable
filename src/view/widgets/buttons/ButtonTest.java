package view.widgets.buttons;

import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;

import view.SuggestableScene;
import view.elements.observers.RetrievedElementBirthObserver;
import view.widgets.custom.OrbButton;
import bookshelf.apis.libis.LibisBookProcessor;
import bookshelf.exceptions.BookNotFoundException;
import bookshelf.exceptions.BookshelfUnavailableException;
import bookshelf.exceptions.InvalidBarcodeException;

public class ButtonTest extends OrbButton {
	public ButtonTest(final SuggestableScene scene) {
		super(scene.getMTApplication(),"TESTRUN");
		registerInputProcessor(new TapProcessor(scene.getMTApplication()));
		addGestureListener(TapProcessor.class, new IGestureEventListener() {
			

			@Override
			public boolean processGestureEvent(MTGestureEvent e) {
				// Check if gestured ended on top of this
				// Then check if we are dropping a suggestion on this
				if (e.getId() == MTGestureEvent.GESTURE_ENDED) {
					try {
						LibisBookProcessor lp = scene.getController().getBook("009906485");
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
