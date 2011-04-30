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
					
				}
				return true;
			}
		});
	}
}
