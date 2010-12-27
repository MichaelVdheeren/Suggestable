package view.components;

import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public class Placeholder extends MTEllipse {

	public Placeholder(PApplet pApplet, Vector3D cp, float rx, float ry) {
		super(pApplet, cp, rx, ry);
		// TODO Auto-generated constructor stub
		
		this.setFillColor(new MTColor(0, 0, 0, 200));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
	}
}