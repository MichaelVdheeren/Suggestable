package application;

import bookshelf.AbstractBook;
import bookshelf.apis.google.GoogleBookProcessor;
import bookshelf.apis.google.GoogleBookshelf;
import bookshelf.apis.libis.LibisBarcode;
import bookshelf.apis.libis.LibisBookProcessor;
import bookshelf.apis.libis.LibisBookshelf;
import bookshelf.exceptions.BookNotFoundException;
import bookshelf.exceptions.BookshelfUnavailableException;
import bookshelf.exceptions.InvalidBarcodeException;

public class ModelController {
	private final String libisKey = "96UNFINK44R9MEHP3HD5NYBU61QV63JSPDUYXIFTY56TYQ9MUX-00871";
	private final GoogleBookshelf googleBookshelf = new GoogleBookshelf();
	private final LibisBookshelf libisBookshelf = new LibisBookshelf(libisKey);
	
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
