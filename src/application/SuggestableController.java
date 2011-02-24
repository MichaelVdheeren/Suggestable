package application;

import java.util.ArrayList;

import bookshelf.AbstractBook;
import bookshelf.apis.google.GoogleBookshelf;
import bookshelf.apis.libis.LibisBarcode;
import bookshelf.apis.libis.LibisBookshelf;
import bookshelf.exceptions.BookNotFoundException;
import bookshelf.exceptions.BookshelfUnavailableException;
import bookshelf.exceptions.InvalidBarcodeException;

public class SuggestableController {
	private final String libisKey = "XG3M5F6NMMYE7MS686G8EC43TRAA9DVRJSIXCF65KTRCVI2RVL-06073";
	private final GoogleBookshelf googleBookshelf = new GoogleBookshelf();
	private final LibisBookshelf libisBookshelf = new LibisBookshelf(libisKey);
	
	public AbstractBook getBook(String strBarcode) throws InvalidBarcodeException, BookshelfUnavailableException, BookNotFoundException {
		if (!LibisBarcode.isValidLength(strBarcode))
			throw new InvalidBarcodeException();
		
		LibisBarcode lbsBarcode = new LibisBarcode(strBarcode);
		return getLibisBookshelf().getBook(lbsBarcode);
	}
	
	public ArrayList<AbstractBook> getRelatedBooks(AbstractBook book) throws BookshelfUnavailableException {
		return getGoogleBookshelf().getRelatedBooks(book);
	}

	private GoogleBookshelf getGoogleBookshelf() {
		return googleBookshelf;
	}

	private LibisBookshelf getLibisBookshelf() {
		return libisBookshelf;
	}
}
