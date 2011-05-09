package view.components.actions;

import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.util.math.Vector3D;

import view.elements.actions.AbstractElementPreDrawAction;

public class ComponentCreatedPreDrawAction extends AbstractElementPreDrawAction {
	private final Vector3D centerTN1;
	private final AbstractShape component2;
	private static final float springK = 0.03f;
	private static final float targetLength = 600;
	private boolean stopLoop = false;
	
	public ComponentCreatedPreDrawAction(Vector3D vector, AbstractShape component) {
		this.centerTN1 = vector;
		this.component2 = component;
	}
	
	@Override
	public void processAction() {
		Vector3D centerTN2 = component2.getCenterPointRelativeToParent();
		
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
