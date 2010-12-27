package view.widgets.window;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public abstract class WindowWidget extends MTRoundRectangle {
	public WindowWidget(float x, float y, float w, float h, PApplet pApplet) {
		super(x, y,  0, w, h, 5, 5, pApplet);
		
		this.setFillColor(new MTColor(0, 0, 0, 180));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		

		IFont font = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255),  //Font fill color
				new MTColor(255,255,255,180));	//Font outline color
		
		MTTextArea text = new MTTextArea(pApplet, font);
		text.setText("Widget title");
		text.setPositionGlobal(new Vector3D(x+text.getWidthXY(TransformSpace.GLOBAL)/2, y+15));
		text.setNoStroke(true);
		text.setNoFill(true);
		text.setPickable(false);
		this.addChild(text);
		
		
		final CloseButton btnClose = new CloseButton(pApplet, new Vector3D(x+w-15, y+15));
		btnClose.removeAllGestureEventListeners();
		btnClose.registerInputProcessor(new TapProcessor(pApplet));
		btnClose.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent e) {
				if (e.getId() == MTGestureEvent.GESTURE_DETECTED)
					destroy();
				
				return true;
			}
		});
		
		this.addChild(btnClose);
		
	}
}