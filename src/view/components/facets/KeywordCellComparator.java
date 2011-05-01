package view.components.facets;

import java.util.Comparator;


public class KeywordCellComparator implements Comparator<KeywordCell> {

	@Override
	public int compare(KeywordCell arg0, KeywordCell arg1) {
		return arg0.getKeyword().compareTo(arg1.getKeyword());
	}

}
