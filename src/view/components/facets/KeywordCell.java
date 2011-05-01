package view.components.facets;

import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.widgets.MTListCell;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import bookshelf.Keyword;

import view.components.MTPercentageIndicator;
import controllers.SuggestableScene;

public class KeywordCell extends MTListCell {
	
	private final IFont font;
	private final String keyword;
	private int count = 0;
	private float totalImportance = 0;
	private final MTTextArea textKeyword;
	private final MTPercentageIndicator barImportance;
	private final MTSvgButton btnCheck;

	public KeywordCell(SuggestableScene scene, float width, float height, Keyword keyword) {
		super(scene.getMTApplication(), width, height);
		this.keyword = keyword.getValue();
		
		this.setNoStroke(true);
		
		font = FontManager.getInstance().createFont(scene.getMTApplication(), "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255));	//Font color
		
		btnCheck = new MTSvgButton(scene.getMTApplication(), "src/data/icons/check.svg");
		btnCheck.setSizeXYGlobal(18, 18);
		this.addChild(btnCheck);
		
		textKeyword = new MTTextArea(scene.getMTApplication(), font);
		addChild(textKeyword);
		textKeyword.setAnchor(PositionAnchor.UPPER_LEFT);
		textKeyword.setPositionRelativeToParent(new Vector3D(35, 0));
		textKeyword.setText(this.keyword);
		textKeyword.setNoStroke(true);
		textKeyword.setNoFill(true);
		
		barImportance = new MTPercentageIndicator(scene.getMTApplication(), 50, 15);
		addChild(barImportance);
		barImportance.setAnchor(PositionAnchor.LOWER_RIGHT);
		barImportance.setPositionRelativeToParent(new Vector3D(width-10, height-7.5f));
		
		this.setFillColor(new MTColor(255, 255, 255, 50));
		
		this.raiseCount();
		this.updateImportance(keyword.getImportance());
		this.setComposite(true);
	}

	public boolean isSelected() {
		return btnCheck.isVisible();
	}
	
	public void inverseSelection() {
		if (isSelected())
			deselect();
		else
			select();
	}
	
	public void select() {
		btnCheck.setVisible(true);
	}
	
	public void deselect() {
		btnCheck.setVisible(false);
	}
	
	public String getKeyword() {
		return this.keyword;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void raiseCount() {
		this.count++;
	}
	
	public void lowerCount() {
		this.count--;
	}

	public void updateImportance(double importance) {
		totalImportance += importance;
		float mixedImportance = totalImportance/count;
		
		barImportance.setValue(mixedImportance);
	}
}
