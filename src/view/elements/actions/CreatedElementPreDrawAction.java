package view.elements.actions;

import java.util.Random;

import org.mt4j.util.math.Vector3D;

import view.elements.AbstractElement;


public class CreatedElementPreDrawAction extends AbstractElementPreDrawAction {
	private final Vector3D centerTN1;
	private final AbstractElement component2;
	private final float springK = 0.03f;
	private final float targetLength;
	private boolean stopLoop = false;
	
	public CreatedElementPreDrawAction(Vector3D vector, AbstractElement element) {
		this.centerTN1 = vector;
		this.component2 = element;
		addAssociatedElement(element);
		
		Random r = new Random();
		targetLength = 300 + r.nextInt(700);
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
		
		if(distance > targetLength || component2.isDragged()) {
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
