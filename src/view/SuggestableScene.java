package view;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;

import view.components.ActionOrb;

public class SuggestableScene extends AbstractScene {
	private MTApplication application;
	
	public SuggestableScene(MTApplication application, String name) {
		super(application, name);
		this.application = application;
		this.setClearColor(new MTColor(146, 150, 188));
	}
	
	@Override
	public void init() {
		MTBackgroundImage background = new MTBackgroundImage(application, application.loadImage("backgrounds/" + "leather.jpg"), true);
		this.getCanvas().addChild(background);
		this.getCanvas().addChild(new ActionOrb(this.application.getWidth()/2, this.application.getHeight()/2, application));
	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
		
	}

}
