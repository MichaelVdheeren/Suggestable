package view.widgets.listeners;

import org.mt4j.sceneManagement.IPreDrawAction;

import view.elements.IElement;
import view.widgets.facets.IFacetWidget;

public class FacetWidgetListener implements IPreDrawAction {
	private final IFacetWidget widget;
	private final IElement element;
	
	public FacetWidgetListener(IFacetWidget widget, IElement element) {
		this.widget = widget;
		this.element = element;
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
