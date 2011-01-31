package view.widgets;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;

import processing.core.PApplet;

import com.google.books.unofficial.api.Book;

public class InformationWidget extends WindowWidget {

	MTRoundRectangle container;
	
	public InformationWidget(float x, float y, float w, float h, Book book, PApplet pApplet) {
		super(x, y, w, h, pApplet);
		
		container = new MTRoundRectangle(x+7.5f, y+30, 0, this.getWidthXYGlobal()-15, this.getHeightXYGlobal()-40, 5, 5, pApplet);
		container.setFillColor(new MTColor(0, 0, 0, 150));
		container.setNoStroke(true);
		container.removeAllGestureEventListeners();
		this.addChild(container);
		
		float tx = container.getCenterPointGlobal().x-container.getWidthXY(TransformSpace.GLOBAL)/2;
		float ty = container.getCenterPointGlobal().y-container.getHeightXY(TransformSpace.GLOBAL)/2;
		
		MTTextArea text = new MTTextArea(tx, ty, container.getWidthXY(TransformSpace.GLOBAL), 20, pApplet);
		text.setText(book.getTitles().get(0));
		container.addChild(text);
	}
}
