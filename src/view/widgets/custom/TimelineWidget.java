package view.widgets.custom;

import java.util.ArrayList;
import java.util.Collections;

import org.mt4j.components.MTComponent;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;
import view.widgets.AbstractWindow;

public class TimelineWidget extends AbstractWindow {
	private ArrayList<Integer> values = new ArrayList<Integer>();
	
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

	public ArrayList<Integer> getValues() {
		return values;
	}

	public void setValues(ArrayList<Integer> values) {
		this.values = values;
		
		// Clear
		graph.removeAllChildren();
		Collections.sort(values);
		
		if (values.isEmpty())
			return;
		
		int lowest = values.get(0);
		int highest = values.get(values.size()-1);
		int amount = highest - lowest + 1;
		
		int maxCount = 0;
		int previousIndex = -1;
		
		for (int i=lowest; i<=highest; i++) {
			int index = values.lastIndexOf(i);
			
			if (index == -1)
				continue;
			
			int count = index-previousIndex;
			
			if (maxCount < count)
				maxCount = count;
			
			previousIndex = index;
		}
		
		previousIndex = -1;
		
		float margin = 5.0f;
		float maxHeight = graph.getHeightXY(TransformSpace.LOCAL) - 2*margin;
		float minHeight = 10;
		float width = (graph.getWidthXY(TransformSpace.LOCAL) - margin)/amount-margin;
		float x = graph.getCenterPointLocal().x - graph.getWidthXY(TransformSpace.LOCAL)/2 + margin;
		float y = graph.getCenterPointLocal().y + graph.getHeightXY(TransformSpace.LOCAL)/2 - margin;
		
		for (int i=lowest; i<=highest; i++) {
			int index = values.lastIndexOf(i);
			int count = 0;
			
			if (index != -1) {
				count = index-previousIndex;
				previousIndex = index;
			}
			
			float h = minHeight + count*((maxHeight-minHeight)/maxCount);
			final MTRoundRectangle bar = new MTRoundRectangle(this.getpApplet(), x+(i-lowest)*(width+margin), y-h, 0, width, h, 5, 5);
			bar.setFillColor(white);
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

//bar.removeAllGestureEventListeners();
//bar.registerInputProcessor(new TapProcessor(getpApplet()));
//bar.addGestureListener(TapProcessor.class, new IGestureEventListener() {
//	@Override
//	public boolean processGestureEvent(MTGestureEvent e) {
//		if (e.getId() == MTGestureEvent.GESTURE_ENDED)
//			if (bar.getFillColor() == white)
//				bar.setFillColor(blue);
//			else
//				bar.setFillColor(white);
//		
//		return true;
//	}
//});
