package view.widgets.listeners;

import org.mt4j.components.MTComponent;
import org.mt4j.sceneManagement.IPreDrawAction;
import org.mt4j.util.math.Vector3D;

import view.elements.IElement;
import controllers.SuggestableScene;

public class HoverListener implements IPreDrawAction {
	private final SuggestableScene scene;
	private final MTComponent component;
	private final Vector3D center;
	private boolean hovered = false;
	
	public HoverListener(SuggestableScene scene, MTComponent component, Vector3D center) {
		this.scene = scene;
		this.component = component;
		this.center = center;
	}
	
	@Override
	public void processAction() {		
		MTComponent component = getScene().getCanvas().pick(center.x, center.y).getNearestPickResult();

        if (component instanceof IElement) {
        	IElement element = (IElement) component;
        	if (element.isDragged()) {
        		setHovered();
        		return;
        	}
        }
		
		resetHovered();
	}

	@Override
	public boolean isLoop() {
		return true;
	}
	
	private SuggestableScene getScene() {
		return this.scene;
	}

	private MTComponent getComponent() {
		return component;
	}
	
	private boolean isHovered() {
		return this.hovered;
	}
	
	private void setHovered() {
		if (isHovered())
			return;
		
		this.hovered = true;
		this.getComponent().scaleGlobal(1.2f, 1.2f, 1f, center);
	}
	
	private void resetHovered() {
		if (!isHovered())
			return;
		
		this.hovered = false;
		this.getComponent().scaleGlobal(1/1.2f, 1/1.2f, 1f, center);
	}
}
