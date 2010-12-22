package view.components;

import org.mt4j.components.MTComponent;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public class ActionOrb extends MTComponent {
	public ActionOrb(float x, float y, PApplet pApplet) {
		super(pApplet);
		
		float s = 70;
		MTEllipse ellipse = new MTEllipse(pApplet, new Vector3D(x, y), s, s);
		ellipse.setFillColor(new MTColor(0, 0, 0, 180));
		ellipse.setStrokeWeight(10);
		ellipse.setStrokeColor(new MTColor(0, 0, 0, 100));
		
		this.addChild(ellipse);
	}
	
	public void addAction() {
		
	}
	
	public void deleteAction() {
		
	}
}
