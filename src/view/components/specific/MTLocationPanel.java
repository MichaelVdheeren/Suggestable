package view.components.specific;

import java.util.ArrayList;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.clipping.Clip;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.widgets.MTSvg;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import view.components.MTMessage;
import view.components.MTPanel;
import view.components.MTPanelButton;
import view.scene.SuggestableScene;
import bookshelf.AbstractBook;
import bookshelf.apis.libis.LibisLocation;
import bookshelf.apis.libis.parameters.LibisLibrary;
import bookshelf.exceptions.BookshelfUnavailableException;

public class MTLocationPanel extends MTPanel {

	public MTLocationPanel(final SuggestableScene scene, final float width, final float height, final AbstractBook book) {
		super(scene.getMTApplication(), width, height, new MTPanelButton(scene.getMTApplication(), "data/icons/circle-pointer.svg"));
		
		setFillColor(new MTColor(224,224,244));
		
		Thread floorLoader = new Thread(new Runnable() {
			@Override
			public void run() {
				final MTSvg floor = new MTSvg(scene.getMTApplication(), "data/floors/niv00.v17.svg");
				addChild(floor);
				floor.setSizeXYGlobal(getWidthXYGlobal()-10, getHeightXYGlobal()-10);
				floor.setPositionRelativeToParent(getCenterPointLocal());
				floor.setClip(new Clip(scene.getMTApplication(), 0, 0, width, height));
				floor.removeAllGestureEventListeners();
				
				IFont font = FontManager.getInstance().createFont(scene.getMTApplication(), "fonts/Trebuchet MS.ttf", 
				14, 	//Font size
				new MTColor(255,255,255));	//Font color
				
				String desc = "Not found in library!";
				
				try {
					ArrayList<LibisLocation> locations = scene.getBookController().getBookLocations(book, LibisLibrary.KULeuven_Campusbibl_Arenberg);
				
					boolean found = false;
					
					
					for (int i=0; i<locations.size() && !found; i++)
						if (locations.get(i).getCollection().equals("WBIB")) {
							desc = locations.get(i).getShelf();
						}
				} catch (BookshelfUnavailableException e) {
					// Nothing
				}
				
				MTMessage locationMessage = new MTMessage(scene,desc,font);
				addChild(locationMessage);
				locationMessage.removeAllGestureEventListeners();
				locationMessage.setPositionRelativeToParent(getCenterPointLocal().addLocal(new Vector3D(0,-getHeightXY(TransformSpace.LOCAL)/2+30)));
			}
		});
		
		floorLoader.start();		
	}
}
