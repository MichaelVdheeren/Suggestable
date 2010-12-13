package view.components;

import org.mt4j.components.MTComponent;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public class ActionOrb extends MTComponent {
	public ActionOrb(float x, float y, PApplet pApplet) {
		super(pApplet);
		
		MTEllipse ellipse = new MTEllipse(pApplet, new Vector3D(x, y), 50, 50);
		ellipse.setStrokeWeight(10);
		ellipse.setStrokeColor(new MTColor(255, 255, 255, 100));
		
		this.addChild(ellipse);
	}
}
