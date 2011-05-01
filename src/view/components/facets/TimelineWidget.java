package view.components.facets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTLine;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;
import view.components.MTAbstractWindow;
import view.elements.SuggestedElement;
import controllers.SuggestableScene;

public class TimelineWidget extends MTAbstractWindow implements IFacetWidget {
	private final ArrayList<Integer> values = new ArrayList<Integer>();
	private final HashMap<Integer,MTRoundRectangle> bars = new HashMap<Integer,MTRoundRectangle>();
	private final HashMap<Integer,MTLine> lines = new HashMap<Integer,MTLine>();
	
	private int maxAmount = 0;
	private float stepBarHeight = 0;
	private float minBarHeight = 10f;
	private float maxBarHeight = 10f;
	private float margin = 5.0f;
	
	private final PApplet pApplet;
	private final SuggestableScene scene;
	private final MTTextArea warning;
	private final TimelineSlider sliderOne, sliderTwo;
	private final MTTextArea lowestValue, highestValue;
	private final MTLine scaleLine;
	
	public HashMap<Integer, MTRoundRectangle> getBars() {
		return bars;
	}

	private final MTColor inPeriod = new MTColor(255, 255, 255, 255);
	private final MTColor outPeriod = new MTColor(255, 255, 255, 50);
	
	public TimelineWidget(final SuggestableScene scene, float x, float y, float w, float h) {
		super(scene.getMTApplication(), x, y, w, h, "Publications / Year");
		this.pApplet = scene.getMTApplication();
		this.scene = scene;
		
		IFont font = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255));	//Font color
		
		IFont legendFont = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
				14, 	//Font size
				new MTColor(255,255,255)); // Font color
		
		warning = new MTTextArea(pApplet, font);
		this.addChild(warning);
		warning.setText("No information on publishing dates found");
		warning.setPositionRelativeToOther(getContainer(), getContainer().getCenterPointLocal());
		warning.setNoStroke(true);
		warning.setNoFill(true);
		
		sliderOne = new TimelineSlider(this);
		sliderTwo = new TimelineSlider(this);
		this.addChild(sliderOne);
		this.addChild(sliderTwo);
		
		lowestValue = new MTTextArea(pApplet, legendFont);
		this.addChild(lowestValue);
		lowestValue.setNoStroke(true);
		lowestValue.setNoFill(true);
		
		highestValue = new MTTextArea(pApplet, legendFont);
		this.addChild(highestValue);
		highestValue.setNoStroke(true);
		highestValue.setNoFill(true);
		
		scaleLine = new MTLine(pApplet, 0, 0, getContainer().getWidthXY(TransformSpace.GLOBAL)-10, 0);
		this.addChild(scaleLine);
		scaleLine.setVisible(false);
		scaleLine.setStrokeWeight(3);
		scaleLine.setPositionRelativeToOther(getContainer(),new Vector3D(getContainer().getWidthXY(TransformSpace.GLOBAL)/2, getContainer().getHeightXY(TransformSpace.GLOBAL)));
	}
	
	public int getLowestValue() {
		return this.values.isEmpty() ? 0 : Collections.min(this.values);
	}
	
	public int getHighestValue() {
		return this.values.isEmpty() ? 0 : Collections.max(this.values);
	}
	
	public void addValue(int year) {
		if (values.isEmpty()) {
			sliderOne.setVisible(true);
			sliderTwo.setVisible(true);
			scaleLine.setVisible(true);
			warning.setVisible(false);
		}
		
		values.add(year);
		int amount = Collections.frequency(values, year);
		
		if (bars.containsKey(year) && amount <= maxAmount) {
			updateBar(year,getLowestValue(),getHighestValue());
		} else {
			maxAmount = Math.max(amount,maxAmount);
			updateGraph();
		}
	}
	
	public void removeValue(int year) {
		values.remove(new Integer(year));
		int amount = Collections.frequency(values, year);
		
		if (values.isEmpty()) {
			removeValues();
			return;
		}
		
		if ((amount==0) || (getHighestFrequence() < maxAmount)) {
			updateGraph();
		} else {
			updateBar(year,getLowestValue(),getHighestValue());
		}
		
		maxAmount = getHighestFrequence();
	}

	private int getHighestFrequence() {
		int minValue = getLowestValue();
		int maxValue = getHighestValue();
		int previousIndex = -1;
		int highestFreq = 0;
		Collections.sort(values);
		
		for (int i=minValue; i<=maxValue; i++) {
			int tmpIndex = values.lastIndexOf(i);
			int tmpFreq = tmpIndex-previousIndex;
			if (tmpFreq > highestFreq)
				highestFreq = tmpFreq;
			if (tmpIndex > -1)
				previousIndex = tmpIndex;
		}
		
		return maxAmount;
	}
	
	public void removeValues() {
		values.clear();
		bars.clear();
		lines.clear();
		getContainer().removeAllChildren();
		
		warning.setVisible(true);
		sliderOne.setVisible(false);
		sliderTwo.setVisible(false);
		scaleLine.setVisible(false);
		lowestValue.setText("");
		highestValue.setText("");
		maxAmount = 0;
	}
	
	private synchronized void updateGraph() {
		maxBarHeight = getContainer().getHeightXY(TransformSpace.LOCAL) - 2*margin;
		stepBarHeight = (maxBarHeight-minBarHeight) / maxAmount;
		
		int minValue = getLowestValue();
		int maxValue = getHighestValue();
		int oldBarCount = bars.size();
		
		// Remove unwanted bars
		int oldMinValue = bars.isEmpty() ? 0 : Collections.min(bars.keySet());
		int oldMaxValue = bars.isEmpty() ? -1 : Collections.max(bars.keySet());
		
		for (int i=oldMinValue; i<=oldMaxValue; i++)
			if (i < minValue || maxValue < i)
				bars.remove(i).destroy();
		
		// Update each bar
		for (int i=minValue; i<=maxValue; i++) {
			updateBar(i, minValue, maxValue);
		}
		
		lowestValue.setText(Integer.toString(minValue));
		lowestValue.setPositionRelativeToOther(getContainer(), new Vector3D(lines.get(minValue).getCenterPointLocal().x,
				getContainer().getHeightXY(TransformSpace.LOCAL)+lowestValue.getHeightXY(TransformSpace.LOCAL)/2));
		highestValue.setText(Integer.toString(maxValue));
		highestValue.setPositionRelativeToOther(getContainer(), new Vector3D(lines.get(maxValue).getCenterPointLocal().x,
				getContainer().getHeightXY(TransformSpace.LOCAL)+lowestValue.getHeightXY(TransformSpace.LOCAL)/2));
		
		if (oldBarCount <= 2 || bars.size() <= 2) {
			sliderOne.setValue(minValue);
			sliderTwo.setValue(maxValue);
		} else {
			int currMinValue = Math.max(Math.min(sliderOne.getValue(), sliderTwo.getValue()),minValue);
			int currMaxValue = Math.min(Math.max(sliderOne.getValue(), sliderTwo.getValue()),maxValue);

			sliderOne.setValue(currMinValue);
			sliderTwo.setValue(currMaxValue);
		}
	}
	
	private synchronized void updateBar(int value, int minValue, int maxValue) {
		MTColor color = outPeriod;
		
		if (bars.containsKey(value)) {
			color = bars.get(value).getFillColor();
			bars.remove(value).destroy();
			lines.remove(value).destroy();
		}
		
		int amount = Collections.frequency(values, value);
		
		float w = (getContainer().getWidthXY(TransformSpace.LOCAL) - margin)/(maxValue-minValue+1)-margin;
		float h = minBarHeight + amount * stepBarHeight;
		
		float x = getContainer().getCenterPointLocal().x - getContainer().getWidthXY(TransformSpace.LOCAL)/2 + margin + (value-minValue)*(w+margin);
		float y = getContainer().getCenterPointLocal().y + getContainer().getHeightXY(TransformSpace.LOCAL)/2 - margin - h;
		
		MTRoundRectangle bar = new MTRoundRectangle(getpApplet(), x,y,0f,w,h,5f,5f);
		MTLine line = new MTLine(getpApplet(), 
					x+w/2, getContainer().getHeightXY(TransformSpace.LOCAL)-3, 
					x+w/2, getContainer().getHeightXY(TransformSpace.LOCAL)+4);
		line.setStrokeWeight(3);
		bar.setPickable(false);
		bar.setFillColor(color);
		bars.put(value, bar);
		lines.put(value, line);
		getContainer().addChild(bar);
		getContainer().addChild(line);
	}
	
	public synchronized void updateSelection() {
		int sliderOneValue = sliderOne.getValue();
		int sliderTwoValue = sliderTwo.getValue();
		int leftValue = Math.min(sliderOneValue, sliderTwoValue);
		int rightValue = Math.max(sliderOneValue, sliderTwoValue);
		
		for (int i=getLowestValue(); i<= getHighestValue(); i++)
			if (leftValue <= i && i <= rightValue)
				bars.get(i).setFillColor(inPeriod);
			else
				bars.get(i).setFillColor(outPeriod);
		
		scene.updateElements();
	}

	PApplet getpApplet() {
		return pApplet;
	}

	@Override
	public boolean withinSelection(SuggestedElement element) {
		int sliderOneValue = sliderOne.getValue();
		int sliderTwoValue = sliderTwo.getValue();
		int leftValue = Math.min(sliderOneValue, sliderTwoValue);
		int rightValue = Math.max(sliderOneValue, sliderTwoValue);
		
		if (leftValue > element.getBook().getPublishingYear())
			return false;
		
		if (rightValue < element.getBook().getPublishingYear())
			return false;
		
		return true;
	}
	
	public MTRoundRectangle getGraph() {
		return this.getContainer();
	}
}


