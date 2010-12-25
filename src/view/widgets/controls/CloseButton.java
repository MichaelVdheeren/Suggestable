package view.widgets.controls;

import org.mt4j.components.MTComponent;
import org.mt4j.components.visibleComponents.shapes.MTLine;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public class CloseButton extends MTComponent {
	static float s = 15f;
	static float w = 3.5f;
	
	public CloseButton(PApplet pApplet, Vector3D center) {
		super(pApplet);
		
		MTLine tLbR = new MTLine(pApplet, center.x-s/2, center.y-s/2, center.x+s/2, center.y+s/2);
		tLbR.removeAllGestureEventListeners();
		tLbR.setStrokeWeight(w);
		this.addChild(tLbR);
		
		MTLine tRbL = new MTLine(pApplet, center.x+s/2, center.y-s/2, center.x-s/2, center.y+s/2);
		tRbL.removeAllGestureEventListeners();
		tRbL.setStrokeWeight(w);
		this.addChild(tRbL);
	}
}
