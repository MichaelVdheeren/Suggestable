package view.widgets;

import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.util.MTColor;

import processing.core.PApplet;

public class Timeline extends MTRoundRectangle {
	public Timeline(float x, float y, PApplet pApplet) {
		super(x, y,  0, 400, 200, 5, 5, pApplet);
		
		this.setFillColor(new MTColor(0, 0, 0, 180));
		this.setStrokeWeight(10);
		this.setStrokeColor(new MTColor(0, 0, 0, 100));
		
		float interspacing = 5;
		int amount = 10;
		float size = (this.getWidthXYGlobal()-2*this.getStrokeWeight())/amount;
		
		for (int i=0; i<amount; i++) {
			float bx = this.getCenterPointGlobal().x - this.getWidthXYGlobal()/2 + interspacing + i*size;
			float by = this.getCenterPointGlobal().y + this.getHeightXYGlobal()/2 - this.getStrokeWeight() - 50; 
			MTRoundRectangle bar = new MTRoundRectangle(bx, by, 0, size-2*interspacing, 50, 5, 5, pApplet);
			bar.setFillColor(new MTColor(255,255,255));
			bar.setStrokeWeight(5);
			bar.setStrokeColor(new MTColor(255,255,255, 100));
			this.addChild(bar);
		}
	}
}
