package view.widgets.listeners;

import org.mt4j.components.MTComponent;
import org.mt4j.sceneManagement.IPreDrawAction;
import org.mt4j.util.math.Vector3D;

import view.elements.AbstractElement;
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

        if (component instanceof AbstractElement) {
        	AbstractElement element = (AbstractElement) component;
        	if (element.isDragged()) {
        		setHovered(true);
        		return;
        	}
        }
		
		setHovered(false);
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
	
	public boolean isHovered() {
		return this.hovered;
	}
	
	private void setHovered(boolean hovered) {
		if (hovered == this.hovered)
			return;
		
		this.hovered = hovered;
		float scale = 1.2f;
		
		if (!hovered)
			scale = 1/scale;
		
		getComponent().scaleGlobal(scale, scale, 1f, center);
	}
}
