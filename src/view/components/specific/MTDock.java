package view.components.specific;

import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.util.MTColor;

import controllers.SuggestableScene;

public class MTDock extends MTRectangle {

	public MTDock(SuggestableScene scene, float height) {
		super(scene.getMTApplication(), scene.getMTApplication().getWidth()+2.5f, height);
		this.setFillColor(new MTColor(0, 0, 0, 255));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
	}

}
