package application.filters;

import java.util.ArrayList;

import bookshelf.BookFilter;
import bookshelf.apis.google.GoogleBook;

public class KeywordBookFilter implements BookFilter<GoogleBook> {
	private final ArrayList<String> keywords;
	
	public KeywordBookFilter(ArrayList<String> keywords) {
		this.keywords = keywords;
	}
	
	@Override
	public ArrayList<GoogleBook> filter(ArrayList<GoogleBook> books) {
		ArrayList<GoogleBook> result = new ArrayList<GoogleBook>();
		
		for (GoogleBook book : books)
			if (!filter(book))
				result.add(book);
		
		return result;
	}
	
	private boolean filter(GoogleBook book) {
		if (book.getWords().containsAll(keywords))
			return false;
		
		return true;
	}
}
