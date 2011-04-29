package view.components.actions;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.sceneManagement.IPreDrawAction;
import org.mt4j.util.math.Vector3D;

public class ComponentDistancePreDrawAction implements IPreDrawAction {
	private final AbstractShape component1;
	private final AbstractShape component2;
	private static final float springK = 0.01f;
	
	public ComponentDistancePreDrawAction(AbstractShape component1, AbstractShape component2) {
		this.component1 = component1;
		this.component2 = component2;
	}
	
	@Override
	public void processAction() {
		if (!(component2.isVisible() && component1.isVisible()))
			return;
		
		Vector3D centerTN1 = component1.getCenterPointGlobal();
		Vector3D centerTN2 = component2.getCenterPointGlobal();
		
		float width1 = 0, width2 = 0, height1 = 0, height2 = 0, targetLength = 250;
		
		try {
			width1 = component1.getWidthXY(TransformSpace.GLOBAL);
			height1 = component1.getHeightXY(TransformSpace.GLOBAL);
			width2 = component2.getWidthXY(TransformSpace.GLOBAL);
			height2 = component2.getHeightXY(TransformSpace.GLOBAL);
			
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
		
		float distance = centerTN2.distance2D(centerTN1);
		Vector3D distanceVector = centerTN2.getSubtracted(centerTN1);
		// TODO: find better solution for this
		if (distance == 0)
			distance = 0.00001f;
		
		distanceVector.scaleLocal(1/distance*springK*(distance-targetLength));
		
		if(distance > targetLength) {
			distanceVector = new Vector3D(0,0);
		}
		
		component1.translate(distanceVector);
		component2.translate(distanceVector.getInverted());
	}

	@Override
	public boolean isLoop() {
		return true;
	}
}
