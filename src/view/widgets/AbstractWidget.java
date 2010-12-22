package view.widgets;

import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public abstract class AbstractWidget extends MTRoundRectangle {
	public AbstractWidget(float x, float y, float w, float h, PApplet pApplet) {
		super(x, y,  0, w, h, 5, 5, pApplet);
		
		this.setFillColor(new MTColor(0, 0, 0, 180));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		
		CloseButton btnClose = new CloseButton(pApplet, new Vector3D(x+w-15, y+15));
		btnClose.setPickable(false);
		this.addChild(btnClose);
	}
}