package view.widgets.listeners;

import org.mt4j.MTApplication;
import org.mt4j.components.MTCanvas;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;

import view.widgets.custom.OrbButton;

public class ButtonClearTable extends OrbButton {
	public ButtonClearTable(final MTApplication application, final MTCanvas canvas) {
		super(application,"Clear Table");
		registerInputProcessor(new TapProcessor(application));
		addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent e) {
				if (e.getId() == MTGestureEvent.GESTURE_ENDED) {

				}
				return true;
			}
		});
	}
}
