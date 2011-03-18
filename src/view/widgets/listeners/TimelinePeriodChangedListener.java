package view.widgets.listeners;

import org.mt4j.sceneManagement.IPreDrawAction;

import view.widgets.custom.TimelineWidget;
import controllers.SuggestableScene;

public class TimelinePeriodChangedListener implements IPreDrawAction {
	private final TimelineWidget widget;
	private final SuggestableScene scene;
	
	public TimelinePeriodChangedListener(SuggestableScene scene, TimelineWidget widget) {
		this.scene = scene;
		this.widget = widget;
	}
	
	@Override
	public void processAction() {
		if (!widget.isChanged())
			return;
		
		// TODO: create filter and set it into the scene
		// TODO: make scene update the visibility of the elements
	}

	@Override
	public boolean isLoop() {
		return true;
	}

}
