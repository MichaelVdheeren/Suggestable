package view;

import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;

import view.widgets.OrbWidget;
import view.widgets.orbactions.ButtonKeywords;
import view.widgets.orbactions.ButtonRemove;
import view.widgets.orbactions.ButtonTimeline;
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
		orb.addButton(new ButtonTimeline(application, getCanvas()));
		orb.addButton(new ButtonKeywords(application, getCanvas()));
		orb.addButton(new ButtonRemove(application, getCanvas()));
		this.getCanvas().addChild(orb);
	}

}
