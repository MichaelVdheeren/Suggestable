package view.widgets;

import java.util.ArrayList;

import org.mt4j.components.MTComponent;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;

import processing.core.PApplet;

public class TimelineWidget extends WindowWidget {
	private ArrayList<Float> values = new ArrayList<Float>();
	
	private final PApplet pApplet;
	private final MTRoundRectangle graph;
	
	private final MTColor white = new MTColor(255,255,255);
	private final MTColor blue = new MTColor(1,151,253);
	
	public TimelineWidget(float x, float y, float w, float h, PApplet pApplet) {
		super(x, y, w, h, pApplet);
		this.pApplet = pApplet;
		
		graph = new MTRoundRectangle(x+7.5f, y+30, 0, this.getWidthXYGlobal()-15, this.getHeightXYGlobal()-60, 5, 5, pApplet);
		graph.setFillColor(new MTColor(0, 0, 0, 150));
		graph.setNoStroke(true);
		graph.removeAllGestureEventListeners();
		this.addChild(graph);
		
		ArrayList<Float> values = new ArrayList<Float>();
		values.add(100f);
		values.add(50f);
		values.add(30f);
		values.add(20f);
		values.add(60f);
		values.add(100f);
		values.add(50f);
		values.add(30f);
		values.add(20f);
		values.add(60f);
		values.add(100f);
		values.add(50f);
		values.add(30f);
		values.add(20f);
		values.add(60f);
		values.add(30f);
		values.add(20f);
		values.add(60f);
		values.add(100f);
		values.add(50f);
		
		this.setValues(values);
	}

	public ArrayList<Float> getValues() {
		return values;
	}

	public void setValues(ArrayList<Float> values) {
		this.values = values;
		
		// Clear
		graph.removeAllChildren();
		
		float a = values.size();
		float s = 5.0f;
		float mH = graph.getHeightXY(TransformSpace.GLOBAL) - 2*s;
		float w = (graph.getWidthXY(TransformSpace.GLOBAL) - s)/a-s;
		float x = graph.getCenterPointGlobal().x - graph.getWidthXY(TransformSpace.GLOBAL)/2 + s;
		float y = graph.getCenterPointGlobal().y + graph.getHeightXY(TransformSpace.GLOBAL)/2 - s;
		
		for (int i=0; i<this.getValues().size(); i++) {
			float h = this.getValues().get(i)/100*mH;
			final MTRoundRectangle bar = new MTRoundRectangle(x+i*(w+s), y-h, 0, w, h, 5, 5, this.getpApplet());
			bar.setFillColor(white);
			bar.removeAllGestureEventListeners();
			bar.registerInputProcessor(new TapProcessor(getpApplet()));
			bar.addGestureListener(TapProcessor.class, new IGestureEventListener() {
				@Override
				public boolean processGestureEvent(MTGestureEvent e) {
					if (e.getId() == MTGestureEvent.GESTURE_ENDED)
						if (bar.getFillColor() == white)
							bar.setFillColor(blue);
						else
							bar.setFillColor(white);
					
					return true;
				}
			});
			graph.addChild(bar);
		}
	}

	private PApplet getpApplet() {
		return pApplet;
	}

	public ArrayList<Integer> getSelectedIndices() {
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		for (int i=0; i < graph.getChildCount(); i++) {
			MTComponent c = graph.getChildByIndex(i);
			if ((c instanceof MTRoundRectangle) && ((MTRoundRectangle) c).getFillColor() == blue)
				result.add(i);
		}
		
		return result;
	}
}
