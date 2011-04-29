package view.components.specific;

import org.mt4j.components.clipping.Clip;
import org.mt4j.components.visibleComponents.widgets.MTSvg;
import org.mt4j.util.MTColor;

import processing.core.PApplet;
import view.components.MTPanel;
import view.components.MTPanelButton;

public class MTLocationPreviewPanel extends MTPanel {

	public MTLocationPreviewPanel(final PApplet pApplet, final float width, final float height) {
		super(pApplet, width, height, new MTPanelButton(pApplet, "data/icons/circle-pointer.svg"));
		
		setFillColor(new MTColor(224,224,244));
		
		Thread floorLoader = new Thread(new Runnable() {
			@Override
			public void run() {
				final MTSvg floor = new MTSvg(pApplet, "data/floors/niv00.v17.svg");
				addChild(floor);
				floor.setSizeXYGlobal(getWidthXYGlobal(), getHeightXYGlobal());
				floor.setPositionRelativeToParent(getCenterPointLocal());
				floor.setClip(new Clip(pApplet, 0, 0, width, height));
				floor.removeAllGestureEventListeners();
			}
		});
		
		floorLoader.start();		
	}
}
