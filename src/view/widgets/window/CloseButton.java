package view.widgets.window;

import org.mt4j.components.MTComponent;
import org.mt4j.components.visibleComponents.shapes.MTLine;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public class CloseButton extends MTComponent {
	static float s = 12f;
	static float w = 2f;
	private Vector3D center;
	
	public CloseButton(PApplet pApplet, Vector3D center) {
		super(pApplet);
		this.center = center;
		MTLine tLbR = new MTLine(pApplet, center.x-s/2, center.y-s/2, center.x+s/2, center.y+s/2);
		tLbR.setStrokeWeight(w);
		this.addChild(tLbR);
		
		MTLine tRbL = new MTLine(pApplet, center.x+s/2, center.y-s/2, center.x-s/2, center.y+s/2);
		tRbL.setStrokeWeight(w);
		this.addChild(tRbL);
		
		this.setComposite(true);
	}
}
