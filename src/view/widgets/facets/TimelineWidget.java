package view.widgets.facets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTRectangle.PositionAnchor;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;
import view.elements.SuggestedElement;
import view.widgets.AbstractWindow;
import controllers.SuggestableScene;

public class TimelineWidget extends AbstractWindow implements IFacetWidget {
	private final ArrayList<Integer> values = new ArrayList<Integer>();
	private final HashMap<Integer,MTRoundRectangle> bars = new HashMap<Integer,MTRoundRectangle>();
	
	private int maxAmount = 0;
	private float stepBarHeight = 0;
	private float minBarHeight = 10f;
	private float maxBarHeight = 10f;
	private float margin = 5.0f;
	
	private final PApplet pApplet;
	private final SuggestableScene scene;
	private final MTRoundRectangle graph;
	private final MTTextArea warning;
	private final TimelineSlider sliderOne, sliderTwo;
	private final MTTextArea lowestValue, highestValue;
	
	public HashMap<Integer, MTRoundRectangle> getBars() {
		return bars;
	}

	private final MTColor inPeriod = new MTColor(255, 255, 255, 255);
	private final MTColor outPeriod = new MTColor(255, 255, 255, 50);
	
	public TimelineWidget(final SuggestableScene scene, float x, float y, float w, float h) {
		super(scene.getMTApplication(), x, y, w, h, "Publications / Year");
		this.pApplet = scene.getMTApplication();
		this.scene = scene;
		
		graph = new MTRoundRectangle(pApplet, 0, 0, 0, this.getWidthXYGlobal()-15, this.getHeightXYGlobal()-60, 5, 5);
		this.addChild(graph);
		graph.setPositionRelativeToParent(new Vector3D(7.5f,32,0).addLocal(graph.getCenterOfMass2DLocal()));
		graph.setFillColor(new MTColor(0, 0, 0, 150));
		graph.setNoStroke(true);
		graph.removeAllGestureEventListeners();
		
		IFont font = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255));	//Font color
		
		IFont legendFont = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
				14, 	//Font size
				new MTColor(0, 0, 0, 150)); // Font color
		
		warning = new MTTextArea(pApplet, font);
		this.addChild(warning);
		warning.setText("No information on publishing dates found");
		warning.setPositionRelativeToOther(graph, graph.getCenterPointLocal());
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
		lowestValue.setAnchor(PositionAnchor.LOWER_LEFT);
		
		highestValue = new MTTextArea(pApplet, legendFont);
		this.addChild(highestValue);
		highestValue.setNoStroke(true);
		highestValue.setNoFill(true);
		highestValue.setAnchor(PositionAnchor.LOWER_RIGHT);
		
	}
	
	public int getLowestValue() {
		return Collections.min(values);
	}
	
	public int getHighestValue() {
		return Collections.max(values);
	}
	
	public void addValue(int year) {
		if (year <= 0)
			return;
		
		values.add(year);

		int amount = Collections.frequency(values, year);
		if (bars.containsKey(year) && amount <= maxAmount) {
			int minValue = Collections.min(values);
			int maxValue = Collections.max(values);
			updateBar(year,minValue,maxValue);
		} else {
			maxAmount = (amount > maxAmount) ? amount : maxAmount;
			updateGraph();
		}
	}
	
	public void removeValue(int year) {
		int amount = Collections.frequency(values, year);
		values.remove(new Integer(year));
		
		int minValue, maxValue;
		
		if (values.size() > 0) {
			minValue = Collections.min(values);
			maxValue = Collections.max(values);
		} else {
			minValue = -1;
			maxValue = -2;
			warning.setVisible(true);
		}
		
		if (amount == maxAmount) {
			int previousIndex = -1;
			maxAmount = 0;
			Collections.sort(values);
			for (int i=minValue; i<=maxValue; i++) {
				System.out.println(i);
				int tmpIndex = values.lastIndexOf(i);
				System.out.println(tmpIndex);
				int tmpAmount = tmpIndex-previousIndex;
				if (tmpAmount > maxAmount)
					maxAmount = tmpAmount;
				if (tmpIndex > -1)
					previousIndex = tmpIndex;
			}
			
			updateGraph();
			System.out.println("Removed and updated graph:"+maxAmount);
		} else {
			updateBar(year,minValue,maxValue);
			System.out.println("Removed and updated bar:"+year);
		}
	}
	
	public void removeValues() {
		values.clear();
		bars.clear();
		graph.removeAllChildren();
		warning.setVisible(true);
		
		sliderOne.setVisible(false);
		sliderTwo.setVisible(false);
		lowestValue.setText("");
		highestValue.setText("");
	}
	
	private void updateGraph() {
		maxBarHeight = graph.getHeightXY(TransformSpace.LOCAL) - 2*margin;
		stepBarHeight = (maxBarHeight-minBarHeight) / maxAmount;
		
		int minValue = Collections.min(values);
		int maxValue = Collections.max(values);
		int oldBarCount = bars.size();
		
		if (!bars.isEmpty()) {
			sliderOne.setVisible(true);
			sliderTwo.setVisible(true);
			warning.setVisible(false);
		} else {
			sliderOne.setVisible(false);
			sliderTwo.setVisible(false);
			warning.setVisible(true);
			lowestValue.setText("");
			highestValue.setText("");
		}
		
		// Remove unwanted bars
		for (Integer value : bars.keySet())
			if (value < minValue || maxValue < value)
				graph.removeChild(bars.remove(value));
		
		// Update each bar
		for (int i=minValue; i<=maxValue; i++) {
			updateBar(i, minValue, maxValue);
		}
		
		lowestValue.setText(Integer.toString(minValue));
		lowestValue.setPositionRelativeToParent(new Vector3D(10,this.getHeightXY(TransformSpace.LOCAL),1));
		highestValue.setText(Integer.toString(maxValue));
		highestValue.setPositionRelativeToParent(new Vector3D(this.getWidthXY(TransformSpace.LOCAL)-10,this.getHeightXY(TransformSpace.LOCAL),1));
		
		if (oldBarCount <= 2 || bars.size() <= 2) {
			sliderOne.setValue(minValue);
			sliderTwo.setValue(maxValue);
		}
	}
	
	private void updateBar(int value, int minValue, int maxValue) {
		if (bars.containsKey(value)) {
			graph.removeChild(bars.remove(value));
		}
		
		int amount = Collections.frequency(values, value);
		
		float w = (graph.getWidthXY(TransformSpace.LOCAL) - margin)/(maxValue-minValue+1)-margin;
		float h = minBarHeight + amount * stepBarHeight;
		
		float x = graph.getCenterPointLocal().x - graph.getWidthXY(TransformSpace.LOCAL)/2 + margin + (value-minValue)*(w+margin);
		float y = graph.getCenterPointLocal().y + graph.getHeightXY(TransformSpace.LOCAL)/2 - margin - h;
		
		MTRoundRectangle bar = new MTRoundRectangle(getpApplet(), x,y,0f,w,h,5f,5f);
		bar.setPickable(false);
		bars.put(value, bar);
		graph.addChild(bar);
	}
	
	public synchronized void updateSelection() {
		int sliderOneValue = sliderOne.getValue();
		int sliderTwoValue = sliderTwo.getValue();
		int leftValue = sliderOneValue <= sliderTwoValue ? sliderOneValue : sliderTwoValue;
		int rightValue = sliderOneValue >= sliderTwoValue ? sliderOneValue : sliderTwoValue;
		
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
		int leftValue = sliderOneValue <= sliderTwoValue ? sliderOneValue : sliderTwoValue;
		int rightValue = sliderOneValue >= sliderTwoValue ? sliderOneValue : sliderTwoValue;
		
		if (leftValue > element.getBook().getPublishingYear())
			return false;
		
		if (rightValue < element.getBook().getPublishingYear())
			return false;
		
		return true;
	}
	
	public MTRoundRectangle getGraph() {
		return this.graph;
	}
}


