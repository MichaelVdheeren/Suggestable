package bookshelf.filters;

import java.util.ArrayList;

import bookshelf.BookFilter;
import bookshelf.apis.google.GoogleBook;

public class KeywordFilter implements BookFilter<GoogleBook> {
	private final ArrayList<String> keywords;
	
	// Keywords you want!
	public KeywordFilter(ArrayList<String> keywords) {
		this.keywords = keywords;
	}
	
	@Override
	public ArrayList<GoogleBook> applyTo(ArrayList<GoogleBook> books) {
		ArrayList<GoogleBook> result = new ArrayList<GoogleBook>();
		
		for (GoogleBook book : books)
			if (!applyTo(book))
				result.add(book);
		
		return result;
	}
	
	public boolean applyTo(GoogleBook book) {
		for (String keyword : book.getKeywords())
			if (keywords.contains(keyword.toLowerCase()))
				return false;
		
		return true;
	}
}
