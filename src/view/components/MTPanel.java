package view.components;

import org.mt4j.components.visibleComponents.shapes.MTRectangle;

import processing.core.PApplet;

public class MTPanel extends MTRectangle {
	private MTPanelButton button;
	
	public MTPanel(PApplet pApplet, float width, float height) {
		super(pApplet, width, height);
		button = new MTPanelButton(pApplet, "data/icons/circle.svg");
		this.removeAllGestureEventListeners();
		// TODO Auto-generated constructor stub
	}
	
	public MTPanel(PApplet pApplet, float width, float height, MTPanelButton button) {
		this(pApplet,width,height);
		setButton(button);
	}
	
	public MTPanelButton getButton() {
		return this.button;
	}
	
	public void setButton(MTPanelButton button) {
		this.button=button;
		this.button.removeAllGestureEventListeners();
	}
}
