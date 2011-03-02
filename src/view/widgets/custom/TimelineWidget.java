package view.widgets.custom;

import java.util.ArrayList;

import org.mt4j.components.MTComponent;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;
import view.widgets.AbstractWindow;

public class TimelineWidget extends AbstractWindow {
	private ArrayList<Float> values = new ArrayList<Float>();
	
	private final PApplet pApplet;
	private final MTRoundRectangle graph;
	
	private final MTColor white = new MTColor(255,255,255);
	private final MTColor blue = new MTColor(1,151,253);
	
	public TimelineWidget(PApplet pApplet, float x, float y, float w, float h) {
		super(pApplet, x, y, w, h, "Timeline");
		this.pApplet = pApplet;
		
		graph = new MTRoundRectangle(pApplet, 0, 0, 0, this.getWidthXYGlobal()-15, this.getHeightXYGlobal()-60, 5, 5);
		this.addChild(graph);
		graph.setPositionRelativeToParent(new Vector3D(7.5f,32,0).addLocal(graph.getCenterOfMass2DLocal()));
		graph.setFillColor(new MTColor(0, 0, 0, 150));
		graph.setNoStroke(true);
		graph.removeAllGestureEventListeners();
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
		float mH = graph.getHeightXY(TransformSpace.LOCAL) - 2*s;
		float w = (graph.getWidthXY(TransformSpace.LOCAL) - s)/a-s;
		float x = graph.getCenterPointLocal().x - graph.getWidthXY(TransformSpace.LOCAL)/2 + s;
		float y = graph.getCenterPointLocal().y + graph.getHeightXY(TransformSpace.LOCAL)/2 - s;
		
		for (int i=0; i<this.getValues().size(); i++) {
			float h = this.getValues().get(i)/100*mH;
			final MTRoundRectangle bar = new MTRoundRectangle(this.getpApplet(), x+i*(w+s), y-h, 0, w, h, 5, 5);
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
