package view.elements;

import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.util.math.Vertex;

import bookshelf.AbstractBook;
import controllers.SuggestableScene;

public abstract class AbstractElement extends AbstractShape {
	public AbstractElement(SuggestableScene scene) {
		super(scene.getMTApplication(), new Vertex[0]);
		this.setComposite(true);
	}

	private boolean dragged;
	
	public boolean isDragged() {
		return this.dragged;
	}

	public void setDragged(boolean dragged) {
		this.dragged = dragged;
	}

	public abstract AbstractBook getBook();
}
