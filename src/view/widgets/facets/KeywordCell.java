package view.widgets.facets;

import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.widgets.MTListCell;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import view.widgets.Progressbar;

import controllers.SuggestableScene;

public class KeywordCell extends MTListCell {
	
	private final IFont font;
	private final String keyword;
	private int count = 0;
	private boolean selected = true;
	private final MTTextArea textKeyword;
	private final Progressbar barImportance;

	public KeywordCell(SuggestableScene scene, float width, float height, String keyword) {
		super(scene.getMTApplication(), width, height);
		this.keyword = keyword;
		
		this.setNoStroke(true);
		
		font = FontManager.getInstance().createFont(scene.getMTApplication(), "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255));	//Font color
		
		textKeyword = new MTTextArea(scene.getMTApplication(), font);
		addChild(textKeyword);
		textKeyword.setText(keyword);
		textKeyword.setNoStroke(true);
		textKeyword.setNoFill(true);
		
		barImportance = new Progressbar(scene.getMTApplication(), 50, 15);
		addChild(barImportance);
		barImportance.setAnchor(PositionAnchor.LOWER_RIGHT);
		barImportance.setPositionRelativeToParent(new Vector3D(width-10, height-7.5f));
		
		this.setFillColor(new MTColor(255, 255, 255, 50));
		
		this.raiseCount();
	}

	public boolean isSelected() {
		return this.selected;
	}
	
	public void inverseSelection() {
		if (isSelected())
			deselect();
		else
			select();
	}
	
	public void select() {
		this.selected = true;
	}
	
	public void deselect() {
		this.selected = false;
	}
	
	public String getKeyword() {
		return this.keyword;
	}
	
	public int getCount() {
		return this.count;
	}
	
	private void updateCount() {
	}
	
	public void raiseCount() {
		this.count++;
		updateCount();
	}
	
	public void lowerCount() {
		this.count--;
		updateCount();
	}
}
