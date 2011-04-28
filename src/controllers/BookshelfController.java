package controllers;

import bookshelf.AbstractBook;
import bookshelf.ISBN;
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
	
	public LibisBookProcessor getBookByBarcode(String barcode) 
			throws InvalidBarcodeException, BookshelfUnavailableException, BookNotFoundException {
		if (!LibisBarcode.isValidLength(barcode))
			throw new InvalidBarcodeException();
		
		LibisBarcode lbsBarcode = new LibisBarcode(barcode);
		return getLibisBookshelf().getBook(lbsBarcode);
	}
	
	public LibisBookProcessor getBookByISBN(String isbn) 
			throws BookshelfUnavailableException, BookNotFoundException {
		return getLibisBookshelf().getBook(new ISBN(isbn));
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
