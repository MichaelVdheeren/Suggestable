package view;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.input.MTEvent;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;

import view.widgets.KeywordWidget;
import view.widgets.OrbWidget;
import view.widgets.TimelineWidget;
import view.widgets.controls.OrbButton;

public class SuggestableScene extends AbstractScene {
	private MTApplication application;
	
	public SuggestableScene(MTApplication application, String name) {
		super(application, name);
		this.application = application;
		this.setClearColor(new MTColor(146, 150, 188));
	}
	
	@Override
	public void init() {
		MTBackgroundImage background = new MTBackgroundImage(application, application.loadImage("data/images/stripes.png"), false);
		this.getCanvas().addChild(background);
		
		OrbWidget orb = new OrbWidget(500, 600, application);
		
		
		OrbButton btnTimeline = new OrbButton(application,"Tijdlijn");
		btnTimeline.registerInputProcessor(new TapProcessor(application));
		btnTimeline.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent e) {
				if (e.getId() == MTGestureEvent.GESTURE_ENDED)
					getCanvas().addChild(new TimelineWidget(application.getWidth()/2, application.getHeight()/2, application));
				
				return true;
			}
		});
		
		OrbButton btnKeywords = new OrbButton(application,"Kernwoorden");
		btnKeywords.registerInputProcessor(new TapProcessor(application));
		btnKeywords.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent e) {
				if (e.getId() == MTGestureEvent.GESTURE_ENDED)
					getCanvas().addChild(new KeywordWidget(100, 100, application));
				
				return true;
			}
		});
		
		orb.addButton(btnTimeline);
		orb.addButton(btnKeywords);
		this.getCanvas().addChild(orb);
	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
		
	}

}
