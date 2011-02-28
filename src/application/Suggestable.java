package application;
import org.mt4j.MTApplication;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;

import view.SuggestableScene;

/**
 * The application
 */
public class Suggestable extends MTApplication {
	private static final long serialVersionUID = 1L;
	
	public static void main(String args[]){
		initialize();
	}

	@Override
	public void startUp() {
		// Create the Suggestable scene
		SuggestableScene scene = new SuggestableScene(this);
		
		// Show touches on the scene
		scene.registerGlobalInputProcessor(new CursorTracer(this, scene));
		// Add the scene to the application
		this.addScene(scene);
	}
}
