package view.elements.observers;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import org.mt4j.util.math.Vector3D;

import controllers.SuggestableScene;

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
		SuggestedElement s = new SuggestedElement(getScene(), 125, book);
		
		Vector3D position = new Vector3D(50, 50);
		Random r = new Random();
		position.rotateZ(r.nextInt(360));
		
		s.setPositionGlobal(p.getCenterPointGlobal().addLocal(position));
		getScene().addElement(s, p);
	}

	private SuggestableScene getScene() {
		return this.scene;
	}
}
