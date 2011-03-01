package view.widgets.custom;

import java.awt.Image;
import java.io.IOException;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTImage;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;
import processing.core.PImage;
import view.widgets.AbstractWindow;
import bookshelf.apis.google.GoogleBook;

public class InformationWidget extends AbstractWindow {
	MTRoundRectangle container;
	private final GoogleBook book;
	private final PApplet pApplet;
	
	public InformationWidget(PApplet pApplet, float x, float y, float w, float h, GoogleBook book) {
		super(pApplet, x, y, w, h, "Information");
		this.pApplet = pApplet;
		this.book = book;
		
		container = new MTRoundRectangle(pApplet, 0, 0, 0, this.getWidthXYGlobal()-15, this.getHeightXYGlobal()-40, 5, 5);
		container.setPositionRelativeToParent(new Vector3D(7.5f,32,0).addLocal(container.getCenterOfMass2DLocal()));
		container.setFillColor(new MTColor(0, 0, 0, 150));
		container.setNoStroke(true);
		container.removeAllGestureEventListeners();
		this.addChild(container);
		
		try {
			Image image = getBook().getCover();
			MTImage cover = new MTImage(getpApplet(), new PImage(image));
			container.addChild(cover);
			float s = 100/cover.getHeightXY(TransformSpace.GLOBAL);
			cover.scaleGlobal(s, s, 1, cover.getCenterPointGlobal());
			cover.setPositionRelativeToParent(new Vector3D(7.5f,7.5f,0).addLocal(
					new Vector3D(cover.getWidthXY(TransformSpace.GLOBAL)/2, cover.getHeightXY(TransformSpace.GLOBAL)/2)
				));
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private PApplet getpApplet() {
		return this.pApplet;
	}
	
	private GoogleBook getBook() {
		return this.book;
	}
}
