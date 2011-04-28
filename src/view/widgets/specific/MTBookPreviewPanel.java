package view.widgets.specific;

import java.io.IOException;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.widgets.MTImage;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;
import processing.core.PImage;
import view.widgets.MTPanel;
import view.widgets.MTPanelButton;
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
				
			if (leftPage != null)
				leftPage.destroy();
			if (rightPage != null)
				rightPage.destroy();
			
			String leftPageId = book.getPageIds().get(leftPageIndex);
			leftPage = new MTImage(pApplet, new PImage(book.getPage(leftPageId)));
			addChild(leftPage);
			leftPage.setHeightXYGlobal(getHeightXYGlobal());
			leftPage.setPositionRelativeToParent(getCenterPointLocal().addLocal(new Vector3D(-leftPage.getWidthXY(TransformSpace.RELATIVE_TO_PARENT)/2-5,0)));
			leftPage.removeAllGestureEventListeners();
			leftPage.registerInputProcessor(new TapProcessor(pApplet));
			leftPage.addGestureListener(TapProcessor.class, new IGestureEventListener() {
				@Override
				public boolean processGestureEvent(MTGestureEvent ge) {
					TapEvent te = (TapEvent) ge;
					if (te.getTapID() == TapEvent.TAPPED) {
						showPages(leftPageIndex-1, rightPageIndex-1);
					}
					return true;
				}
			});
			
			String rightPageId = book.getPageIds().get(rightPageIndex);
			rightPage = new MTImage(pApplet, new PImage(book.getPage(rightPageId)));
			addChild(rightPage);
			rightPage.setHeightXYGlobal(getHeightXYGlobal());
			rightPage.setPositionRelativeToParent(getCenterPointLocal().addLocal(new Vector3D(rightPage.getWidthXY(TransformSpace.RELATIVE_TO_PARENT)/2+5,0)));
			rightPage.removeAllGestureEventListeners();
			rightPage.registerInputProcessor(new TapProcessor(pApplet));
			rightPage.addGestureListener(TapProcessor.class, new IGestureEventListener() {
				@Override
				public boolean processGestureEvent(MTGestureEvent ge) {
					TapEvent te = (TapEvent) ge;
					if (te.getTapID() == TapEvent.TAPPED) {
						showPages(leftPageIndex+1, rightPageIndex+1);
					}
					return true;
				}
			});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
