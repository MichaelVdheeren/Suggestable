package view.components.specific;

import org.mt4j.components.TransformSpace;

import processing.core.PApplet;
import view.components.MTAbstractWindow;
import view.components.MTPanel;
import view.components.MTPanelContainer;
import bookshelf.apis.google.GoogleBook;

public class MTInformationWindow extends MTAbstractWindow {
	public MTInformationWindow(PApplet pApplet, float x, float y, float w, float h, GoogleBook book) {
		super(pApplet, x, y, w, h, book.getTitle());
		
		float width = this.getContainer().getWidthXY(TransformSpace.GLOBAL);
		float height = this.getContainer().getHeightXY(TransformSpace.GLOBAL);
		MTPanelContainer panelContainer = new MTPanelContainer(pApplet, width, height);
		this.getContainer().addChild(panelContainer);
		panelContainer.setPositionRelativeToParent(this.getContainer().getCenterPointLocal());
		
		MTPanel metaPanel = new MTMetadataPanel(pApplet, width-20, height-60, book);
		metaPanel.setNoFill(true);
		metaPanel.setNoStroke(true);
		metaPanel.setComposite(true);
		panelContainer.addPanel(metaPanel);
		
		if (book.hasPagePreviews()) {
			MTPanel previewPanel = new MTBookPreviewPanel(pApplet, width-20, height-60,book);
			previewPanel.setNoFill(true);
			previewPanel.setNoStroke(true);
			panelContainer.addPanel(previewPanel);
		}
		
//		MTLocationPanel locationPanel = new MTLocationPanel(pApplet, width-20, height-60);
//		locationPanel.setNoStroke(true);
//		panelContainer.addPanel(locationPanel);
	}
}
