package view.components.specific;

import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import view.scene.SuggestableScene;


public class MTTrashCan extends MTEllipse {
	private float scale = 1/1.2f;
	private boolean hovered = false;
	private final MTSvgButton trash;
	
	public MTTrashCan(SuggestableScene scene) {
		super(scene.getMTApplication(), new Vector3D(0, 0),100,100);
		this.setFillColor(new MTColor(0, 0, 0, 255));
		this.setStrokeWeight(2.5f);
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		this.removeAllGestureEventListeners();
		
		trash = new MTSvgButton(scene.getMTApplication(),"data/icons/trash.svg");
		this.addChild(trash);
		trash.setWidthXYGlobal(60);
		trash.setPositionRelativeToParent(getCenterPointLocal().addLocal(new Vector3D(-45,0)));
		trash.setPickable(false);
	}
	
	public void setHovered(boolean hovered) {
		if (hovered == this.hovered)
			return;
		
		scale = 1/scale;
		trash.scaleGlobal(scale, scale, 1f, trash.getCenterPointGlobal());
		this.hovered = hovered;
	}
	
	public boolean isHovered() {
		return this.hovered;
	}
}
