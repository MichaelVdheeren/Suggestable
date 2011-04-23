package view.elements.actions;

import org.mt4j.util.math.Vector3D;

import view.elements.SuggestedElement;

//TODO: some calculations in here can be done quite better!
public class UnrelatedElementPreDrawAction extends AbstractElementPreDrawAction {
	private final SuggestedElement component1;
	private final SuggestedElement component2;
	private static final float springK = 0.01f;
	private static final float targetLength = 250;
	
	public UnrelatedElementPreDrawAction(SuggestedElement component1, SuggestedElement component2) {
		this.component1 = component1;
		this.component2 = component2;
		addAssociatedElement(component1);
		addAssociatedElement(component2);
	}
	
	@Override
	public void processAction() {
		Vector3D centerTN1 = component1.getCenterPointGlobal();
		Vector3D centerTN2 = component2.getCenterPointGlobal();
		
		float distance = centerTN2.distance2D(centerTN1);
		Vector3D distanceVector = centerTN2.getSubtracted(centerTN1);
		// TODO: find better solution for this
		if (distance == 0)
			distance = 0.00001f;
		
		distanceVector.scaleLocal(1/distance*springK*(distance-targetLength));
		
		if(distance > targetLength) {
			distanceVector = new Vector3D(0,0);
		}
		
		// If one of the two components is not visible, then don't translate
		boolean visibility = component1.isVisible() && component2.isVisible();
		
		if (!component1.isDragged() && visibility)
			component1.translate(distanceVector);
		if (!component2.isDragged() && visibility)
			component2.translate(distanceVector.getInverted());
	}

	@Override
	public boolean isLoop() {
		return true;
	}
}
