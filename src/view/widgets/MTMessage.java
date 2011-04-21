package view.widgets;

import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;

import processing.core.PApplet;

public class MTMessage extends MTRoundRectangle {

	public MTMessage(PApplet pApplet, float width,float height) {
		super(pApplet, 0,0,0, width, height, 5, 5);
		

		this.setFillColor(new MTColor(0, 0, 0, 255));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		
		IFont font = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255));	//Font color
		
		MTTextArea text = new MTTextArea(pApplet, 0, 0, width, height, font);
		text.setText("test");
		text.setPositionGlobal(this.getCenterPointGlobal());
		text.setNoStroke(true);
		text.setNoFill(true);
		this.addChild(text);
		this.setComposite(true);
	}
}
