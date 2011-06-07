package view.elements.observers;

import java.util.Observable;
import java.util.Observer;

import view.elements.RetrievedElement;
import view.elements.SuggestedElement;
import view.scene.SuggestableScene;
import bookshelf.AbstractBookProcessor;
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
		AbstractBookProcessor processor = (AbstractBookProcessor) o;
		
		SuggestedElement s = new SuggestedElement(getScene(), 100, book);
		
		if (!p.isDestroyed())
			getScene().addElement(s, p);
		
		if (processor.isFinished())
			p.setLoading(false);
	}

	private SuggestableScene getScene() {
		return this.scene;
	}
}
