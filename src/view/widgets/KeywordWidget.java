package view.widgets;

import java.util.ArrayList;
import java.util.Collections;

import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTList;
import org.mt4j.components.visibleComponents.widgets.MTListCell;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;

import processing.core.PApplet;

public class KeywordWidget extends WindowWidget {
	private ArrayList<String> keywords;
	private final MTList list;
	private final MTRoundRectangle cloud;
	private final PApplet applet;
	
	public KeywordWidget(float x, float y, float w, float h, PApplet applet) {
		super(x, y, w, h, applet);
		
		this.applet = applet;
		cloud = new MTRoundRectangle(x+7.5f, y+30, 0, w-15, h-40, 5, 5, applet);
		cloud.setFillColor(new MTColor(0, 0, 0, 150));
		cloud.setNoStroke(true);
		cloud.removeAllGestureEventListeners();
		this.addChild(cloud);
		
		list = new MTList(x+12.5f, y+35, w-25, h-50, applet);
		list.setFillColor(new MTColor(0,0,0,0));
		list.setStrokeColor(new MTColor(0,0,0,0));

		cloud.addChild(list);
	}
	
	public void setKeywords(ArrayList<String> keywords) {

		this.keywords = keywords;
		
		Collections.sort(keywords);
		String previous = null;
		int count = 1;
		
		for (String keyword : keywords) {
			if (keyword.equals(previous))
				count++;
			else {
				MTListCell cell;
				MTTextArea text;
				cell = new MTListCell(this.getWidthXYGlobal()-25, 30, applet);
				cell.setStrokeColor(new MTColor(0,0,0,0));
				cell.setFillColor(new MTColor(0,0,0,0));
				text = new MTTextArea(applet);
				text.setText(keyword + " (" + count + ")");
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
