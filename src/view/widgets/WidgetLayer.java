package view.widgets;

import org.mt4j.components.MTComponent;
import org.mt4j.util.camera.MTCamera;

import view.widgets.buttons.ButtonClearTable;
import view.widgets.buttons.ButtonKeywords;
import view.widgets.buttons.ButtonTest;
import view.widgets.buttons.ButtonTimeline;
import view.widgets.custom.OrbWidget;
import controllers.SuggestableScene;

public class WidgetLayer extends MTComponent {
	private final SuggestableScene scene;
	
	public WidgetLayer(SuggestableScene scene) {
		super(scene.getMTApplication(), new MTCamera(scene.getMTApplication()));
		this.scene = scene;
		
		float x = scene.getMTApplication().getWidth()/2;
		float y = scene.getMTApplication().getHeight()/2;
		
		OrbWidget orb = new OrbWidget(scene, x, y);
		orb.addButton(new ButtonTimeline(scene));
		orb.addButton(new ButtonKeywords(scene));
		orb.addButton(new ButtonClearTable(scene));
		orb.addButton(new ButtonTest(scene));
		addChild(orb);
	}
}
