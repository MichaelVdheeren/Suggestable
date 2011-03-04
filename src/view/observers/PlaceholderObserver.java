package view.observers;

import java.util.Observable;
import java.util.Observer;

import view.SuggestableScene;
import view.universe.Placeholder;
import bookshelf.apis.libis.LibisBook;

public class PlaceholderObserver implements Observer {
	private final SuggestableScene scene;
	
	public PlaceholderObserver(SuggestableScene scene) {
		this.scene = scene;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		LibisBook book = (LibisBook) arg;
		Placeholder p = new Placeholder(getScene().getMTApplication(), 200, 200, 50, book);
		getScene().addPlaceholder(p);
	}

	private SuggestableScene getScene() {
		return this.scene;
	}
}
