package view.widgets.specific;

import org.mt4j.components.visibleComponents.widgets.MTSvg;

import processing.core.PApplet;

public class MTTrashCan extends MTSvg {
	private float scale = 1/1.2f;
	private boolean hovered = false;
	
	public MTTrashCan(PApplet applet) {
		super(applet, "data/icons/trash.svg");
		// TODO Auto-generated constructor stub
	}
	
	public void setHovered(boolean hovered) {
		if (hovered == this.hovered)
			return;
		
		scale = 1/scale;
		scaleGlobal(scale, scale, 1f, getCenterPointGlobal());
		this.hovered = hovered;
	}
	
	public boolean isHovered() {
		return this.hovered;
	}
}
