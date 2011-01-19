package model.books;

import java.awt.Image;
import java.io.IOException;

public interface Book {

	public String getTitle();
	public Image getThumbnail() throws IOException;
	public String getSummary();
	public String[] getKeywords();
	public String[] getAuthors();
	public abstract Integer getRating(); 
}
