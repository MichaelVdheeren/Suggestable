package model.books.google;

import java.net.URL;
import java.util.ArrayList;

import com.google.gdata.client.books.BooksService;
import com.google.gdata.client.books.VolumeQuery;
import com.google.gdata.data.books.VolumeEntry;
import com.google.gdata.data.books.VolumeFeed;

public class GoogleCollection {
	public ArrayList<GoogleBook> findBooks(String search) {
		ArrayList<GoogleBook> result = new ArrayList<GoogleBook>();
		BooksService service = new BooksService("");
		VolumeQuery query;
		
		try {
			query = new VolumeQuery(new URL("http://www.google.com/books/feeds/volumes"));
		
			// exclude no-preview books (by default, they are included)
			query.setMinViewability(VolumeQuery.MinViewability.PARTIAL);
	
			// search for
			query.setFullTextQuery(search);

			VolumeFeed volumeFeed = service.query(query, VolumeFeed.class);
			
			for (VolumeEntry entry : volumeFeed.getEntries())
				result.add(new GoogleBook(entry));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
