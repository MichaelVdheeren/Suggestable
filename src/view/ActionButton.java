package view;

import org.mt4j.components.visibleComponents.shapes.MTComplexPolygon;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vertex;

import processing.core.PApplet;

public class ActionButton extends MTRoundRectangle {

	public ActionButton(PApplet pApplet) {
		super(100, 200, 0, 100, 30, 5, 5, pApplet);
		
		float w = this.getWidthXYGlobal();
		float h = this.getHeightXYGlobal();
		float x = this.getCenterPointGlobal().x-w/2;
		float y = this.getCenterPointGlobal().y-h/2;
		
		float aw = 20;
		
		Vertex[] vertices = new Vertex[3];
		vertices[0] = new Vertex(x+w/2-aw/2, y+h);
		vertices[1] = new Vertex(x+w/2+aw/2, y+h);
		vertices[2] = new Vertex(x+w/2, y+h+aw/2);
		MTComplexPolygon polygon = new MTComplexPolygon(pApplet, vertices);
		
		this.addChild(polygon);
		polygon.setPickable(false);
		
		MTTextArea text = new MTTextArea(pApplet);
		text.setText("Timeline");
		text.setPositionGlobal(this.getCenterPointGlobal());
		text.setNoStroke(true);
		text.setNoFill(true);
		text.setPickable(false);
		text.setFillColor(new MTColor(0,0,0));
		this.addChild(text);
	}
	
}
