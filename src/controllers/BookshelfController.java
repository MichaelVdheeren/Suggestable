package controllers;

import bookshelf.AbstractBook;
import bookshelf.apis.google.GoogleBookProcessor;
import bookshelf.apis.google.GoogleBookshelf;
import bookshelf.apis.libis.LibisBarcode;
import bookshelf.apis.libis.LibisBookProcessor;
import bookshelf.apis.libis.LibisBookshelf;
import bookshelf.exceptions.BookNotFoundException;
import bookshelf.exceptions.BookshelfUnavailableException;
import bookshelf.exceptions.InvalidBarcodeException;

public class BookshelfController {
	private final GoogleBookshelf googleBookshelf = new GoogleBookshelf();
	private final LibisBookshelf libisBookshelf = new LibisBookshelf();
	
	public LibisBookProcessor getBook(String strBarcode) 
			throws InvalidBarcodeException, BookshelfUnavailableException, BookNotFoundException {
		if (!LibisBarcode.isValidLength(strBarcode))
			throw new InvalidBarcodeException();
		
		LibisBarcode lbsBarcode = new LibisBarcode(strBarcode);
		return getLibisBookshelf().getBook(lbsBarcode);
	}
	
	public GoogleBookProcessor getRelatedBooks(AbstractBook book) throws BookshelfUnavailableException {
		return getGoogleBookshelf().getRelatedBooks(book);
	}

	private GoogleBookshelf getGoogleBookshelf() {
		return googleBookshelf;
	}

	private LibisBookshelf getLibisBookshelf() {
		return libisBookshelf;
	}
}
