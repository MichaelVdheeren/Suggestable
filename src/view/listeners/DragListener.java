package view.listeners;

import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;

import view.universe.IElement;

public class DragListener implements IGestureEventListener {
	private final IElement element;
	
	public DragListener (IElement element) {
		this.element = element;
	}
	
	@Override
	public boolean processGestureEvent(MTGestureEvent ge) {
		DragEvent de = (DragEvent) ge;
		
		switch (de.getId()) {
			case DragEvent.GESTURE_STARTED:
				element.setDragged();
				break;
			case DragEvent.GESTURE_ENDED:
				element.resetDragged();
				break;
			default:
				break;
		}
		
		return false;
	}
}
