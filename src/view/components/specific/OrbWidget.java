package view.components.specific;

import java.util.ArrayList;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.widgets.MTSvg;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import controllers.SuggestableScene;

public class OrbWidget extends MTEllipse {
	private ArrayList<OrbButton> buttonList = new ArrayList<OrbButton>();
//	private final MTSvg trash, book;
	private final static float radius = 100;

	public OrbWidget(SuggestableScene scene, float x, float y) {
		super(scene.getMTApplication(), new Vector3D(x, y,100), radius, radius);
		
		this.setFillColor(new MTColor(0, 0, 0, 255));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		
		MTSvg book = new MTSvg(scene.getMTApplication(), "data/icons/book.svg");
		this.addChild(book);
		book.setWidthXYGlobal(60);
		book.setPositionRelativeToParent(this.getCenterPointLocal().addLocal(new Vector3D(45, 0)));
		book.setPickable(false);
//		
//		IFont font = FontManager.getInstance().createFont(scene.getMTApplication(), "fonts/Trebuchet MS.ttf", 
//				16, 	//Font size
//				new MTColor(255,255,255));	//Font color
//		
//		MTTextArea text = new MTTextArea(scene.getMTApplication(), font);
//		text.setNoFill(true);
//		text.setNoStroke(true);
//		text.setText("Place a book");
//		book.addChild(text);
//		text.setPositionRelativeToParent(book.getCenterPointLocal().addLocal(new Vector3D(0, 70)));
//		
//		MTTextArea text2 = new MTTextArea(scene.getMTApplication(), font);
//		text2.setNoFill(true);
//		text2.setNoStroke(true);
//		text2.setText("to start!");
//		book.addChild(text2);
//		text2.setPositionRelativeToParent(book.getCenterPointLocal().addLocal(new Vector3D(0, 90)));
		
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
