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
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;
import view.widgets.AbstractWindow;

public class KeywordWidget extends AbstractWindow {
	private ArrayList<String> keywords = new ArrayList<String>();
	private final MTList list;
	private final MTRoundRectangle cloud;
	private final PApplet pApplet;
	
	public KeywordWidget(PApplet pApplet, float x, float y, float w, float h) {
		super(pApplet, x, y, w, h, "Keywords");
		
		this.pApplet = pApplet;
		cloud = new MTRoundRectangle(pApplet, 0, 0, 0, w-15, h-40, 5, 5);
		this.addChild(cloud);
		cloud.setPositionRelativeToParent(new Vector3D(7.5f,32,0).addLocal(cloud.getCenterOfMass2DLocal()));
		cloud.setFillColor(new MTColor(0, 0, 0, 150));
		cloud.setNoStroke(true);
		cloud.removeAllGestureEventListeners();
		
		
		list = new MTList(pApplet, 0, 0, w-25, h-50);
		cloud.addChild(list);
		list.setPositionRelativeToParent(new Vector3D(5,5,0).addLocal(list.getCenterOfMass2DLocal()));
		list.setNoFill(true);
		list.setNoStroke(true);

		
	}
	
	public void addKeywords(ArrayList<String> keywords) {
		keywords.addAll(getKeywords());
		setKeywords(keywords);
	}
	
	public void setKeywords(ArrayList<String> keywords) {
		list.removeAllListElements();
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
				MTListCell cell = new MTListCell(pApplet, this.getWidthXYGlobal()-25, 30);
				MTTextArea text = new MTTextArea(pApplet, font);
				list.addChild(cell);
				cell.setNoStroke(true);
				cell.setFillColor(new MTColor(0,0,0,255));
				text.setText(keyword + " (" + count + ")");
				text.setNoStroke(true);
				text.setNoFill(true);
				cell.addChild(text);
				
				count = 1;
			}
			
			previous = keyword;
		}
	}
	
	public ArrayList<String> getKeywords() {
		return this.keywords;
	}
}
