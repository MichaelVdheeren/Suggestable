package view.elements.observers;

import java.util.Observable;
import java.util.Observer;

import org.mt4j.util.math.Vector3D;

import controllers.SuggestableScene;

import view.elements.RetrievedElement;
import bookshelf.apis.libis.LibisBook;

public class RetrievedElementBirthObserver implements Observer {
	private final SuggestableScene scene;
	
	public RetrievedElementBirthObserver(SuggestableScene scene) {
		this.scene = scene;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		LibisBook book = (LibisBook) arg;
		RetrievedElement p = new RetrievedElement(getScene(), 50, book);
		p.setPositionGlobal(scene.getOrbWidget().getCenterPointGlobal().addLocal(new Vector3D(1, 1)));
		getScene().addElement(p);
	}

	private SuggestableScene getScene() {
		return this.scene;
	}
}
