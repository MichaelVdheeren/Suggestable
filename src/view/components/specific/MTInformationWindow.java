package view.components.specific;

import java.awt.Image;
import java.io.IOException;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.widgets.MTImage;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;
import processing.core.PImage;
import view.components.MTAbstractWindow;
import view.components.MTPanel;
import view.components.MTPanelButton;
import view.components.MTPanelContainer;
import bookshelf.apis.google.GoogleBook;

public class MTInformationWindow extends MTAbstractWindow {
	private final GoogleBook book;
	private final PApplet pApplet;
	
	public MTInformationWindow(PApplet pApplet, float x, float y, float w, float h, GoogleBook book) {
		super(pApplet, x, y, w, h, "Information");
		this.pApplet = pApplet;
		this.book = book;
		
		float width = this.getContainer().getWidthXY(TransformSpace.GLOBAL);
		float height = this.getContainer().getHeightXY(TransformSpace.GLOBAL);
		MTPanelContainer panelContainer = new MTPanelContainer(pApplet, width, height);
		this.getContainer().addChild(panelContainer);
		panelContainer.setPositionRelativeToParent(this.getContainer().getCenterPointLocal());
		
		MTPanelButton metaIcon = new MTPanelButton(pApplet, "data/icons/circle-tag.svg");
		MTPanel metaPanel = new MTPanel(pApplet, width-20, height-60,metaIcon);
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
		
//		MTLocationPreviewPanel locationPanel = new MTLocationPreviewPanel(pApplet, width-20, height-60);
//		locationPanel.setNoStroke(true);
//		panelContainer.addPanel(locationPanel);
		
		try {
			Image image = getBook().getCover();
			MTImage cover = new MTImage(getpApplet(), new PImage(image));
			metaPanel.addChild(cover);
			cover.setHeightXYGlobal(metaPanel.getHeightXY(TransformSpace.GLOBAL));
			cover.setPositionRelativeToParent(metaPanel.getCenterPointLocal().addLocal(
					new Vector3D(-cover.getWidthXY(TransformSpace.RELATIVE_TO_PARENT)/2-5,0)));
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		IFont font = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
//				14, 	//Font size
//				new MTColor(255,255,255));	//Font color
		
//		MTTextArea title = new MTTextArea(getpApplet(),font);
//		getContainer().addChild(title);
//		title.setAnchor(PositionAnchor.UPPER_LEFT);
//		title.setPositionRelativeToParent(new Vector3D(100f,7.5f,0));
//		title.setText(getBook().getTitle());
//		title.setNoStroke(true);
//		title.setNoFill(true);
//		
//		MTTextArea authors = new MTTextArea(getpApplet(),font);
//		getContainer().addChild(authors);
//		authors.setAnchor(PositionAnchor.UPPER_LEFT);
//		authors.setPositionRelativeToParent(new Vector3D(100f,23.5f,0));
//		authors.setText(getBook().getAuthors().toString());
//		authors.setNoStroke(true);
//		authors.setNoFill(true);
//		
//		if (getBook().hasSummary()) {
//			MTList summaryScrollList = new MTList(getpApplet(), 0, 0, w-15f, h-122.5f);
//			getContainer().addChild(summaryScrollList);
//			summaryScrollList.setPositionRelativeToParent(new Vector3D(0f, 115f, 0).addLocal(summaryScrollList.getCenterOfMass2DLocal()));
//			summaryScrollList.setNoFill(true);
//			summaryScrollList.setNoStroke(true);
//			
//			MTTextArea summary = new MTTextArea(getpApplet(), 0, 0, summaryScrollList.getWidthXY(TransformSpace.GLOBAL), 1000, font);
//			summary.setText(getBook().getSummary());
//			summary.setNoStroke(true);
//			summary.setNoFill(true);
//			
//			MTListCell summaryScrollCell = new MTListCell(getpApplet(), summaryScrollList.getWidthXY(TransformSpace.GLOBAL), 1000);
//			summaryScrollList.addListElement(summaryScrollCell);
//			summaryScrollCell.addChild(summary);
//			summaryScrollCell.setNoFill(true);
//			summaryScrollCell.setNoStroke(true);
//		}
	}
	
	private PApplet getpApplet() {
		return this.pApplet;
	}
	
	private GoogleBook getBook() {
		return this.book;
	}
}
