package view.universe;

import java.io.IOException;
import java.util.List;

import org.mt4j.components.PickResult.PickEntry;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTImage;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PImage;
import view.SuggestableScene;
import view.widgets.buttons.ButtonRemove;
import view.widgets.custom.InformationWidget;
import bookshelf.apis.google.GoogleBook;

public class Suggestion extends MTRoundRectangle {
	private final GoogleBook book;
	private final SuggestableScene scene;
	
	public Suggestion(final SuggestableScene scene, final float x, final float y, float s, GoogleBook book) {
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
		
		addGestureListener(DragProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				DragEvent de = (DragEvent) ge;
				// Check if gestured ended on top of this
				// Then check if we are dropping a suggestion on this
				if (de.getId() == DragEvent.GESTURE_ENDED) {
					
					Vector3D location = de.getTo();
					List<PickEntry> components = scene.getCanvas().pick(location.x, location.y).getPickList();
					
					for( PickEntry pe : components ) {
                        if (pe.hitObj instanceof ButtonRemove)
                        	destroy();
                    }
				}
				
				return true;
			}
		});
		
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
	}

	public GoogleBook getBook() {
		return this.book;
	}

	protected SuggestableScene getScene() {
		return scene;
	}
}
