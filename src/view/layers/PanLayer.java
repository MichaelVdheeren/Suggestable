package view.layers;

import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.util.camera.MTCamera;
import org.mt4j.util.math.Vector3D;

import controllers.SuggestableScene;

public class PanLayer extends MTEllipse {
//	private final OrbWidget orbWidget;
	
	public PanLayer(final SuggestableScene scene) {
		super(scene.getMTApplication(), new Vector3D(scene.getMTApplication().getWidth()/2,
					scene.getMTApplication().getHeight()/2), 10000000, 10000000);
		this.setNoFill(true);
	}
	
	
}
