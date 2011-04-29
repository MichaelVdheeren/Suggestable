package view.components;

import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public class MTPanelButton extends MTSvgButton {
	private final MTEllipse selectionCircle;
	
	public MTPanelButton(PApplet pa, String fileString) {
		super(pa, fileString);
		
		float width = this.getWidthXYGlobal()-10;
		float height = this.getHeightXYGlobal()-10;
		
		selectionCircle = new MTEllipse(pa, new Vector3D(0,0), width, height);
		this.addChild(selectionCircle);
		selectionCircle.setNoFill(true);
		selectionCircle.setStrokeWeight(3);
		selectionCircle.setPositionRelativeToParent(this.getCenterPointLocal());
	}
	
	public void setSelected(boolean selected) {
		selectionCircle.setVisible(selected);
	}
	
	public boolean isSelected() {
		return selectionCircle.isVisible();
	}

}
