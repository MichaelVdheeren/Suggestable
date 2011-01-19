package view.widgets;

import org.mt4j.components.MTComponent;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public class TimelineSlider extends MTComponent {
	public TimelineSlider(PApplet pApplet, float x1, float y1, float x2, float y2) {
		super(pApplet);
		float d = (float) Math.toDegrees(Math.atan2(y2-y1,x2-x1));
		
		Vector3D xy1 = new Vector3D(x1,y1);
		Vector3D xy2 = new Vector3D(x2,y2);
		
		MTRoundRectangle line = new MTRoundRectangle(x1, y1-2.5f, 0, xy1.distance(xy2), 5, 2.5f, 2.5f, pApplet);
		line.rotateZ(xy1, d);
		line.setNoStroke(true);
		line.setFillColor(new MTColor(255, 255, 255, 150));
		
		Vector3D handlerCenter = xy2.getCopy();
		handlerCenter.translate(new Vector3D(9.5f,0));
		handlerCenter.rotateZ(xy2, d);
		
		MTEllipse handler = new MTEllipse(pApplet, handlerCenter, 12, 12);
		
		handler.setFillColor(new MTColor(0, 0, 0, 180));
		handler.setStrokeWeight(2.5f);
		handler.setStrokeColor(new MTColor(255, 255, 255, 150));

		this.addChild(line);
		this.addChild(handler);
		this.setComposite(true);
	}
}
