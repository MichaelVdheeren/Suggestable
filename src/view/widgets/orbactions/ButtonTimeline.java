package view.widgets.orbactions;

import org.mt4j.MTApplication;
import org.mt4j.components.MTCanvas;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;

import view.widgets.OrbButton;
import view.widgets.TimelineWidget;

public class ButtonTimeline extends OrbButton {
	public ButtonTimeline(final MTApplication application, final MTCanvas canvas) {
		super(application,"Tijdlijn");
		registerInputProcessor(new TapProcessor(application));
		addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent e) {
				if (e.getId() == MTGestureEvent.GESTURE_ENDED)
					canvas.addChild(new TimelineWidget(application.getWidth()/2, application.getHeight()/2, 400, 200, application));
				
				return true;
			}
		});
	}
}
