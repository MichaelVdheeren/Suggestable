package view.widgets.listeners;

import java.util.List;

import org.mt4j.components.PickResult.PickEntry;
import org.mt4j.sceneManagement.IPreDrawAction;
import org.mt4j.util.math.Vector3D;

import view.SuggestableScene;
import view.elements.RetrievedElement;
import view.widgets.buttons.ButtonRemove;

public class HoverListener implements IPreDrawAction {
	private final SuggestableScene scene;
	private final ButtonRemove shape;
	private boolean hovered = false;
	
	public HoverListener(SuggestableScene scene, ButtonRemove shape) {
		this.scene = scene;
		this.shape = shape;
	}
	
	@Override
	public void processAction() {
		Vector3D location = getShape().getCenterPointGlobal();
		
		List<PickEntry> components = getScene().getCanvas().pick(location.x, location.y).getPickList();
		
		for( PickEntry pe : components )
            if (pe.hitObj instanceof RetrievedElement) {
            	setHovered();
            	return;
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

	private ButtonRemove getShape() {
		return shape;
	}
	
	private boolean isHovered() {
		return this.hovered;
	}
	
	private void setHovered() {
		if (isHovered())
			return;
		
		this.hovered = true;
		this.getShape().scaleGlobal(1.2f, 1.2f, 1f, this.getShape().getCenterPointGlobal());
	}
	
	private void resetHovered() {
		if (!isHovered())
			return;
		
		this.hovered = false;
		this.getShape().scaleGlobal(1/1.2f, 1/1.2f, 1f, this.getShape().getCenterPointGlobal());
	}
}
