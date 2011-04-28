package view.layers;

import org.mt4j.components.visibleComponents.widgets.MTOverlayContainer;
import org.mt4j.util.math.Vector3D;

import view.widgets.buttons.ButtonClearTable;
import view.widgets.buttons.ButtonKeywords;
import view.widgets.buttons.ButtonTest;
import view.widgets.buttons.ButtonTimeline;
import view.widgets.specific.OrbWidget;
import controllers.SuggestableScene;

public class WidgetLayer extends MTOverlayContainer {
//	private final OrbWidget orbWidget;
	
	public WidgetLayer(SuggestableScene scene) {
		super(scene.getMTApplication());
		
		float x = scene.getMTApplication().getWidth()/2;
		float y = scene.getMTApplication().getHeight()/2;
		
		OrbWidget orbWidget = new OrbWidget(scene, x, y);
		orbWidget.addButton(new ButtonTimeline(scene));
		orbWidget.addButton(new ButtonKeywords(scene));
		orbWidget.addButton(new ButtonClearTable(scene));
		orbWidget.addButton(new ButtonTest(scene));
		addChild(orbWidget);
		
		orbWidget.setPositionRelativeToParent(new Vector3D(0, y));
	}
	
//	public OrbWidget getOrbWidget() {
//		return this.orbWidget;
//	}
}
