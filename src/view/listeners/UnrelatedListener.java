package view.listeners;

import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.sceneManagement.IPreDrawAction;
import org.mt4j.util.math.Vector3D;

public class UnrelatedListener implements IPreDrawAction {
	private final AbstractShape shape1;
	private final AbstractShape shape2;
	private static final float springK = 0.01f;
	private static final float targetLength = 250;
	
	public UnrelatedListener(AbstractShape component1, AbstractShape component2) {
		this.shape1 = component1;
		this.shape2 = component2;
	}
	
	@Override
	public void processAction() {
		Vector3D centerTN1 = shape1.getCenterPointGlobal();
		Vector3D centerTN2 = shape2.getCenterPointGlobal();
		
		double dx = centerTN1.x-centerTN2.x;
		double dy = centerTN1.y-centerTN2.y;
		
		double distance = Math.sqrt(dx*dx+dy*dy);
		
		double diffX = dx/distance*springK*(distance-targetLength);
		double diffY = dy/distance*springK*(distance-targetLength);
		
		if(distance > targetLength) {
			diffX = 0;
			diffY = 0;
		}
		
		Vector3D diff2 = new Vector3D((float)diffX,(float)diffY,0);
		Vector3D diff1 = new Vector3D(-(float)diffX,-(float)diffY,0);
		shape1.translate(diff1);
		shape2.translate(diff2);
	}

	@Override
	public boolean isLoop() {
		return true;
	}
}
