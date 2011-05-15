package view.elements.observers;

import java.util.Observable;
import java.util.Observer;

import org.mt4j.util.math.Vector3D;

import rfid.idtronic.evo.desktop.hf.EDHFReply;
import view.elements.RetrievedElement;
import view.elements.actions.CreatedElementPreDrawAction;
import bookshelf.apis.libis.LibisBook;
import controllers.SuggestableScene;

public class RetrievedElementBirthObserver implements Observer {
	private final SuggestableScene scene;
	private final EDHFReply tag;
	
	public RetrievedElementBirthObserver(SuggestableScene scene, EDHFReply tag) {
		this.scene = scene;
		this.tag = tag;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		LibisBook book = (LibisBook) arg;
		RetrievedElement p = new RetrievedElement(getScene(), tag, book, 125);
		getScene().addElement(p);
		Vector3D anchor = new Vector3D(0,scene.getMTApplication().getHeight()/2);
		p.setPositionGlobal(new Vector3D(1,scene.getMTApplication().getHeight()/2));
		scene.registerAssociatedAction(new CreatedElementPreDrawAction(scene.getPanLayer().globalToLocal(anchor),p));
	}

	private SuggestableScene getScene() {
		return this.scene;
	}
}
