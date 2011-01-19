package view.universe;

import model.books.Book;

import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;

import processing.core.PApplet;

public class Suggestion extends MTRoundRectangle {
	private final Book book;
	
	public Suggestion(PApplet pApplet, float x, float y, float s, Book book) {
		super(x, y, 0, s, s, 5, 5, pApplet);

		this.book = book;
		
		this.setComposite(true);
		this.setFillColor(new MTColor(0, 0, 0, 200));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		
		IFont font = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255),  //Font fill color
				new MTColor(255,255,255,180));	//Font outline color
		
		MTTextArea text = new MTTextArea(this.getCenterPointGlobal().getX(), this.getCenterPointGlobal().getY(), s, s, font, pApplet);
		text.setNoStroke(true);
		text.setNoFill(true);
		text.setText(getBook().getTitle());
		text.setPositionGlobal(this.getCenterPointGlobal());
		this.addChild(text);
	}

	public Book getBook() {
		return this.book;
	}
}
