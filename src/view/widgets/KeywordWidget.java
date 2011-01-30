package view.widgets;

import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.util.MTColor;

import processing.core.PApplet;

public class KeywordWidget extends WindowWidget {

	private final MTRoundRectangle cloud;
	
	public KeywordWidget(float x, float y, float w, float h, PApplet pApplet) {
		super(x, y, w, h, pApplet);
		
		cloud = new MTRoundRectangle(x+7.5f, y+30, 0, this.getWidthXYGlobal()-15, this.getHeightXYGlobal()-40, 5, 5, pApplet);
		cloud.setFillColor(new MTColor(0, 0, 0, 150));
		cloud.setNoStroke(true);
		cloud.removeAllGestureEventListeners();
		this.addChild(cloud);
		
		
	}

}
