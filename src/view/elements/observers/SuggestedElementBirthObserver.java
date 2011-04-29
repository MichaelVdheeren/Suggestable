package view.elements.observers;

import java.util.Observable;
import java.util.Observer;

import view.elements.RetrievedElement;
import view.elements.SuggestedElement;
import bookshelf.apis.google.GoogleBook;
import controllers.SuggestableScene;

public class SuggestedElementBirthObserver implements Observer {
	private final SuggestableScene scene;
	private final RetrievedElement p;
	
	public SuggestedElementBirthObserver(SuggestableScene scene, RetrievedElement p) {
		this.scene = scene;
		this.p = p;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		GoogleBook book = (GoogleBook) arg;
		SuggestedElement s = new SuggestedElement(getScene(), 125, book);
		getScene().addElement(s, p);
	}

	private SuggestableScene getScene() {
		return this.scene;
	}
}
