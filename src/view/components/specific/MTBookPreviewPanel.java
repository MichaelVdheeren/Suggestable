package view.components.specific;

import java.io.IOException;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.widgets.MTImage;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.math.Matrix;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;
import processing.core.PImage;
import view.components.MTPanel;
import view.components.MTPanelButton;
import bookshelf.apis.google.GoogleBook;

public class MTBookPreviewPanel extends MTPanel {
	private MTImage leftPage, rightPage;
	private final GoogleBook book;
	private PApplet pApplet;
	
	public MTBookPreviewPanel(PApplet pApplet, float width, float height, GoogleBook book) {
		super(pApplet, width, height, new MTPanelButton(pApplet, "data/icons/circle-eye.svg"));
		
		this.pApplet = pApplet;
		this.book = book;
		
		showPages(0,1);
	}
	
	private void showPages(final int leftPageIndex, final int rightPageIndex) {
		try {
			if (leftPageIndex<0 || rightPageIndex >= book.getPageIds().size())
				return;
			
			if (leftPage != null)
				leftPage.destroy();
			if (rightPage != null)
				rightPage.destroy();
			
			Thread leftPageLoader = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						String leftPageId = book.getPageIds().get(leftPageIndex);
						leftPage = new MTImage(pApplet, new PImage(book.getPage(leftPageId)));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					addChild(leftPage);
					leftPage.setHeightXYGlobal(getHeightXYGlobal());
					leftPage.setPositionRelativeToParent(getCenterPointLocal().addLocal(new Vector3D(-leftPage.getWidthXY(TransformSpace.RELATIVE_TO_PARENT)/2-5,0)));
					leftPage.removeAllGestureEventListeners();
					leftPage.addGestureListener(DragProcessor.class, new IGestureEventListener() {
						Vector3D from;
						
						@Override
						public boolean processGestureEvent(MTGestureEvent ge) {
							DragEvent de = (DragEvent) ge;
							
							if (de.getId() == DragEvent.GESTURE_STARTED) {
								from = de.getFrom();
							} else if (de.getId() == DragEvent.GESTURE_ENDED) {
								Vector3D translation = de.getTo().getSubtracted(from);
								if (translation.getX() > 0)
									showPages(leftPageIndex-2, rightPageIndex-2);
							}
							
							return true;
						}
					});
				}
			});
			
			Thread rightPageLoader = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						String rightPageId = book.getPageIds().get(rightPageIndex);
						rightPage = new MTImage(pApplet, new PImage(book.getPage(rightPageId)));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					addChild(rightPage);
					rightPage.setHeightXYGlobal(getHeightXYGlobal());
					rightPage.setPositionRelativeToParent(getCenterPointLocal().addLocal(new Vector3D(rightPage.getWidthXY(TransformSpace.RELATIVE_TO_PARENT)/2+5,0)));
					rightPage.removeAllGestureEventListeners();
					rightPage.addGestureListener(DragProcessor.class, new IGestureEventListener() {
						Vector3D from;
						
						@Override
						public boolean processGestureEvent(MTGestureEvent ge) {
							DragEvent de = (DragEvent) ge;
							
							if (de.getId() == DragEvent.GESTURE_STARTED) {
								from = de.getFrom();
							} else if (de.getId() == DragEvent.GESTURE_ENDED) {
								Vector3D translation = de.getTo().getSubtracted(from);
								if (translation.getX() < 0)
									showPages(leftPageIndex+2, rightPageIndex+2);
							}
							
							return true;
						}
					});
				}
			});
			
			leftPageLoader.start();
			rightPageLoader.start();
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
