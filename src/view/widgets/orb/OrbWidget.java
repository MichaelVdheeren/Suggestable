package view.widgets.orb;

import java.util.ArrayList;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.widgets.MTSvg;
import org.mt4j.util.MTColor;
import org.mt4j.util.animation.Animation;
import org.mt4j.util.animation.AnimationEvent;
import org.mt4j.util.animation.IAnimationListener;
import org.mt4j.util.animation.MultiPurposeInterpolator;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public class OrbWidget extends MTEllipse {
	private ArrayList<OrbButton> actions = new ArrayList<OrbButton>();
	
	public OrbWidget(float x, float y, PApplet pApplet) {
		super(pApplet, new Vector3D(x, y), 100,100);
		
		this.setFillColor(new MTColor(0, 0, 0, 200));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		
		MTSvg svg = new MTSvg(pApplet, "data/icons/rfid-3.svg");
		
		svg.setPositionGlobal(this.getCenterPointGlobal());
		svg.scale(2.5f, 2.5f, 0, svg.getCenterPointGlobal());
		this.addChild(svg);
		svg.setPickable(false);
		
		Animation anim = new Animation("rotate", new MultiPurposeInterpolator( 0,360, 4000, 0, 1f, -1), svg);
		anim.addAnimationListener(new IAnimationListener() {
			public void processAnimationEvent(AnimationEvent ae) {
				float factor = ae.getCurrentStepDelta();
				MTSvg target = (MTSvg)ae.getTargetObject();
				target.rotateZ(target.getCenterPointGlobal(), factor,TransformSpace.GLOBAL);
			}
		});
		anim.start();
	}
	
	public void addButton(OrbButton button) {
		OrbButton[] buttons = new OrbButton[1];
		buttons[0] = button;
		this.addButtons(buttons);
	}
	
	public void addButtons(OrbButton[] buttons) {
		float t;
		
		// Reset the rotation
		if (actions.size() != 0) {
			t = 360/actions.size();
			for (int i=0; i<actions.size(); i++)
				actions.get(i).rotateZ(this.getCenterPointGlobal(), -i*t);
		}
		
		for (OrbButton button : buttons) {
			Vector3D anchor = this.getCenterPointGlobal();
			anchor.translate(new Vector3D(0,-(this.getHeightXYGlobal()/2+button.getHeightXY(TransformSpace.GLOBAL)),0));
			actions.add(button);
			this.addChild(button);
			button.setPositionGlobal(anchor);
		}

		// Now rotate again
		t = 360/actions.size();
		for (int i=0; i<actions.size(); i++)
			actions.get(i).rotateZ(this.getCenterPointGlobal(), i*t);
	}
	
	public void removeButtons() {
		
	}
}
