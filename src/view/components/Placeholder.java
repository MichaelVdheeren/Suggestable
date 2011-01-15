package view.components;

import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public class Placeholder extends MTEllipse {
	private String description;
	
	public Placeholder(PApplet pApplet, Vector3D cp, float rx, float ry, String description) {
		super(pApplet, cp, rx, ry);
		setDescription(description);
		// TODO Auto-generated constructor stub
		
		this.setFillColor(new MTColor(0, 0, 0, 200));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		
		IFont font = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255),  //Font fill color
				new MTColor(255,255,255,180));	//Font outline color
		
		MTTextArea text = new MTTextArea(pApplet, font);
		text.setText(this.description);
		text.setPositionGlobal(this.getCenterPointGlobal());
	}
	
	public String getDescription() {
		return new String(description);
	}

	private void setDescription(String description) {
		this.description = new String(description);
	}
}