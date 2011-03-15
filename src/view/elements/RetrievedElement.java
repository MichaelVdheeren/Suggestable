package view.elements;

import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;
import view.elements.listeners.DragElementListener;
import bookshelf.AbstractBook;
import bookshelf.apis.libis.LibisBook;

public class RetrievedElement extends MTEllipse implements IElement {
	private final LibisBook book;
	private final float radius;
	private boolean dragged;
	
	public RetrievedElement(PApplet pApplet, float x, float y, float r, LibisBook book) {
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
		
		addGestureListener(DragProcessor.class, new DragElementListener(this));
	}
	
	@Override
	public AbstractBook getBook() {
		return this.book;
	}
	
	public float getGForce() {
		float currentRadius = this.getWidthXYGlobal()/2;
		return 9.81f*currentRadius/radius;
	}

	@Override
	public boolean isDragged() {
		return this.dragged;
	}

	@Override
	public void setDragged() {
		this.dragged = true;
	}

	@Override
	public void resetDragged() {
		this.dragged = false;
	}
	
	
}