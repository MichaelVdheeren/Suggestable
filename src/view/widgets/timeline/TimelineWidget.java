package view.widgets.timeline;

import java.util.ArrayList;

import org.mt4j.components.MTComponent;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.util.MTColor;

import processing.core.PApplet;
import view.widgets.window.WindowWidget;

public class TimelineWidget extends WindowWidget {
	private ArrayList<Float> values = new ArrayList<Float>();
	private ArrayList<MTRoundRectangle> bars = new ArrayList<MTRoundRectangle>();
	private ArrayList<Integer> selectedIndices = new ArrayList<Integer>();
	private PApplet pApplet;
	
	public TimelineWidget(float x, float y, PApplet pApplet) {
		super(x, y, 400, 200, pApplet);
		this.setpApplet(pApplet);
		
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
		
		ArrayList<Integer> selected = new ArrayList<Integer>();
		selected.add(3);
		selected.add(4);
		selected.add(5);
		this.setSelectedIndices(selected);
		
		float x1 = this.getCenterPointGlobal().x - this.getWidthXYGlobal()/2 + 3*this.getStrokeWeight();
		float x2 = this.getCenterPointGlobal().x + this.getWidthXYGlobal()/2 - 3*this.getStrokeWeight();
		float y1 = this.getCenterPointGlobal().y - this.getHeightXYGlobal()/2 + 30;
		float y2 = this.getCenterPointGlobal().y + this.getHeightXYGlobal()/2 + 10;
		this.addChild(new TimelineSlider(pApplet, x1, y1, x1, y2));
		this.addChild(new TimelineSlider(pApplet, x2, y1, x2, y2));
	}

	public ArrayList<Float> getValues() {
		return values;
	}

	public void setValues(ArrayList<Float> values) {
		this.values = values;
		
		// Clear
		for (MTComponent bar : this.getBars()) {
			bar.removeFromParent();
		}
		
		float space = 20;
		float interspacing = 2.0f;
		float width = (this.getWidthXYGlobal()-2*this.getStrokeWeight()-2*space)/getValues().size();
		
		for (int i=0; i<this.getValues().size(); i++) {
			float value = this.getValues().get(i);
			float bx = this.getCenterPointGlobal().x - this.getWidthXYGlobal()/2 + this.getStrokeWeight() + space + interspacing + i*width;
			float by = this.getCenterPointGlobal().y + this.getHeightXYGlobal()/2 - this.getStrokeWeight() - value; 
			MTRoundRectangle bar = new MTRoundRectangle(bx, by, 0, width-2*interspacing, value, 5, 5, this.getpApplet());
			bar.setFillColor(new MTColor(255,255,255));
			bar.setPickable(false);
			this.addChild(bar);
			this.bars.add(bar);
		}
	}

	private ArrayList<MTRoundRectangle> getBars() {
		return bars;
	}

	private PApplet getpApplet() {
		return pApplet;
	}

	private void setpApplet(PApplet pApplet) {
		this.pApplet = pApplet;
	}

	public ArrayList<Integer> getSelectedIndices() {
		return new ArrayList<Integer>(this.selectedIndices);
	}

	public void setSelectedIndices(ArrayList<Integer> selectedIndices) {
		for (Integer i : this.selectedIndices)
			this.getBars().get(i).setFillColor(new MTColor(255,255,255));
		
		this.selectedIndices = selectedIndices;
		
		for (Integer i : this.selectedIndices)
			this.getBars().get(i).setFillColor(new MTColor(1,151,253));
	}
}
