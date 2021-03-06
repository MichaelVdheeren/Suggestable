package view.elements;

import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.util.math.Vertex;

import view.scene.SuggestableScene;

import bookshelf.AbstractBook;

public abstract class AbstractElement extends AbstractShape {
	private boolean destroyed = false;
	
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
	
	public void destroy() {
		super.destroy();
		this.destroyed = true;
	}
	
	public boolean isDestroyed() {
		return this.destroyed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getBook() == null) ? 0 : getBook().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractElement other = (AbstractElement) obj;
		if (getBook() == null) {
			if (other.getBook() != null)
				return false;
		} else if (!getBook().equals(other.getBook()))
			return false;
		return true;
	}
}
