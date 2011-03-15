package view.widgets.listeners;

import java.util.ArrayList;

import org.mt4j.sceneManagement.IPreDrawAction;

import view.elements.SuggestedElement;
import view.widgets.custom.KeywordWidget;
import view.widgets.custom.TimelineWidget;

public class ChangedWidgetsListener implements IPreDrawAction {
	private final ArrayList<SuggestedElement> elements;
	private final TimelineWidget tLwidget;
	private final KeywordWidget kwWidget;
	
	public ChangedWidgetsListener(SuggestedElement element, TimelineWidget widget) {
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
