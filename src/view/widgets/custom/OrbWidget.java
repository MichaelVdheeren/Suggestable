package view.widgets.custom;

import java.util.ArrayList;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.widgets.MTSvg;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import view.widgets.actions.HoverPreDrawAction;
import controllers.SuggestableScene;

public class OrbWidget extends MTEllipse {
	private ArrayList<OrbButton> buttonList = new ArrayList<OrbButton>();
	private final MTSvg trash;

	public OrbWidget(SuggestableScene scene, float x, float y) {
		super(scene.getMTApplication(), new Vector3D(x, y), 100,100);
		
		this.setFillColor(new MTColor(0, 0, 0, 200));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		
		trash = new MTSvg(scene.getMTApplication(), "data/icons/trash.svg");
		this.addChild(trash);
		trash.setPositionRelativeToParent(this.getCenterPointLocal());
		trash.setPickable(false);
		
		scene.registerPreDrawAction(new HoverPreDrawAction(scene, trash, trash.getCenterPointGlobal()));
		
//		Animation anim = new Animation("rotate", new MultiPurposeInterpolator( 0,360, 4000, 0, 1f, -1), svg);
//		anim.addAnimationListener(new IAnimationListener() {
//			public void processAnimationEvent(AnimationEvent ae) {
//				float factor = ae.getDelta();
//				MTSvg target = (MTSvg)ae.getTarget();
//				target.rotateZ(target.getCenterPointGlobal(), factor,TransformSpace.GLOBAL);
//			}
//		});
//		anim.start();
		this.removeAllGestureEventListeners();
	}
	
	public MTSvg getTrash() {
		return trash;
	}
	
	/**
	 * Add a single button to the orb
	 * @param 	button
	 * 			The button to add to the orb
	 */
	public void addButton(OrbButton button) {
		OrbButton[] buttons = new OrbButton[1];
		buttons[0] = button;
		this.addButtons(buttons);
	}
	
	/**
	 * Add multiple buttons at once to the orb
	 * @param 	buttons
	 * 			An array of buttons to add to the orb
	 */
	public void addButtons(OrbButton[] buttons) {
		float t;
		
		// Reset the rotation
		if (buttonList.size() != 0) {
			t = 360/buttonList.size();
			for (int i=0; i<buttonList.size(); i++)
				buttonList.get(i).rotateZ(this.getCenterPointGlobal(), -i*t);
		}
		
		for (OrbButton button : buttons) {
			Vector3D anchor = this.getCenterPointGlobal();
			anchor.translate(new Vector3D(0,-(this.getHeightXYGlobal()/2+button.getHeightXY(TransformSpace.GLOBAL)),0));
			buttonList.add(button);
			this.addChild(button);
			button.setPositionGlobal(anchor);
		}

		// Now rotate again
		t = 360/buttonList.size();
		for (int i=0; i<buttonList.size(); i++)
			buttonList.get(i).rotateZ(this.getCenterPointGlobal(), i*t);
	}
	
	public void removeButtons() {
		
	}
}
