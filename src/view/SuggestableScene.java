package view;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;

import view.widgets.OrbWidget;
import view.widgets.KeywordWidget;
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
		this.getCanvas().addChild(new TimelineWidget(this.application.getWidth()/2, this.application.getHeight()/2, application));
		this.getCanvas().addChild(new KeywordWidget(100, 100, application));
		OrbWidget orb = new OrbWidget(500, 600, application);
		orb.addAction(new OrbButton(application,"Tijdlijn"));
		orb.addAction(new OrbButton(application,"Kernwoorden"));
		orb.addAction(new OrbButton(application,"Verwijderen"));
		orb.addAction(new OrbButton(application,"Leeg maken"));
		this.getCanvas().addChild(orb);
	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
		
	}

}
