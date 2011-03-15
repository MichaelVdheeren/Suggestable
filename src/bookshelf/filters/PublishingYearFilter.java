package bookshelf.filters;

import java.util.ArrayList;

import bookshelf.AbstractBook;
import bookshelf.BookFilter;

public class PublishingYearFilter implements BookFilter<AbstractBook> {
	private final int periodBegin;
	private final int periodEnd;
	
	public PublishingYearFilter(int periodBegin, int periodEnd) {
		if (periodBegin > periodEnd) {
			this.periodBegin = periodEnd;
			this.periodEnd = periodBegin;
		}else {
			this.periodBegin = periodBegin;
			this.periodEnd = periodEnd;
		}
		
	}

	@Override
	public ArrayList<AbstractBook> applyTo(ArrayList<AbstractBook> books) {
		ArrayList<AbstractBook> result = new ArrayList<AbstractBook>();
		
		for (AbstractBook book : books)
			if (!applyTo(book))
				result.add(book);
		
		return result;
	}
	
	public boolean applyTo(AbstractBook book) {
		if (periodBegin > book.getPublishingYear())
			return true;
		
		if (periodEnd < book.getPublishingYear())
			return true;
		
		return false;
	}
}
