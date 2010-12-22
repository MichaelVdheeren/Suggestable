package view;

import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTComplexPolygon;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.math.Vertex;

import processing.core.PApplet;

public class ActionButton extends MTRoundRectangle {

	public ActionButton(PApplet pApplet) {
		super(100, 200, 0, 100, 30, 5, 5, pApplet);
		
		this.setFillColor(new MTColor(0, 0, 0, 180));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		
		float w = this.getWidthXYGlobal();
		float h = this.getHeightXYGlobal();
		float x = this.getCenterPointGlobal().x-w/2;
		float y = this.getCenterPointGlobal().y-h/2;
		
		float aw = 20;
		
		Vertex[] vertices = new Vertex[3];
		vertices[0] = new Vertex(x+w/2-aw/2, y+h-1f);
		vertices[1] = new Vertex(x+w/2, y+h+aw/2);
		vertices[2] = new Vertex(x+w/2+aw/2, y+h-1f);
		
		MTComplexPolygon polygon = new MTComplexPolygon(pApplet, vertices);
		
		this.addChild(polygon);
		polygon.setPickable(false);
		
		polygon.setFillColor(new MTColor(0, 0, 0, 180));
		polygon.setStrokeWeight(2.5f);
		polygon.setStrokeColor(new MTColor(255, 255, 255, 150));
		
		IFont font = FontManager.getInstance().createFont(pApplet, "Trebuchet MS.ttf", 
				18, 	//Font size
				new MTColor(255,255,255),  //Font fill color
				new MTColor(255,255,255,180));	//Font outline color
		
		MTTextArea text = new MTTextArea(pApplet, font);
		text.setText("Timeline");
		text.setPositionGlobal(this.getCenterPointGlobal());
		text.setNoStroke(true);
		text.setNoFill(true);
		text.setPickable(false);
		this.addChild(text);
	}	
}
