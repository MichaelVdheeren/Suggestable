package view.components;

import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.util.MTColor;

import processing.core.PApplet;

public class Suggestion extends MTRoundRectangle {

	public Suggestion(float x, float y, float z, float w, float h, float aw, float ah, PApplet pApplet) {
		super(x, y, z, w, h, aw, ah, pApplet);
		// TODO Auto-generated constructor stub
		
		this.setFillColor(new MTColor(0, 0, 0, 200));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
	}

}
