package view.components;

import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTSvg;

import processing.core.PApplet;

public class MTStarIndicator extends MTRectangle {
	private float value;
	private final float margin = 2;
	private MTSvg stars[] = new MTSvg[5];
	
	public MTStarIndicator(PApplet pApplet, float width, float height) {
		super(pApplet, 0, 0, 0, width, height);
		setComposite(true);
		float sWidth = (width-9*margin)/10;
		
		this.setNoFill(true);
		this.setNoStroke(true);
	}
	
	public void setValue(float value) {

	}
	
	public float getValue() {
		return this.value;
	}
}