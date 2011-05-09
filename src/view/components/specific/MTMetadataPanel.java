package view.components.specific;

import java.awt.Image;
import java.io.IOException;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.widgets.MTImage;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;
import processing.core.PImage;
import view.components.MTPanel;
import view.components.MTPanelButton;
import bookshelf.apis.google.GoogleBook;

public class MTMetadataPanel extends MTPanel {

	public MTMetadataPanel(final PApplet pApplet, final float width, final float height, GoogleBook book) {
		super(pApplet, width, height, new MTPanelButton(pApplet, "data/icons/circle-tag.svg"));
		
		try {
			Image image = book.getCover();
			MTImage cover = new MTImage(pApplet, new PImage(image));
			addChild(cover);
			cover.setHeightXYGlobal(150);
			cover.setPositionRelativeToParent(new Vector3D(5,5).addLocal(
					new Vector3D(cover.getWidthXY(TransformSpace.GLOBAL)/2,cover.getHeightXY(TransformSpace.GLOBAL)/2)
					));
			
			// Fonts
			IFont font = FontManager.getInstance().createFont(pApplet, "fonts/Trebuchet MS.ttf", 
					16, 							// Font size
					new MTColor(255,255,255));	// Font color
			
			MTTextArea meta = new MTTextArea(pApplet, 0, 0, width-cover.getWidthXY(TransformSpace.GLOBAL)-15, 150,font);
			meta.setNoFill(true);
			meta.setNoStroke(true);
			addChild(meta);
			meta.setAnchor(PositionAnchor.UPPER_LEFT);
			meta.setPositionRelativeToParent(new Vector3D(cover.getWidthXY(TransformSpace.GLOBAL)+10,0));
			
			String metaString = "";
			
			if (book.hasTitle())
				metaString += "Title:\t\t" + book.getTitle() + "\n";
			if (book.hasAuthors()) {
				String authors = book.getAuthors().toString();
				metaString += "Authors:\t" + authors.substring(1, authors.length()-1) + "\n";
			}
			metaString += "\n";
			if (book.hasPublishingYear())
				metaString += "Publication year: \t" + book.getPublishingYear() + "\n";
			if (book.hasPublisher())
				metaString += "Publication by:\t\t" + book.getPublisher() + "\n";
			metaString += "\n";
			if (book.hasISBN())
				metaString += "ISBN-number:\t\t" + book.getISBN() + "\n";
			
			meta.setText(metaString);
			
			MTTextArea summary = new MTTextArea(pApplet, 0, 0, width-10, height-cover.getHeightXY(TransformSpace.GLOBAL)-20,font);
			summary.setNoFill(true);
			summary.setNoStroke(true);
			addChild(summary);
			summary.setAnchor(PositionAnchor.UPPER_LEFT);
			summary.setPositionRelativeToParent(new Vector3D(5,cover.getHeightXY(TransformSpace.GLOBAL)+10));
			
			if (book.hasSummary())
				summary.setText(book.getSummary());
			else
				summary.setText("No summary available");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}

}
