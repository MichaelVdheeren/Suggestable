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
	private final OrbWidget orbWidget;
	
	public WidgetLayer(SuggestableScene scene) {
		super(scene.getMTApplication(), new MTCamera(scene.getMTApplication()));
		
		float x = scene.getMTApplication().getWidth()/2;
		float y = scene.getMTApplication().getHeight()/2;
		
		orbWidget = new OrbWidget(scene, x, y);
		orbWidget.addButton(new ButtonTimeline(scene));
		orbWidget.addButton(new ButtonKeywords(scene));
		orbWidget.addButton(new ButtonClearTable(scene));
		orbWidget.addButton(new ButtonTest(scene));
		addChild(orbWidget);
	}
	
	public OrbWidget getOrbWidget() {
		return this.orbWidget;
	}
}
