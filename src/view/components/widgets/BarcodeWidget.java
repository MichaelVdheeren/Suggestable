package view.components.widgets;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTRectangle.PositionAnchor;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import view.components.MTAbstractWindow;
import controllers.SuggestableScene;

public class BarcodeWidget extends MTAbstractWindow {
	private final SuggestableScene scene;
	private final MTTextArea warning;
	
	public BarcodeWidget(final SuggestableScene scene, float x, float y, float w, float h) {
		super(scene.getMTApplication(), x, y, w, h, "Enter WBIB-Barcode");
		this.scene = scene;
		
		IFont font = FontManager.getInstance().createFont(scene.getMTApplication(), "fonts/Trebuchet MS.ttf", 
				16, 	//Font size
				new MTColor(255,255,255));	//Font color
		
		
		
		MTTextArea selectAllButton = new MTTextArea(scene.getMTApplication(), font);
		getContainer().addChild(selectAllButton);
		selectAllButton.setText("Select All");
		selectAllButton.setFillColor(new MTColor(0, 0, 0, 255));
		selectAllButton.setStrokeWeight(2.5f);
		selectAllButton.setStrokeColor(new MTColor(255, 255, 255, 150));
		selectAllButton.setAnchor(PositionAnchor.LOWER_RIGHT);
		selectAllButton.setPositionRelativeToParent(new Vector3D(getContainer().getWidthXY(TransformSpace.LOCAL)-5, getContainer().getHeightXY(TransformSpace.LOCAL)-5));
		selectAllButton.removeAllGestureEventListeners();
		selectAllButton.registerInputProcessor(new TapProcessor(scene.getMTApplication()));
		selectAllButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent) ge;
				
				if (te.getTapID() == TapEvent.TAPPED) {
					// TODO
				}
				
				return true;
			}
		});
		
		warning = new MTTextArea(scene.getMTApplication(), font);
		this.addChild(warning);
		warning.setText("No keywords found");
		warning.setPositionRelativeToOther(getContainer(), getContainer().getCenterPointLocal());
		warning.setNoStroke(true);
		warning.setNoFill(true);
	}
}
