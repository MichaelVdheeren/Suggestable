package view.components.specific;

import java.util.ArrayList;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.widgets.MTSvg;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import view.components.buttons.OrbButton;
import view.scene.SuggestableScene;


public class MTActionOrb extends MTEllipse {
	private ArrayList<OrbButton> buttonList = new ArrayList<OrbButton>();
	private final static float radius = 100;

	public MTActionOrb(SuggestableScene scene, float x, float y) {
		super(scene.getMTApplication(), new Vector3D(x, y,100), radius, radius);
		
		this.setFillColor(new MTColor(0, 0, 0, 255));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		
		MTSvg book = new MTSvg(scene.getMTApplication(), "data/icons/options.svg");
		this.addChild(book);
		book.setWidthXYGlobal(60);
		book.setPositionRelativeToParent(this.getCenterPointLocal().addLocal(new Vector3D(45, 0)));
		book.setPickable(false);
		
		this.removeAllGestureEventListeners();
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
			t = 90/(buttonList.size()+1);
			for (int i=0; i<buttonList.size(); i++)
				buttonList.get(i).rotateZ(this.getCenterPointGlobal(), 45-(i+1)*t);
		}
		
		for (OrbButton button : buttons) {
			Vector3D anchor = this.getCenterPointGlobal();
			anchor.translate(new Vector3D(this.getWidthXYGlobal()/2+button.getWidthXY(TransformSpace.GLOBAL)/2+button.getHeightXY(TransformSpace.GLOBAL)/2,0));
			buttonList.add(button);
			this.addChild(button);
			button.setPositionGlobal(anchor);
		}

		// Now rotate again
		t = 90/(buttonList.size()+1);
		for (int i=0; i<buttonList.size(); i++)
			buttonList.get(i).rotateZ(this.getCenterPointGlobal(), -45+(i+1)*t);
	}
	
	public void removeButtons() {
		
	}
}
