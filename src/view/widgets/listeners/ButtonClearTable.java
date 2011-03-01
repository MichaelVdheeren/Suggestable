package view.widgets.listeners;

import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;

import view.SuggestableScene;
import view.widgets.custom.OrbButton;

public class ButtonClearTable extends OrbButton {
	public ButtonClearTable(final SuggestableScene scene) {
		super(scene.getMTApplication(),"Clear Table");
		registerInputProcessor(new TapProcessor(scene.getMTApplication()));
		addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent) ge;
				if (te.getTapID() == TapEvent.TAPPED) {
					scene.removeAllBooks();
				}
				return true;
			}
		});
	}
}
