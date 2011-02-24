package view.widgets.custom;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;

import processing.core.PApplet;
import view.widgets.AbstractWindow;
import bookshelf.AbstractBook;

public class InformationWidget extends AbstractWindow {

	MTRoundRectangle container;
	
	public InformationWidget(PApplet pApplet, float x, float y, float w, float h, AbstractBook book) {
		super(pApplet, x, y, w, h, "Information");
		
		container = new MTRoundRectangle(pApplet, x+7.5f, y+30, 0, this.getWidthXYGlobal()-15, this.getHeightXYGlobal()-40, 5, 5);
		container.setFillColor(new MTColor(0, 0, 0, 150));
		container.setNoStroke(true);
		container.removeAllGestureEventListeners();
		this.addChild(container);
		
		float tx = container.getCenterPointGlobal().x-container.getWidthXY(TransformSpace.GLOBAL)/2;
		float ty = container.getCenterPointGlobal().y-container.getHeightXY(TransformSpace.GLOBAL)/2;
		
		MTTextArea text = new MTTextArea(pApplet, tx, ty, container.getWidthXY(TransformSpace.GLOBAL), 20);
		text.setText(book.getTitle());
		container.addChild(text);
	}
}
