package view.widgets;

import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;

import processing.core.PApplet;

public class MTPanel extends MTRectangle {
	private MTSvgButton button;
	
	public MTPanel(PApplet pApplet, float width, float height) {
		super(pApplet, width, height);
		button = new MTSvgButton(pApplet, "data/icons/circle.svg");
		this.removeAllGestureEventListeners();
		// TODO Auto-generated constructor stub
	}
	
	public MTPanel(PApplet pApplet, float width, float height, MTSvgButton button) {
		this(pApplet,width,height);
		setButton(button);
	}
	
	public MTSvgButton getButton() {
		return this.button;
	}
	
	public void setButton(MTSvgButton button) {
		this.button=button;
		this.button.removeAllGestureEventListeners();
	}
}
