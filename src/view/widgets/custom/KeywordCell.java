package view.widgets.custom;

import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.widgets.MTListCell;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import controllers.SuggestableScene;

public class KeywordCell extends MTListCell {
	
	private final IFont font;
	private final String keyword;
	private int count = 0;
	private final SuggestableScene scene;
	private boolean selected = true;
	private final MTTextArea textKeyword;
	private final MTTextArea textCounter;

	public KeywordCell(SuggestableScene scene, float width, float height, String keyword) {
		super(scene.getMTApplication(), width, height);
		this.scene = scene;
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
		
		textCounter = new MTTextArea(scene.getMTApplication(), font);
		addChild(textCounter);
		textCounter.setAnchor(PositionAnchor.LOWER_RIGHT);
		textCounter.setPositionRelativeToParent(new Vector3D(width-10, height));
		textCounter.setNoStroke(true);
		textCounter.setNoFill(true);
		
		registerInputProcessor(new TapProcessor(scene.getMTApplication()));
		addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent) ge;
				
				if (te.getTapID() == TapEvent.TAPPED)
					inverseSelection();
					
				return true;
			}
		});
		
		this.select();
		this.raiseCount();
	}

	public boolean isSelected() {
		return this.selected;
	}
	
	private void inverseSelection() {
		if (isSelected())
			deselect();
		else
			select();
	}
	
	public void select() {
		this.selected = true;

		this.setFillColor(new MTColor(255, 255, 255, 255));
		font.setFillColor(new MTColor(0, 0, 0));
	}
	
	public void deselect() {
		this.selected = false;

		this.setFillColor(new MTColor(255, 255, 255, 50));
		font.setFillColor(new MTColor(255, 255, 255));
	}
	
	public String getKeyword() {
		return this.keyword;
	}
	
	public int getCount() {
		return this.count;
	}
	
	private void updateCount() {
		textCounter.setText(Integer.toString(getCount()));
	}
	
	public void raiseCount() {
		this.count++;
		updateCount();
	}
	
	public void lowerCount() {
		this.count--;
		updateCount();
	}

	private SuggestableScene getScene() {
		return scene;
	}
}
