package view.elements.observers;

import java.util.Observable;
import java.util.Observer;

import org.mt4j.util.math.Vector3D;

import view.elements.RetrievedElement;
import view.elements.actions.CreatedElementPreDrawAction;
import bookshelf.apis.libis.LibisBook;
import controllers.SuggestableScene;

public class RetrievedElementBirthObserver implements Observer {
	private final SuggestableScene scene;
	
	public RetrievedElementBirthObserver(SuggestableScene scene) {
		this.scene = scene;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		LibisBook book = (LibisBook) arg;
		RetrievedElement p = new RetrievedElement(getScene(), 125, book);
		getScene().addElement(p);
	}

	private SuggestableScene getScene() {
		return this.scene;
	}
}
