package view.widgets.orbactions;

import java.util.ArrayList;

import org.mt4j.MTApplication;
import org.mt4j.components.MTCanvas;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;

import view.widgets.KeywordWidget;
import view.widgets.OrbButton;

public class ButtonKeywords extends OrbButton {
	public ButtonKeywords(final MTApplication application, final MTCanvas canvas) {
		super(application,"Kernwoorden");
		registerInputProcessor(new TapProcessor(application));
		addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent e) {
				if (e.getId() == MTGestureEvent.GESTURE_ENDED) {
					KeywordWidget widget = new KeywordWidget(application.getWidth()/2, application.getHeight()/2, 400, 200, application);
					ArrayList<String> keywords = new ArrayList<String>();
					keywords.add("Boom");
					keywords.add("Wortel");
					keywords.add("Luipaard");
					keywords.add("Ezel");
					keywords.add("Wortel");
					keywords.add("Giraf");
					keywords.add("Dwerg");
					keywords.add("Koe");
					keywords.add("Chimp");
					keywords.add("Luipaard");
					keywords.add("Tag");
					keywords.add("Dwerg");
					keywords.add("Koe");
					widget.setKeywords(keywords);
					canvas.addChild(widget);
				}
				return true;
			}
		});
	}
}
