package view.elements.actions;

import java.util.ArrayList;

import org.mt4j.sceneManagement.IPreDrawAction;

import view.elements.AbstractElement;

public abstract class ElementPreDrawAction implements IPreDrawAction {
	private final ArrayList<AbstractElement> associatedElements = new ArrayList<AbstractElement>();
	
	protected void addAssociatedElement(AbstractElement element) {
		associatedElements.add(element);
	}
	public ArrayList<AbstractElement> getAssociatedElements() {
		return associatedElements;
	}
}
