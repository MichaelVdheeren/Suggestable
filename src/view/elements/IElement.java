package view.elements;

import bookshelf.AbstractBook;

public interface IElement {
	public boolean isDragged();
	public void setDragged();
	public void resetDragged();
	public abstract AbstractBook getBook();
}
