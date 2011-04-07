package view.widgets;

import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public class Progressbar extends MTRectangle {
	private float value;
	private final float margin = 3;
	private MTRectangle bars[] = new MTRectangle[10];
	
	public Progressbar(PApplet pApplet, float width, float height) {
		super(pApplet, 0, 0, 0, width, height);
		setComposite(true);
		float sWidth = (width-9*margin)/10;
		
		this.setNoFill(true);
		this.setNoStroke(true);
		
		for (int i=0; i<10; i++) {
			float x = i*(margin+sWidth)+sWidth/2;
			bars[i] = new MTRectangle(pApplet, sWidth, height);
			this.addChild(bars[i]);
			bars[i].setPositionRelativeToParent(new Vector3D(x,height/2));
		}
	}
	
	public void setValue(float value) {
		this.value = value;
	}
	
	public float getValue() {
		return this.value;
	}

}
