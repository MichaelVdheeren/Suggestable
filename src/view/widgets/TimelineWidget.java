package view.widgets;

import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.util.MTColor;

import processing.core.PApplet;

public class TimelineWidget extends AbstractWidget {
	public TimelineWidget(float x, float y, PApplet pApplet) {
		super(x, y, 400, 200, pApplet);
		
		float interspacing = 5;
		int amount = 15;
		float size = (this.getWidthXYGlobal()-2*this.getStrokeWeight())/amount;
		
		for (int i=0; i<amount; i++) {
			float bx = this.getCenterPointGlobal().x - this.getWidthXYGlobal()/2 + this.getStrokeWeight() + interspacing + i*size;
			float by = this.getCenterPointGlobal().y + this.getHeightXYGlobal()/2 - this.getStrokeWeight() - 50; 
			MTRoundRectangle bar = new MTRoundRectangle(bx, by, 0, size-2*interspacing, 50, 5, 5, pApplet);
			bar.setFillColor(new MTColor(255,255,255));
			bar.setStrokeWeight(5);
			bar.setStrokeColor(new MTColor(255,255,255, 100));
			bar.setPickable(false);
			this.addChild(bar);
		}
	}
}
