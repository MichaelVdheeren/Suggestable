package view.widgets;

import org.mt4j.components.MTComponent;
import org.mt4j.util.camera.MTCamera;

import view.SuggestableScene;
import view.widgets.buttons.ButtonClearTable;
import view.widgets.buttons.ButtonKeywords;
import view.widgets.buttons.ButtonRemove;
import view.widgets.buttons.ButtonTest;
import view.widgets.buttons.ButtonTimeline;
import view.widgets.custom.OrbWidget;
import view.widgets.listeners.HoverListener;

public class WidgetLayer extends MTComponent {
	private final SuggestableScene scene;
	
	public WidgetLayer(SuggestableScene scene) {
		super(scene.getMTApplication(), new MTCamera(scene.getMTApplication()));
		this.scene = scene;
		
		float x = scene.getMTApplication().getWidth()/2;
		float y = scene.getMTApplication().getHeight()/2;
		
		OrbWidget orb = new OrbWidget(x, y, scene.getMTApplication());
		orb.addButton(new ButtonTimeline(scene));
		orb.addButton(new ButtonKeywords(scene));
		ButtonRemove btn = new ButtonRemove(scene);
		orb.addButton(btn);
		scene.registerPreDrawAction(new HoverListener(scene, btn));
		orb.addButton(new ButtonClearTable(scene));
		orb.addButton(new ButtonTest(scene));
		addChild(orb);
	}
}
