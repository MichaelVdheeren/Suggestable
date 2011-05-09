package view.elements;

import java.io.IOException;

import javax.media.opengl.GL;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.bounds.IBoundingShape;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTImage;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Ray;
import org.mt4j.util.math.Vector3D;

import processing.core.PGraphics;
import processing.core.PImage;
import view.components.MTSpinner;
import view.elements.gestures.DragElementListener;
import view.elements.gestures.TrashRetrievedElementListener;
import bookshelf.apis.libis.LibisBook;
import controllers.SuggestableScene;

public class RetrievedElement extends AbstractElement {
	private final LibisBook book;
	private final MTRoundRectangle child;
	private final MTSpinner spinner;
	
	public RetrievedElement(SuggestableScene scene,float s, LibisBook book) {
		super(scene);
		this.child = new MTRoundRectangle(scene.getMTApplication(), 0, 0, 0, s, s, 5, 5);
		
		this.book = book;
		this.addChild(child);
		this.setVertices(child.getVerticesLocal());
		
		child.setFillColor(new MTColor(0, 0, 0, 200));
		child.setStrokeWeight(2.5f);
		child.setStrokeColor(new MTColor(255, 255, 255, 150));
		child.setComposite(true);
		child.setBoundsAutoCompute(true);
		
		try {
			final MTImage cover = new MTImage(scene.getMTApplication(), new PImage(getBook().getCover()));
			child.addChild(cover);
			cover.setPositionRelativeToParent(child.getCenterPointLocal());
			cover.setStrokeColor(new MTColor(0, 255, 255, 150));
			float scaleFactor = child.getHeightXY(TransformSpace.GLOBAL)/cover.getHeightXY(TransformSpace.GLOBAL);
			cover.scaleGlobal(scaleFactor, scaleFactor, 1, cover.getCenterPointGlobal());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		spinner = new MTSpinner(scene.getMTApplication(), new Vector3D(s/2, s/2), s*1/16, s*3/16, 12);
		child.addChild(spinner);
		spinner.setVisible(false);
		
		addGestureListener(DragProcessor.class, new DragElementListener(this));
		addGestureListener(DragProcessor.class, new TrashRetrievedElementListener(scene,this));
	}
	
	@Override
	public LibisBook getBook() {
		return this.book;
	}
	
//	public float getGForce() {
//		float currentRadius = this.getWidthXYGlobal()/2;
//		float gForce = 9.81f*currentRadius/radius;
////		System.out.println("Current G-Force: "+gForce);
//		return gForce;
//	}

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
	
	public void setLoading(boolean loading) {
		this.spinner.setVisible(loading);
		
		if (loading)
			this.spinner.start();
		else
			this.spinner.stop();
	}
}