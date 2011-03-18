package view.widgets.custom;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.shapes.MTLine;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

public class TimelineSlider extends MTLine {
	private final TimelineWidget widget;
	
	public TimelineSlider(TimelineWidget widget) {
		super(widget.getpApplet(),0,0,0,widget.getGraph().getHeightXY(TransformSpace.GLOBAL)+40);
		this.widget = widget;
		this.setStrokeWeight(5);
		
		MTEllipse handler = new MTEllipse(widget.getpApplet(), new Vector3D(0,widget.getGraph().getHeightXY(TransformSpace.GLOBAL)+60), 20, 20);
		handler.setFillColor(new MTColor(0, 0, 0, 100));
		handler.setStrokeWeight(2.5f);
		handler.setStrokeColor(new MTColor(255, 255, 255, 255));
		
		this.addChild(handler);
		this.setComposite(true);
		this.setVisible(false);
	}
}
