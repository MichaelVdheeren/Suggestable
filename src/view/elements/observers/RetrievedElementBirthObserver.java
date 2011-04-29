package view.elements.observers;

import java.util.Observable;
import java.util.Observer;

import view.elements.RetrievedElement;
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
		RetrievedElement p = new RetrievedElement(getScene(), 50, book);
		
//		Vector3D position = new Vector3D(100, -100);
//		Random r = new Random();
//		position.rotateZ(r.nextInt(90));
		
		
		getScene().addElement(p);
	}

	private SuggestableScene getScene() {
		return this.scene;
	}
}
