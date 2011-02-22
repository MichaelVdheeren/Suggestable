package view.widgets.orbactions;

import org.mt4j.MTApplication;
import org.mt4j.components.MTCanvas;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;

import view.widgets.OrbButton;

public class ButtonRemove extends OrbButton {
	public ButtonRemove(final MTApplication application, final MTCanvas canvas) {
		super(application,"Kernwoorden");
		registerInputProcessor(new DragProcessor(application));
		addGestureListener(DragProcessor.class, new IGestureEventListener() {
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
