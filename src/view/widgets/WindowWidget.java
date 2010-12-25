package view.widgets;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.IMTInputEventListener;
import org.mt4j.input.inputData.MTInputEvent;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;
import view.widgets.controls.CloseButton;

public abstract class WindowWidget extends MTRoundRectangle {
	public WindowWidget(float x, float y, float w, float h, PApplet pApplet) {
		super(x, y,  0, w, h, 5, 5, pApplet);
		
		this.setFillColor(new MTColor(0, 0, 0, 180));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		
		final CloseButton btnClose = new CloseButton(pApplet, new Vector3D(x+w-15, y+15));
		btnClose.removeAllGestureEventListeners();
		btnClose.sendToFront();
		btnClose.addInputListener(new IMTInputEventListener() {
			
			@Override
			public boolean processInputEvent(MTInputEvent evt) {
				destroy();
				return true;
			}
		});
		
		this.addChild(btnClose);
		
		IFont font = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255),  //Font fill color
				new MTColor(255,255,255,180));	//Font outline color
		
		MTTextArea text = new MTTextArea(pApplet, font);
		text.setText("Widget title");
		text.setPositionGlobal(new Vector3D(x+text.getWidthXY(TransformSpace.GLOBAL)/2, y+15));
		text.setNoStroke(true);
		text.setNoFill(true);
		this.addChild(text);
	}
}