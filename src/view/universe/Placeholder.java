package view.universe;

import model.books.Book;

import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public class Placeholder extends MTEllipse {
	private final Book book;
	private float radius;
	private float gforce = 1;
	
	public Placeholder(PApplet pApplet, Vector3D c, float r, Book book) {
		super(pApplet, c, r, r);
		this.book = book;
		
		this.setComposite(true);
		this.setFillColor(new MTColor(0, 0, 0, 200));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		
		IFont font = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255),  //Font fill color
				new MTColor(255,255,255,180));	//Font outline color
		
		MTTextArea text = new MTTextArea(pApplet, font);
		text.setNoStroke(true);
		text.setNoFill(true);
		text.setText(getBook().getTitle());
		text.setPositionGlobal(c);
		this.addChild(text);
	}
	
	public Book getBook() {
		return this.book;
	}
}