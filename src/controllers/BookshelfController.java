package controllers;

import java.util.ArrayList;

import bookshelf.AbstractBook;
import bookshelf.ISBN;
import bookshelf.apis.google.GoogleBookProcessor;
import bookshelf.apis.google.GoogleBookshelf;
import bookshelf.apis.libis.LibisBarcode;
import bookshelf.apis.libis.LibisBookProcessor;
import bookshelf.apis.libis.LibisBookshelf;
import bookshelf.apis.libis.LibisLocation;
import bookshelf.apis.libis.parameters.LibisLibrary;
import bookshelf.exceptions.BookshelfUnavailableException;

public class BookshelfController {
	private final GoogleBookshelf googleBookshelf = new GoogleBookshelf();
	private final LibisBookshelf libisBookshelf = new LibisBookshelf();
	
	public LibisBookProcessor getBookByBarcode(LibisBarcode barcode) 
			throws BookshelfUnavailableException {
		return getLibisBookshelf().getBook(barcode);
	}
	
	public LibisBookProcessor getBookByISBN(String isbn) 
			throws BookshelfUnavailableException {
		return getLibisBookshelf().getBook(new ISBN(isbn));
	}
	
	public GoogleBookProcessor getRelatedBooks(AbstractBook book) throws BookshelfUnavailableException {
		return getGoogleBookshelf().getRelatedBooks(book);
	}
	
	public ArrayList<LibisLocation> getBookLocations(AbstractBook book, LibisLibrary library) throws BookshelfUnavailableException {
		LibisBookProcessor processor = getLibisBookshelf().getBook(book.getISBN());
		processor.setLimit(1);
		processor.run();
		
		if (processor.getBooks().size() == 0)
			return new ArrayList<LibisLocation>();
		else
			return processor.getLastBook().getLocations(library);
	}

	private GoogleBookshelf getGoogleBookshelf() {
		return googleBookshelf;
	}

	private LibisBookshelf getLibisBookshelf() {
		return libisBookshelf;
	}
}
