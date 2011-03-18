package view.elements.observers;

import java.util.Observable;
import java.util.Observer;

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
		RetrievedElement p = new RetrievedElement(getScene(), 200, 200, 50, book);
		getScene().addRetrievedElement(p);
	}

	private SuggestableScene getScene() {
		return this.scene;
	}
}
