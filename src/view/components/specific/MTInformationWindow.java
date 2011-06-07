package view.components.specific;

import org.mt4j.components.TransformSpace;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;

import view.components.MTAbstractWindow;
import view.components.MTPanel;
import view.components.MTPanelContainer;
import view.scene.SuggestableScene;
import bookshelf.apis.google.GoogleBook;

public class MTInformationWindow extends MTAbstractWindow {
	public MTInformationWindow(SuggestableScene scene, float x, float y, float w, float h, GoogleBook book) {
		super(scene.getMTApplication(), x, y, w, h, book.getTitle());
		
		float width = this.getContainer().getWidthXY(TransformSpace.GLOBAL);
		float height = this.getContainer().getHeightXY(TransformSpace.GLOBAL);
		MTPanelContainer panelContainer = new MTPanelContainer(scene.getMTApplication(), width, height);
		this.getContainer().addChild(panelContainer);
		panelContainer.setPositionRelativeToParent(this.getContainer().getCenterPointLocal());
		
		btnClose.removeAllGestureEventListeners();
		btnClose.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent) ge;
				if (te.getTapID() == TapEvent.TAPPED) {
					setVisible(false);
					destroy();
				}
				
				return true;
			}
		});
		
		MTPanel metaPanel = new MTMetadataPanel(scene.getMTApplication(), width-20, height-60, book);
		metaPanel.setNoFill(true);
		metaPanel.setNoStroke(true);
		metaPanel.setComposite(true);
		panelContainer.addPanel(metaPanel);
		
		if (book.hasPagePreviews()) {
			MTPanel previewPanel = new MTBookPreviewPanel(scene.getMTApplication(), width-20, height-60,book);
			previewPanel.setNoFill(true);
			previewPanel.setNoStroke(true);
			panelContainer.addPanel(previewPanel);
		}
		
		MTLocationPanel locationPanel = new MTLocationPanel(scene, width-20, height-60,book);
		locationPanel.setNoStroke(true);
		panelContainer.addPanel(locationPanel);
	}
}
