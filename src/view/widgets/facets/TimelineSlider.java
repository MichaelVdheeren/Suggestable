package view.widgets.facets;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.shapes.MTLine;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Matrix;
import org.mt4j.util.math.Vector3D;

public class TimelineSlider extends MTLine {
	private final TimelineWidget widget;
	private int value;
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		
		Matrix transformation = widget.getGraph().getGlobalInverseMatrix();
		Vector3D position = getCenterPointGlobal();
		position.transform(transformation);
		position.setX(widget.getBars().get(getValue()).getCenterPointRelativeToParent().getX());
		setPositionRelativeToOther(widget.getGraph(), position);
		widget.updateSelection();
	}

	public TimelineSlider(final TimelineWidget widget) {
		super(widget.getpApplet(),0,0,0,widget.getGraph().getHeightXY(TransformSpace.GLOBAL)+40);
		this.widget = widget;
		this.setStrokeWeight(5);
		this.translate(new Vector3D(0, 30));
		
		Vector3D handlerCenter = new Vector3D(0,widget.getGraph().getHeightXY(TransformSpace.GLOBAL)+60);
		MTEllipse handler = new MTEllipse(widget.getpApplet(), handlerCenter, 20, 20);
		handler.setFillColor(new MTColor(0, 0, 0, 100));
		handler.setStrokeWeight(2.5f);
		handler.setStrokeColor(new MTColor(255, 255, 255, 255));
		
		this.addChild(handler);
		this.setComposite(true);
		this.setVisible(false);
		
		this.removeAllGestureEventListeners();
		this.addGestureListener(DragProcessor.class, new IGestureEventListener() {
			
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				DragEvent de = (DragEvent) ge;
				
				if (de.getId() == DragEvent.GESTURE_UPDATED) {
					Vector3D translation = de.getTranslationVect();
					Matrix transformation = widget.getGraph().getGlobalInverseMatrix();
					translation.transformDirectionVector(transformation);
					translation.setY(0);
					
					Vector3D position = getCenterPointGlobal();
					position.transform(transformation);
					position.translate(translation);
					
					int lowestValue = widget.getLowestValue();
					int highestValue = widget.getHighestValue();
					
					float min = widget.getBars().get(lowestValue).getCenterPointRelativeToParent().getX();
					float max = widget.getBars().get(highestValue).getCenterPointRelativeToParent().getX();
					
					if (position.getX() < min)
						position.setX(min);
					if (position.getX() > max)
						position.setX(max);
					
					setPositionRelativeToOther(widget.getGraph(), position);
				} else if (de.getId() == DragEvent.GESTURE_ENDED) {
					Matrix transformation = widget.getGraph().getGlobalInverseMatrix();
					Vector3D position = getCenterPointGlobal();
					position.transform(transformation);
					
					int lowestValue = widget.getLowestValue();
					int highestValue = widget.getHighestValue();
					
					float min = widget.getBars().get(lowestValue).getCenterPointRelativeToParent().getX();
					float max = widget.getBars().get(highestValue).getCenterPointRelativeToParent().getX();
					float distance = max-min;
					
					for (int i=lowestValue; i<=highestValue; i++) {
						Vector3D barPosition = widget.getBars().get(i).getCenterPointRelativeToParent();
						float barDistance = Math.abs(position.getX()-barPosition.getX());
						if (barDistance < distance) {
							distance = barDistance;
							setValue(i);
						}
					}
				}
				
				return true;
			}
		});
	}
}
