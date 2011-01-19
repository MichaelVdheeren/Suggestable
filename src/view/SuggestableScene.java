package view;

import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;

import view.widgets.KeywordWidget;
import view.widgets.OrbButton;
import view.widgets.OrbWidget;
import view.widgets.TimelineWidget;
import application.Suggestable;

public class SuggestableScene extends AbstractScene {
	private Suggestable application;
	
	public SuggestableScene(Suggestable application, String name) {
		super(application, name);
		this.application = application;
		this.setClearColor(new MTColor(146, 150, 188));
	}
	
	@Override
	public void init() {
		MTBackgroundImage background = new MTBackgroundImage(application, application.loadImage("data/images/stripes.png"), false);
		this.getCanvas().addChild(background);
		
		initializeOrb();
	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
		
	}
	
	public void initializeOrb() {
		OrbWidget orb = new OrbWidget(application.getWidth()/2, application.getHeight()/2, application);
		
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
					getCanvas().addChild(new KeywordWidget(application.getWidth()/2, application.getHeight()/2, application));
				
				return true;
			}
		});
		
		OrbButton btnSearch = new OrbButton(application,"Zoeken");
		
		orb.addButton(btnTimeline);
		orb.addButton(btnKeywords);
		orb.addButton(btnSearch);
		this.getCanvas().addChild(orb);
	}

}
