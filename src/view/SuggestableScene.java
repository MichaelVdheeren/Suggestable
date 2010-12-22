package view;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;

import view.components.ActionOrb;
import view.widgets.KeywordWidget;
import view.widgets.TimelineWidget;

public class SuggestableScene extends AbstractScene {
	private MTApplication application;
	
	public SuggestableScene(MTApplication application, String name) {
		super(application, name);
		this.application = application;
		this.setClearColor(new MTColor(146, 150, 188));
	}
	
	@Override
	public void init() {
		MTBackgroundImage background = new MTBackgroundImage(application, application.loadImage("backgrounds/stripes.png"), false);
		this.getCanvas().addChild(background);
		this.getCanvas().addChild(new TimelineWidget(this.application.getWidth()/2, this.application.getHeight()/2, application));
		this.getCanvas().addChild(new KeywordWidget(100, 100, application));
		ActionOrb orb = new ActionOrb(500, 600, application);
		orb.addAction(new ActionButton(application));
		orb.addAction(new ActionButton(application));
		orb.addAction(new ActionButton(application));
		orb.addAction(new ActionButton(application));
		orb.addAction(new ActionButton(application));
		this.getCanvas().addChild(orb);
	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
		
	}

}
