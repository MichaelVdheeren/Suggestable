package model.books.google;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import model.books.Book;

import com.google.gdata.data.Person;
import com.google.gdata.data.books.VolumeEntry;
import com.google.gdata.data.dublincore.Subject;

public class GoogleBook implements Book {
	private final VolumeEntry entry;
	
	public GoogleBook(VolumeEntry entry) {
		this.entry = entry;
	}
	
	@Override
	public String getTitle() {
		return getEntry().getTitle().getPlainText();
	}
	
	@Override
	public Image getThumbnail() throws MalformedURLException {
		Image result = null;
		
	    URL url = new URL(getEntry().getThumbnailLink().getHref());
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		result = toolkit.getImage(url);
		
	    return result;
	}
	
	@Override
	public String getSummary() {
		return getEntry().getSummary().getPlainText();
	}
	
	@Override
	public Integer getRating() {
		return getEntry().getRating().getValue();
	}
	
	private VolumeEntry getEntry() {
		return this.entry;
	}

	@Override
	public String[] getKeywords() {
		List<Subject> subjects = getEntry().getSubjects();
		String[] result = new String[subjects.size()];
		int i=0;
		
		for (Subject s : subjects) {
			result[i] = s.getValue();
			i++;
		}
		
		return result;
	}

	@Override
	public String[] getAuthors() {
		List<Person> persons = getEntry().getAuthors();
		String[] result = new String[persons.size()];
		int i=0;
		
		for (Person p : persons) {
			result[i] = p.getName();
			i++;
		}
		
		return result;
	}
}
