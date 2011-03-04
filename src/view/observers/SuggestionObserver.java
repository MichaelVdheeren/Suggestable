package view.observers;

import java.util.Observable;
import java.util.Observer;

import view.SuggestableScene;
import view.universe.Suggestion;
import bookshelf.apis.google.GoogleBook;

public class SuggestionObserver implements Observer {
	private final SuggestableScene scene;
	
	public SuggestionObserver(SuggestableScene scene) {
		this.scene = scene;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		GoogleBook book = (GoogleBook) arg;
		Suggestion s = new Suggestion(getScene().getMTApplication(), 100, 100, 75, book);
		getScene().addSuggestion(s);
	}

	private SuggestableScene getScene() {
		return this.scene;
	}
}
