package view.universe;

import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;
import bookshelf.AbstractBook;
import bookshelf.apis.libis.LibisBook;

public class Placeholder extends MTEllipse {
	private final LibisBook book;
	private final float radius;
	
	public Placeholder(PApplet pApplet, float x, float y, float r, LibisBook book) {
		super(pApplet, new Vector3D(x,y,0), r, r);
		this.book = book;
		this.radius = r;
		
		this.setComposite(true);
		this.setFillColor(new MTColor(0, 0, 0, 200));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		
		IFont font = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255));	//Font color
		
		Vector3D t = getCenterPointGlobal().addLocal(new Vector3D(-r,0,0));
		t.rotateZ(getCenterPointGlobal(), 45);
		float dx = t.getX()-x;
		float dy = t.getY()-y;
		
		MTTextArea text = new MTTextArea(pApplet, x+dx, y+dy, r-dx/2, r-dy/2, font);
		text.setNoStroke(true);
		text.setNoFill(true);
		text.setText(getBook().getTitle());
		//text.setPositionGlobal(center);
		this.addChild(text);
	}
	
	public AbstractBook getBook() {
		return this.book;
	}
	
	public float getGForce() {
		return this.getWidthXYGlobal()/this.radius;
	}
	
	
}