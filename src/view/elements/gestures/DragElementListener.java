package view.elements.gestures;

import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;

import view.elements.AbstractElement;

public class DragElementListener implements IGestureEventListener {
	private final AbstractElement element;
	
	public DragElementListener (AbstractElement element) {
		this.element = element;
	}
	
	@Override
	public boolean processGestureEvent(MTGestureEvent ge) {
		DragEvent de = (DragEvent) ge;
		
		switch (de.getId()) {
			case DragEvent.GESTURE_STARTED:
				element.setDragged(true);
				break;
			case DragEvent.GESTURE_ENDED:
				element.setDragged(false);
				break;
			default:
				break;
		}
		
		return false;
	}
}
