package view.elements;

import java.util.List;

import org.mt4j.components.PickResult.PickEntry;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import controllers.SuggestableScene;

import processing.core.PApplet;
import view.elements.listeners.DragElementListener;
import view.widgets.buttons.ButtonRemove;
import bookshelf.AbstractBook;
import bookshelf.apis.libis.LibisBook;

public class RetrievedElement extends MTEllipse implements IElement {
	private final LibisBook book;
	private final float radius;
	private boolean dragged;
	private final SuggestableScene scene;
	
	public RetrievedElement(SuggestableScene scene, float x, float y, float r, LibisBook book) {
		super(scene.getMTApplication(), new Vector3D(x,y,0), r, r);
		this.book = book;
		this.radius = r;
		this.scene = scene;
		
		this.setComposite(true);
		this.setFillColor(new MTColor(0, 0, 0, 200));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		
		IFont font = FontManager.getInstance().createFont(getScene().getMTApplication(), "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255));	//Font color
		
		Vector3D t = getCenterPointGlobal().addLocal(new Vector3D(-r,0,0));
		t.rotateZ(getCenterPointGlobal(), 45);
		float dx = t.getX()-x;
		float dy = t.getY()-y;
		
		MTTextArea text = new MTTextArea(getScene().getMTApplication(), x+dx, y+dy, r-dx/2, r-dy/2, font);
		text.setNoStroke(true);
		text.setNoFill(true);
		text.setText(getBook().getTitle());
		//text.setPositionGlobal(center);
		this.addChild(text);
		
		final RetrievedElement self = this;
		addGestureListener(DragProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				DragEvent de = (DragEvent) ge;
				// Check if gestured ended on top of this
				// Then check if we are dropping a suggestion on this
				if (de.getId() == DragEvent.GESTURE_ENDED) {
					
					Vector3D location = de.getTo();
					List<PickEntry> components = getScene().widgetLayer.pick(location.x, location.y).getPickList();
					
					for( PickEntry pe : components ) {
                        if (pe.hitObj instanceof ButtonRemove)
                        	getScene().removeRetrievedElement(self);
                    }
				}
				
				return true;
			}
		});
	}
	
	@Override
	public AbstractBook getBook() {
		return this.book;
	}
	
	private SuggestableScene getScene() {
		return this.scene;
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