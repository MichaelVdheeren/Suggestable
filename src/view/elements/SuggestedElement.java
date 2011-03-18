package view.elements;

import java.io.IOException;
import java.util.ArrayList;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTImage;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.sceneManagement.IPreDrawAction;
import org.mt4j.util.MTColor;

import processing.core.PImage;
import view.elements.listeners.DragElementListener;
import view.widgets.custom.InformationWidget;
import bookshelf.apis.google.GoogleBook;
import controllers.SuggestableScene;

public class SuggestedElement extends MTRoundRectangle implements IElement {
	private final GoogleBook book;
	private final SuggestableScene scene;
	private boolean dragged;
	private ArrayList<IPreDrawAction> preDrawActions = new ArrayList<IPreDrawAction>();
	
	public SuggestedElement(final SuggestableScene scene, final float x, final float y, float s, GoogleBook book) {
		super(scene.getMTApplication(), x, y, 0, s, s, 5, 5);

		this.book = book;
		this.scene = scene;
		
		this.setComposite(true);
		this.setFillColor(new MTColor(0, 0, 0, 200));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		
		try {
			MTImage cover = new MTImage(getScene().getMTApplication(), new PImage(getBook().getCover()));
			this.addChild(cover);
			cover.setPositionGlobal(this.getCenterPointGlobal());
			cover.setStrokeColor(new MTColor(0, 255, 255, 150));
			float scaleFactor = this.getHeightXYGlobal()/cover.getHeightXY(TransformSpace.GLOBAL);
			cover.scaleGlobal(scaleFactor, scaleFactor, 1, cover.getCenterPointGlobal());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		registerInputProcessor(new TapProcessor(scene.getMTApplication()));
		addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent) ge;
				
				if (te.getTapID() == TapEvent.TAPPED) {
					getParent().addChild(new InformationWidget(getScene().getMTApplication(), getCenterPointGlobal().getX(), 
							getCenterPointGlobal().getY(), 400, 200, getBook()));
				}
				return true;
			}
		});
		
		addGestureListener(DragProcessor.class, new DragElementListener(this));
	}

	public GoogleBook getBook() {
		return this.book;
	}

	protected SuggestableScene getScene() {
		return scene;
	}
	
	public void registerPreDrawAction(IPreDrawAction action) {
		this.preDrawActions.add(action);
	}
	
	public ArrayList<IPreDrawAction> getPreDrawActions() {
		return this.preDrawActions;
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