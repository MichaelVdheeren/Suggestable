package view.widgets;

import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public abstract class AbstractWindow extends MTRoundRectangle {
	private MTTextArea title;
	
	public AbstractWindow(PApplet pApplet, float x, float y, float w, float h) {
		super(pApplet, 0, 0,  0, w, h, 5, 5);
		this.setPositionGlobal(new Vector3D(x,y));
		
		this.setFillColor(new MTColor(0, 0, 0, 100));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));

		IFont font = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255));	//Font color
		
		title = new MTTextArea(pApplet, font);
		title.setPositionRelativeToParent(new Vector3D(0,0).addLocal(title.getCenterOfMass2DLocal()));
		title.setNoStroke(true);
		title.setNoFill(true);
		title.setPickable(false);
		this.addChild(title);
		
		final MTSvgButton btnClose = new MTSvgButton(pApplet, "data/icons/close.svg");
		btnClose.setSizeXYGlobal(20, 20);
		btnClose.setPositionRelativeToParent(new Vector3D(-15,15).addLocal(new Vector3D(w,0)));
		btnClose.removeAllGestureEventListeners();
		btnClose.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent) ge;
				if (te.getTapID() == TapEvent.TAPPED) {
					setVisible(false);
				}
				
				return true;
			}
		});
		
		this.addChild(btnClose);
		
	}
	
	public AbstractWindow(PApplet pApplet, float x, float y, float w, float h, String title) {
		this(pApplet, x, y, w, h);
		this.setTitle(title);
	}

	public String getTitle() {
		return title.getText();
	}

	public void setTitle(String title) {
		this.title.setText(title);
		
	}
}