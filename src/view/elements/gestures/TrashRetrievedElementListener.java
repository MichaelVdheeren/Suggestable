package view.elements.gestures;

import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;

import view.elements.RetrievedElement;
import controllers.SuggestableScene;

public class TrashRetrievedElementListener implements IGestureEventListener {
	private final RetrievedElement element;
	private final SuggestableScene scene;
	
	public TrashRetrievedElementListener (SuggestableScene scene, RetrievedElement element) {
		this.scene = scene;
		this.element = element;
	}
	
	@Override
	public boolean processGestureEvent(MTGestureEvent ge) {
		DragEvent de = (DragEvent) ge;
		
		if (de.getId() == DragEvent.GESTURE_ENDED) {
			// TODO: change to SVG when bug in MT4j is fixed
	        if (scene.getOrbWidget().containsPointGlobal(de.getTo())) {
	        	scene.removeElement(element);
	        }
		}
		
		return false;
	}
}
