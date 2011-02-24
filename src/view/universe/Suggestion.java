package view.universe;

import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.util.MTColor;

import processing.core.PApplet;
import bookshelf.AbstractBook;

public class Suggestion extends MTRoundRectangle {
	private final AbstractBook book;
	
	public Suggestion(PApplet pApplet, float x, float y, float s, AbstractBook book) {
		super(pApplet, x, y, 0, s, s, 5, 5);

		this.book = book;
		
		this.setComposite(true);
		this.setFillColor(new MTColor(0, 0, 0, 200));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		
		IFont font = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255));	//Font color
		
		MTTextArea text = new MTTextArea(pApplet, this.getCenterPointGlobal().getX(), this.getCenterPointGlobal().getY(), s, s, font);
		text.setNoStroke(true);
		text.setNoFill(true);
		text.setText(getBook().getTitle());
		text.setPositionGlobal(this.getCenterPointGlobal());
		this.addChild(text);
		
		
		
		
		registerInputProcessor(new DragProcessor(pApplet));
		addGestureListener(DragProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent e) {
				// Check if gestured ended on top of this
				// Then check if we are dropping a suggestion on this
				if (e.getId() == MTGestureEvent.GESTURE_ENDED) {
					if (!e.hasTarget())
						return false;
					
					System.out.println("dragged");
				}
				return true;
			}
		});
	}

	public AbstractBook getBook() {
		return this.book;
	}
}
