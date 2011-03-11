package view.observers;

import java.util.Observable;
import java.util.Observer;

import view.SuggestableScene;
import view.universe.Placeholder;
import view.universe.Suggestion;
import bookshelf.apis.google.GoogleBook;

public class SuggestionObserver implements Observer {
	private final SuggestableScene scene;
	private final Placeholder p;
	
	public SuggestionObserver(SuggestableScene scene, Placeholder p) {
		this.scene = scene;
		this.p = p;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		GoogleBook book = (GoogleBook) arg;
		Suggestion s = new Suggestion(getScene(), 0, 0, 75, book);
		s.setPositionGlobal(p.getCenterPointGlobal());
		getScene().addSuggestion(s,p);
	}

	private SuggestableScene getScene() {
		return this.scene;
	}
}
