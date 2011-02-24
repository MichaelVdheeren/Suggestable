package view.widgets.custom;

import java.util.ArrayList;
import java.util.Collections;

import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTList;
import org.mt4j.components.visibleComponents.widgets.MTListCell;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;

import processing.core.PApplet;
import view.widgets.AbstractWindow;

public class KeywordWidget extends AbstractWindow {
	private ArrayList<String> keywords;
	private final MTList list;
	private final MTRoundRectangle cloud;
	private final PApplet pApplet;
	
	public KeywordWidget(PApplet pApplet, float x, float y, float w, float h) {
		super(pApplet, x, y, w, h, "Keywords");
		
		this.pApplet = pApplet;
		cloud = new MTRoundRectangle(pApplet, x+7.5f, y+30, 0, w-15, h-40, 5, 5);
		cloud.setFillColor(new MTColor(0, 0, 0, 150));
		cloud.setNoStroke(true);
		cloud.removeAllGestureEventListeners();
		this.addChild(cloud);
		
		list = new MTList(pApplet, x+12.5f, y+35, w-25, h-50);
		list.setNoFill(true);
		list.setNoStroke(true);

		cloud.addChild(list);
	}
	
	public void setKeywords(ArrayList<String> keywords) {
		// TODO: remove when bug with MT4j is fixed
		if (getKeywords() != null)
			list.removeAllChildren();
			
		this.keywords = keywords;
		
		Collections.sort(keywords);
		String previous = null;
		int count = 1;
		
		IFont font = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255));	//Font color
		
		for (String keyword : keywords) {
			if (keyword.equals(previous))
				count++;
			else {
				MTListCell cell;
				MTTextArea text;
				cell = new MTListCell(pApplet, this.getWidthXYGlobal()-25, 30);
				cell.setNoStroke(true);
				cell.setFillColor(new MTColor(0,0,0,255));
				text = new MTTextArea(pApplet);
				text.setText(keyword + " (" + count + ")");
				text.setFont(font);
				text.setNoStroke(true);
				text.setNoFill(true);
				cell.addChild(text);
				list.addChild(cell);
				count = 1;
			}
			
			previous = keyword;
		}
	}
	
	public ArrayList<String> getKeywords() {
		return this.keywords;
	}
}
