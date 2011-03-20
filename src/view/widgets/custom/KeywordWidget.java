package view.widgets.custom;

import java.util.ArrayList;
import java.util.HashMap;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTList;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import bookshelf.filters.KeywordFilter;

import view.widgets.AbstractWindow;
import controllers.SuggestableScene;

public class KeywordWidget extends AbstractWindow {
	private HashMap<String,KeywordCell> keywords = new HashMap<String,KeywordCell>();
	private final MTList list;
	private final MTRoundRectangle cloud;
	private final SuggestableScene scene;
	private final MTTextArea warning;
//	private final MTTextField selectAll;
	
	public KeywordWidget(SuggestableScene scene, float x, float y, float w, float h) {
		super(scene.getMTApplication(), x, y, w, h, "Keywords");
		this.scene = scene;
		
		IFont font = FontManager.getInstance().createFont(scene.getMTApplication(), "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255));	//Font color
		
		cloud = new MTRoundRectangle(scene.getMTApplication(), 0, 0, 0, w-15, h-40, 5, 5);
		this.addChild(cloud);
		cloud.setPositionRelativeToParent(new Vector3D(7.5f,32,0).addLocal(cloud.getCenterOfMass2DLocal()));
		cloud.setFillColor(new MTColor(0, 0, 0, 150));
		cloud.setNoStroke(true);
		cloud.removeAllGestureEventListeners();
		
		list = new MTList(scene.getMTApplication(), 0, 0, w-25, h-50);
		cloud.addChild(list);
		list.setPositionRelativeToParent(new Vector3D(5,5,0).addLocal(list.getCenterOfMass2DLocal()));
		list.setNoFill(true);
		list.setNoStroke(true);
		
		warning = new MTTextArea(scene.getMTApplication(), font);
		this.addChild(warning);
		warning.setText("No keywords found");
		warning.setPositionRelativeToOther(cloud, cloud.getCenterPointLocal());
		warning.setNoStroke(true);
		warning.setNoFill(true);
	}
	
	private void addKeyword(String keyword) {
		keyword = keyword.toLowerCase();
		
		if (!keywords.containsKey(keyword)) {
			final KeywordCell cell = new KeywordCell(scene, list.getWidthXY(TransformSpace.LOCAL), 30, keyword);
			cell.registerInputProcessor(new TapProcessor(scene.getMTApplication()));
			cell.addGestureListener(TapProcessor.class, new IGestureEventListener() {
				@Override
				public boolean processGestureEvent(MTGestureEvent ge) {
					TapEvent te = (TapEvent) ge;
					
					if (te.getTapID() == TapEvent.TAPPED)
						cell.inverseSelection();
					
					updateFilter();
					
					return true;
				}
			});
			keywords.put(keyword, cell);
			list.addChild(cell);
		} else
			keywords.get(keyword).raiseCount();
		
		if (!keywords.isEmpty())
			warning.setVisible(false);
	}
	
	public void addKeywords(ArrayList<String> keywords) {
		for (String keyword : keywords)
			addKeyword(keyword);
		
		updateFilter();
	}
	
	private void removeKeyword(String keyword) {
		keyword = keyword.toLowerCase();
		KeywordCell cell = keywords.get(keyword);
		
		if (cell != null && cell.getCount()>1)
			cell.lowerCount();
		else
			list.removeChild(keywords.remove(keyword));
		
		if (keywords.isEmpty())
			warning.setVisible(true);
	}
	
	public void removeKeywords(ArrayList<String> keywords) {
		for (String keyword : keywords)
			removeKeyword(keyword);
	}
	
	public void removeKeywords() {
		keywords.clear();
		list.removeAllListElements();
		warning.setVisible(true);
	}
	
	private void updateFilter() {
		ArrayList<String> filter = new ArrayList<String>();
		for (KeywordCell cell : keywords.values())
			if (cell.isSelected())
				filter.add(cell.getKeyword());
		
		scene.setKeywordFilter(new KeywordFilter(filter));
	}
}
