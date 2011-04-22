package view.layers;

import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.util.math.Vector3D;

import controllers.SuggestableScene;

public class PannLayer extends MTEllipse {

	public PannLayer(SuggestableScene scene) {
		super(scene.getMTApplication(), new Vector3D(scene.getMTApplication().getWidth()/2,
					scene.getMTApplication().getHeight()/2), 10000000, 10000000);
		this.setNoFill(true);
	}

}
