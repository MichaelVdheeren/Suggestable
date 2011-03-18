package view.widgets.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;
import view.widgets.AbstractWindow;

public class TimelineWidget extends AbstractWindow {
	private final ArrayList<Integer> values = new ArrayList<Integer>();
	private final HashMap<Integer,MTRoundRectangle> bars = new HashMap<Integer,MTRoundRectangle>();
	
	private int maxAmount = 0;
	private float stepBarHeight = 0;
	private float minBarHeight = 10f;
	private float maxBarHeight = 10f;
	private float margin = 5.0f;
	
	private final PApplet pApplet;
	private final MTRoundRectangle graph;
	private final MTTextArea warning;
	private final TimelineSlider leftSlider, rightSlider;
	private int leftValue, rightValue;
	
	private final MTColor inPeriod = new MTColor(255, 255, 255, 255);
	private final MTColor outPeriod = new MTColor(255, 255, 255, 50);
	
	private boolean changed;
	
	public TimelineWidget(PApplet pApplet, float x, float y, float w, float h) {
		super(pApplet, x, y, w, h, "Publications / Year");
		this.pApplet = pApplet;
		
		graph = new MTRoundRectangle(pApplet, 0, 0, 0, this.getWidthXYGlobal()-15, this.getHeightXYGlobal()-60, 5, 5);
		this.addChild(graph);
		graph.setPositionRelativeToParent(new Vector3D(7.5f,32,0).addLocal(graph.getCenterOfMass2DLocal()));
		graph.setFillColor(new MTColor(0, 0, 0, 150));
		graph.setNoStroke(true);
		graph.removeAllGestureEventListeners();
		
		IFont font = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255));	//Font color
		
		warning = new MTTextArea(pApplet, font);
		this.addChild(warning);
		warning.setText("No information on publishing dates found");
		warning.setPositionRelativeToOther(graph, graph.getCenterPointLocal());
		warning.setNoStroke(true);
		warning.setNoFill(true);
		
		leftSlider = new TimelineSlider(this);
		rightSlider = new TimelineSlider(this);
		this.addChild(leftSlider);
		this.addChild(rightSlider);
		
		leftSlider.removeAllGestureEventListeners();
		leftSlider.addGestureListener(DragProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				DragEvent de = (DragEvent) ge;
				float y = graph.getCenterPointGlobal().getY()+20;
				
				if (de.getId() == DragEvent.GESTURE_UPDATED) {
					float x = de.getTo().getX();
					
					float min = graph.getCenterPointGlobal().getX() - graph.getWidthXY(TransformSpace.GLOBAL)/2 + margin;
					float max = rightSlider.getCenterPointGlobal().getX()-bars.get(rightValue).getWidthXY(TransformSpace.GLOBAL);
					
					if (x < min)
						x = min;
					if (x > max)
						x = max;
					
					leftSlider.setPositionGlobal(new Vector3D(x,y));
				}
				
				if (de.getId() == DragEvent.GESTURE_ENDED) {
					int minValue = Collections.min(values);
					int maxValue = Collections.max(values);
					
					float x = de.getTo().getX();
					
					float min = graph.getCenterPointGlobal().getX() - graph.getWidthXY(TransformSpace.GLOBAL)/2 + margin;
					float max = rightSlider.getCenterPointGlobal().getX()-bars.get(rightValue).getWidthXY(TransformSpace.GLOBAL);
					
					if (x < min)
						x = min;
					if (x > max)
						x = max;
					
					float distance = graph.getWidthXY(TransformSpace.GLOBAL);
					
					for (int i=minValue; i<=maxValue; i++) {
						float barX = bars.get(i).getCenterPointGlobal().getX();
						float barD = Math.abs(x-barX);
						if (barD < distance) {
							distance = barD;
							leftValue = i;
						}
					}
					
					updateSliders();
				}
				
				return true;
			}
		});
		
		
		rightSlider.addGestureListener(DragProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				DragEvent de = (DragEvent) ge;
				float y = graph.getCenterPointGlobal().getY()+20;
				
				if (de.getId() == DragEvent.GESTURE_UPDATED) {
					float x = de.getTo().getX();
					
					float max = graph.getCenterPointGlobal().getX() + graph.getWidthXY(TransformSpace.GLOBAL)/2 - margin;
					float min = leftSlider.getCenterPointGlobal().getX()+bars.get(leftValue).getWidthXY(TransformSpace.GLOBAL);
					
					if (x < min)
						x = min;
					if (x > max)
						x = max;
					
					rightSlider.setPositionGlobal(new Vector3D(x,y));
				}
				
				if (de.getId() == DragEvent.GESTURE_ENDED) {
					int minValue = Collections.min(values);
					int maxValue = Collections.max(values);
					
					float x = de.getTo().getX();
					
					float max = graph.getCenterPointGlobal().getX() + graph.getWidthXY(TransformSpace.GLOBAL)/2 - margin;
					float min = leftSlider.getCenterPointGlobal().getX()+bars.get(leftValue).getWidthXY(TransformSpace.GLOBAL);
					
					if (x < min)
						x = min;
					if (x > max)
						x = max;
					
					float distance = graph.getWidthXY(TransformSpace.GLOBAL);
					
					for (int i=minValue; i<=maxValue; i++) {
						float barX = bars.get(i).getCenterPointGlobal().getX();
						float barD = Math.abs(x-barX);
						if (barD < distance) {
							distance = barD;
							rightValue = i;
						}
					}
					
					updateSliders();
				}
				
				return true;
			}
		});
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
		
		leftSlider.setVisible(false);
		rightSlider.setVisible(false);
	}
	
	private void updateGraph() {
		maxBarHeight = graph.getHeightXY(TransformSpace.LOCAL) - 2*margin;
		stepBarHeight = (maxBarHeight-minBarHeight) / maxAmount;
		
		int minValue = Collections.min(values);
		int maxValue = Collections.max(values);
		int oldBarCount = bars.size();
		
		
		if (!bars.isEmpty()) {
			leftSlider.setVisible(true);
			rightSlider.setVisible(true);
			warning.setVisible(false);
		} else {
			leftSlider.setVisible(false);
			rightSlider.setVisible(false);
		}
		
		if (oldBarCount <= 2 || bars.size() <= 2) {
			leftValue = minValue;
			rightValue = maxValue;
		}
		
		
		// Remove unwanted bars
		for (Integer value : bars.keySet())
			if (value < minValue || maxValue < value)
				graph.removeChild(bars.remove(value));
		
		// Update each bar
		for (int i=minValue; i<=maxValue; i++) {
			updateBar(i, minValue, maxValue);
		}
		
		updateSliders();
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
		
		updateColor(value);
	}
	
	private void updateSliders() {
		float leftX = bars.get(leftValue).getCenterPointGlobal().getX();
		float rightX = bars.get(rightValue).getCenterPointGlobal().getX();
		float y = graph.getCenterPointGlobal().getY()+20;
		
		leftSlider.setPositionGlobal(new Vector3D(leftX,y));
		rightSlider.setPositionGlobal(new Vector3D(rightX,y));
		
		int minValue = Collections.min(values);
		int maxValue = Collections.max(values);
		
		for (int i=minValue; i<=maxValue; i++)
			updateColor(i);
	}
	
	private void updateColor(int value) {
		MTRoundRectangle bar = bars.get(value);
		
		if (leftValue <= value && value <= rightValue)
			bar.setFillColor(inPeriod);
		else
			bar.setFillColor(outPeriod);
	}

	PApplet getpApplet() {
		return pApplet;
	}
	
	public boolean isChanged() {
		return this.changed;
	}
	
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	public MTRoundRectangle getGraph() {
		return this.graph;
	}
}


