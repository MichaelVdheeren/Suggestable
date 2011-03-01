package view.widgets.listeners;

import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;

import view.SuggestableScene;
import view.widgets.custom.OrbButton;
import view.widgets.custom.TimelineWidget;

public class ButtonTimeline extends OrbButton {
	public ButtonTimeline(final SuggestableScene scene) {
		super(scene.getMTApplication(),"Timeline");
		registerInputProcessor(new TapProcessor(scene.getMTApplication()));
		addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent e) {
				if (e.getId() == MTGestureEvent.GESTURE_ENDED)
					scene.getCanvas().addChild(new TimelineWidget(scene.getMTApplication(), scene.getMTApplication().getWidth()/2, scene.getMTApplication().getHeight()/2, 400, 200));
				
				return true;
			}
		});
	}
}
