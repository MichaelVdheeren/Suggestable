package view.elements.actions;

import org.mt4j.components.TransformSpace;
import org.mt4j.util.math.Vector3D;

import view.elements.RetrievedElement;
import view.elements.SuggestedElement;

// TODO: some calculations in here can be done quite better!
public class RelatedElementPreDrawAction extends AbstractElementPreDrawAction {
	private final RetrievedElement retrieved;
	private final SuggestedElement suggested;
	private static final float springK = 0.01f;
	private final float forceMultiplier = 10f;
	
	public RelatedElementPreDrawAction(RetrievedElement retrieved, SuggestedElement suggested) {
		this.retrieved = retrieved;
		this.suggested = suggested;
		addAssociatedElement(retrieved);
		addAssociatedElement(suggested);
	}
	
	@Override
	public void processAction() {
		Vector3D centerTN1 = retrieved.getCenterPointGlobal();
		Vector3D centerTN2 = suggested.getCenterPointGlobal();
		
		float width1 = 0, width2 = 0, height1 = 0, height2 = 0;
		float targetLength = retrieved.getGForce()*forceMultiplier;
		
		try {
			width1 = retrieved.getWidthXY(TransformSpace.GLOBAL);
			height1 = retrieved.getHeightXY(TransformSpace.GLOBAL);
			width2 = suggested.getWidthXY(TransformSpace.GLOBAL);
			height2 = suggested.getHeightXY(TransformSpace.GLOBAL);
			
			float x1 = centerTN1.x - centerTN2.x;
			float y1 = centerTN1.y - centerTN2.y;
			float x2 = centerTN2.x - centerTN1.x;
			float y2 = centerTN2.y - centerTN1.y;
			
			float cos1 = (float)(x1/(Math.sqrt(x1*x1+y1*y1)));
			float sin1 = (float)(y1/(Math.sqrt(x1*x1+y1*y1)));
			float cos2 = (float)(x2/(Math.sqrt(x2*x2+y2*y2)));
			float sin2 = (float)(y2/(Math.sqrt(x2*x2+y2*y2)));

			float xDiff1 = sin1*height1/2;
			float yDiff1 = cos1*width1/2;
			float xDiff2 = sin2*height2/2;
			float yDiff2 = cos2*width2/2;

			float dist1 = (float)Math.sqrt(xDiff1*xDiff1+yDiff1*yDiff1);
			float dist2 = (float)Math.sqrt(xDiff2*xDiff2+yDiff2*yDiff2);
			
			targetLength = dist1+dist2+50;
		} catch(NullPointerException exc) {
			System.out.println("We have a problem");
		}

		float distance = centerTN1.distance2D(centerTN2);
		Vector3D distanceVector = centerTN1.getSubtracted(centerTN2);
		
		// TODO: find better solution for this
		if (distance == 0)
			distance = 0.00001f;
		
		distanceVector.scaleLocal(1/distance*springK*(distance-targetLength));
		
		if(Math.abs(distance-targetLength) < 1) {
			distanceVector = new Vector3D(0,0);
		}

		if (!suggested.isDragged())
			suggested.translate(distanceVector);
	}

	@Override
	public boolean isLoop() {
		return true;
	}

}
