package view.widgets.listeners;

import org.mt4j.MTApplication;
import org.mt4j.components.MTCanvas;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;

import view.widgets.custom.KeywordWidget;
import view.widgets.custom.OrbButton;

public class ButtonKeywords extends OrbButton {
	public ButtonKeywords(final MTApplication application, final MTCanvas canvas) {
		super(application,"Keywords");
		registerInputProcessor(new TapProcessor(application));
		addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent e) {
				if (e.getId() == MTGestureEvent.GESTURE_ENDED) {
					KeywordWidget widget = new KeywordWidget(application, application.getWidth()/2, application.getHeight()/2, 400, 200);
					canvas.addChild(widget);
				}
				return true;
			}
		});
	}
}
