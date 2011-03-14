package application.filters;

import java.util.ArrayList;

import bookshelf.AbstractBook;
import bookshelf.BookFilter;

public class PublishingYearBookFilter implements BookFilter<AbstractBook> {
	private final int periodBegin;
	private final int periodEnd;
	
	public PublishingYearBookFilter(int periodBegin, int periodEnd) {
		if (periodBegin > periodEnd) {
			this.periodBegin = periodEnd;
			this.periodEnd = periodBegin;
		}else {
			this.periodBegin = periodBegin;
			this.periodEnd = periodEnd;
		}
		
	}

	@Override
	public ArrayList<AbstractBook> filter(ArrayList<AbstractBook> books) {
		ArrayList<AbstractBook> result = new ArrayList<AbstractBook>();
		
		for (AbstractBook book : books)
			if (!filter(book))
				result.add(book);
		
		return result;
	}
	
	public boolean filter(AbstractBook book) {
		if (periodBegin > book.getPublishingYear())
			return true;
		
		if (periodEnd < book.getPublishingYear())
			return true;
		
		return false;
	}
}
