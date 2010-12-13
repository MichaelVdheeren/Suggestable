package application;
import org.mt4j.MTApplication;

import view.SuggestableScene;


public class Suggestable extends MTApplication {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String args[]){
		initialize();
	}

	@Override
	public void startUp() {
		this.addScene(new SuggestableScene(this, "Suggestable"));
	}
}
