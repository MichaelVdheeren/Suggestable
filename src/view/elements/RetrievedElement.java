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
import rfid.idtronic.evo.desktop.hf.EDHFReply;
import view.components.MTSpinner;
import view.elements.gestures.DragElementListener;
import view.elements.gestures.TrashRetrievedElementListener;
import view.scene.SuggestableScene;
import bookshelf.AbstractBook;

public class RetrievedElement extends AbstractElement {
	private final AbstractBook book;
	private final MTRoundRectangle child;
	private final MTSpinner spinner;
	private final EDHFReply tag;
	
	public RetrievedElement(SuggestableScene scene, EDHFReply tag, AbstractBook book, float s) {
		super(scene);
		this.child = new MTRoundRectangle(scene.getMTApplication(), 0, 0, 0, s, s, 5, 5);
		this.tag = tag;
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		spinner = new MTSpinner(scene.getMTApplication(), new Vector3D(s/2, s/2), s*1/16, s*3/16, 12);
		child.addChild(spinner);
		spinner.setVisible(false);
		
		addGestureListener(DragProcessor.class, new DragElementListener(this));
		addGestureListener(DragProcessor.class, new TrashRetrievedElementListener(scene,this));
	}
	
	@Override
	public AbstractBook getBook() {
		return this.book;
	}
	
	public EDHFReply getTag() {
		return this.tag;
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
	
	public void setLoading(boolean loading) {
		this.spinner.setVisible(loading);
		
		if (loading)
			this.spinner.start();
		else
			this.spinner.stop();
	}
}