package view.widgets.listeners;

import org.mt4j.sceneManagement.IPreDrawAction;

import view.elements.SuggestedElement;
import view.widgets.custom.TimelineWidget;

public class ChangedTimelinePeriodListener implements IPreDrawAction {
	private final SuggestedElement element;
	private final TimelineWidget widget;
	
	public ChangedTimelinePeriodListener(SuggestedElement element, TimelineWidget widget) {
		this.element = element;
		this.widget = widget;
	}
	
	@Override
	public void processAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLoop() {
		return true;
	}

}
