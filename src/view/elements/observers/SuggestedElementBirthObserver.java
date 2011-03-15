package view.elements.observers;

import java.util.Observable;
import java.util.Observer;

import org.mt4j.util.math.Vector3D;

import view.SuggestableScene;
import view.elements.RetrievedElement;
import view.elements.SuggestedElement;
import bookshelf.apis.google.GoogleBook;

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
		SuggestedElement s = new SuggestedElement(getScene(), 0, 0, 75, book);
		s.setPositionGlobal(p.getCenterPointGlobal().addLocal(new Vector3D(1, 1)));
		getScene().addSuggestion(s,p);
	}

	private SuggestableScene getScene() {
		return this.scene;
	}
}
