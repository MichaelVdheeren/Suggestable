package view.elements.actions;

import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.util.math.Vector3D;

import view.widgets.specific.OrbWidget;

public class CreatedElementPreDrawAction extends AbstractElementPreDrawAction {
	private final AbstractShape component1;
	private final AbstractShape component2;
	private static final float springK = 0.01f;
	private static final float targetLength = 250;
	private boolean stopLoop = false;
	
	public CreatedElementPreDrawAction(OrbWidget component1, AbstractShape component2) {
		this.component1 = component1;
		this.component2 = component2;
	}
	
	@Override
	public void processAction() {
		Vector3D centerTN1 = component1.getCenterPointGlobal();
		Vector3D centerTN2 = component2.getCenterPointGlobal();
		
		float distance = centerTN1.distance2D(centerTN2);
		Vector3D distanceVector = centerTN1.getSubtracted(centerTN2);
		
		// TODO: find better solution for this
		if (distance == 0)
			distance = 0.00001f;
		
		distanceVector.scaleLocal(1/distance*springK*(distance-targetLength));
		
		if(distance > targetLength) {
			stopLoop = true;
		}

		if (component2.isVisible())
			component2.translate(distanceVector);
	}

	@Override
	public boolean isLoop() {
		return !stopLoop;
	}
}
