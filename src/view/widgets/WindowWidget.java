package view.widgets;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public abstract class WindowWidget extends MTRoundRectangle {
	public WindowWidget(float x, float y, float w, float h, PApplet pApplet) {
		super(pApplet, x, y,  0, w, h, 5, 5);
		
		this.setFillColor(new MTColor(0, 0, 0, 100));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));

		IFont font = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255));	//Font color
		
		MTTextArea text = new MTTextArea(pApplet, font);
		text.setText("Widget title");
		text.setPositionGlobal(new Vector3D(x+text.getWidthXY(TransformSpace.GLOBAL)/2, y+15));
		text.setNoStroke(true);
		text.setNoFill(true);
		text.setPickable(false);
		this.addChild(text);
		
		
		final MTSvgButton btnClose = new MTSvgButton(pApplet, "data/icons/close.svg");
		btnClose.setSizeXYGlobal(20, 20);
		btnClose.setPositionGlobal(new Vector3D(x+w-15, y+15));
		btnClose.removeAllGestureEventListeners();
		btnClose.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent e) {
				if (e.getId() == MTGestureEvent.GESTURE_STARTED)
					destroy();
				
				return true;
			}
		});
		
		this.addChild(btnClose);
		
	}
}