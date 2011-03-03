package view.universe;

import java.io.IOException;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTImage;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;

import processing.core.PApplet;
import processing.core.PImage;
import view.widgets.custom.InformationWidget;
import bookshelf.apis.google.GoogleBook;

public class Suggestion extends MTRoundRectangle {
	private final GoogleBook book;
	private final PApplet pApplet;
	
	public Suggestion(final PApplet pApplet, final float x, final float y, float s, GoogleBook book) {
		super(pApplet, x, y, 0, s, s, 5, 5);

		this.book = book;
		this.pApplet = pApplet;
		
		this.setComposite(true);
		this.setFillColor(new MTColor(0, 0, 0, 200));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		
		try {
			MTImage cover = new MTImage(getpApplet(), new PImage(getBook().getCover()));
			this.addChild(cover);
			cover.setPositionGlobal(this.getCenterPointGlobal());
			cover.setStrokeColor(new MTColor(0, 255, 255, 150));
			float scaleFactor = this.getHeightXYGlobal()/cover.getHeightXY(TransformSpace.GLOBAL);
			cover.scaleGlobal(scaleFactor, scaleFactor, 1, cover.getCenterPointGlobal());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		addGestureListener(DragProcessor.class, new IGestureEventListener() {
//			@Override
//			public boolean processGestureEvent(MTGestureEvent e) {
//				// Check if gestured ended on top of this
//				// Then check if we are dropping a suggestion on this
//				if (e.getId() == MTGestureEvent.GESTURE_ENDED) {
//					Vector3D location = ((DragEvent)e).getTo();
//					List<PickEntry> components = pick( location.x, location.y ).getPickList();
//					
//					for( PickEntry pe : components ) {
//                        if (pe.hitObj instanceof ButtonRemove)
//                        	destroy();
//                    }
//				}
//				return true;
//			}
//		});
		registerInputProcessor(new TapProcessor(pApplet));
		addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent) ge;
				
				if (te.getTapID() == TapEvent.TAPPED) {
					getParent().addChild(new InformationWidget(getpApplet(), getCenterPointGlobal().getX(), 
							getCenterPointGlobal().getY(), 400, 200, getBook()));
				}
				return true;
			}
		});
	}

	public GoogleBook getBook() {
		return this.book;
	}

	protected PApplet getpApplet() {
		return pApplet;
	}
}
