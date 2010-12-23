package view.widgets;

import java.util.ArrayList;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;
import view.widgets.controls.OrbButton;

public class OrbWidget extends MTEllipse {
	private ArrayList<OrbButton> actions = new ArrayList<OrbButton>();
	
	public OrbWidget(float x, float y, PApplet pApplet) {
		super(pApplet, new Vector3D(x, y), 100,100);
		
		this.setFillColor(new MTColor(0, 0, 0, 200));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
	}
	
	public void addAction(OrbButton action) {
		float d = 360;
		float t;
		
		// Reset the rotation
		t = d/actions.size();
		for (int i=0; i<actions.size(); i++)
			actions.get(i).rotateZ(this.getCenterPointGlobal(), -i*t);
		
		Vector3D anchor = this.getCenterPointGlobal();
		anchor.translate(new Vector3D(0,-(this.getHeightXYGlobal()/2+action.getHeightXY(TransformSpace.GLOBAL)),0));
		
		actions.add(action);
		this.addChild(action);
		action.setPositionGlobal(anchor);
		action.setPickable(false);

		// Now rotate again
		t = d/actions.size();
		for (int i=0; i<actions.size(); i++)
			actions.get(i).rotateZ(this.getCenterPointGlobal(), i*t);
	}
	
	public void deleteAction() {
		
	}
}
