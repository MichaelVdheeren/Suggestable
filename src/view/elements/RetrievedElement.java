package view.elements;

import javax.media.opengl.GL;

import org.mt4j.components.bounds.IBoundingShape;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Ray;
import org.mt4j.util.math.Vector3D;

import processing.core.PGraphics;
import view.elements.gestures.DragElementListener;
import view.elements.gestures.TrashRetrievedElementListener;
import bookshelf.apis.libis.LibisBook;
import controllers.SuggestableScene;

public class RetrievedElement extends AbstractElement {
	private final LibisBook book;
	private final float radius;
	private final MTEllipse child;
	private final MTTextArea text;
	private boolean composition = false;
	
	public RetrievedElement(SuggestableScene scene,float r, LibisBook book) {
		super(scene);
		this.child = new MTEllipse(scene.getMTApplication(), new Vector3D(0,0,0), r, r);
		this.book = book;
		this.radius = r;
		
		this.addChild(child);
		this.setVertices(child.getVerticesLocal());
		
		child.setFillColor(new MTColor(0, 0, 0, 200));
		child.setStrokeWeight(2.5f);
		child.setStrokeColor(new MTColor(255, 255, 255, 150));
		child.setComposite(true);
		child.setBoundsAutoCompute(true);
		
		IFont font = FontManager.getInstance().createFont(scene.getMTApplication(), "fonts/Trebuchet MS.ttf", 
				9, 	//Font size
				new MTColor(255,255,255));	//Font color
		
		Vector3D t = getCenterPointGlobal().addLocal(new Vector3D(-r,0,0));
		t.rotateZ(getCenterPointGlobal(), 45);
		float dx = t.getX();
		float dy = t.getY();
		
		text = new MTTextArea(scene.getMTApplication(), dx, dy, r-dx/2, r-dy/2, font);
		text.setNoStroke(true);
		text.setNoFill(true);
		text.setText(getBook().getTitle());
		//text.setPositionGlobal(center);
		this.addChild(text);
		
		addGestureListener(DragProcessor.class, new DragElementListener(this));
		addGestureListener(DragProcessor.class, new TrashRetrievedElementListener(scene,this));
		
		registerInputProcessor(new TapProcessor(scene.getMTApplication()));
		addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent) ge;
				if (te.getId() == TapEvent.GESTURE_ENDED) {
					setComposition(!isComposition());
				}
				
				return true;
			}
		});
		
//		registerInputProcessor(new TapAndHoldProcessor(scene.getMTApplication()));
//		addGestureListener(TapAndHoldProcessor.class, new IGestureEventListener() {
//			@Override
//			public boolean processGestureEvent(MTGestureEvent ge) {
//				// TODO: show resize helper animation!
//				return true;
//			}
//		});
	}
	
	@Override
	public LibisBook getBook() {
		return this.book;
	}
	
	public float getGForce() {
		float currentRadius = this.getWidthXYGlobal()/2;
		float gForce = 9.81f*currentRadius/radius;
//		System.out.println("Current G-Force: "+gForce);
		return gForce;
	}

	@Override
	protected void drawPureGl(GL gl) {
		// Nothing.
	}

	@Override
	public Vector3D getGeometryIntersectionLocal(Ray ray) {
		return child.getGeometryIntersectionLocal(ray);
	}

	@Override
	public boolean isGeometryContainsPointLocal(Vector3D testPoint) {
		return child.isGeometryContainsPointLocal(testPoint);
	}

	@Override
	public Vector3D getCenterPointLocal() {
		return child.getCenterPointLocal();
	}

	@Override
	protected void destroyComponent() {
		// Nothing.
	}

	@Override
	public void drawComponent(PGraphics g) {
		// Nothing.
	}
	
	@Override
	public IBoundingShape getBounds() {
		return child.getBounds();
	}
	
	public boolean isComposition() {
		return this.composition;
	}
	
	private void setComposition(boolean composition) {
		this.composition = composition;
	}
}