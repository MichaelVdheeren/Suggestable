package controllers;
import org.mt4j.MTApplication;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;


/**
 * The application
 */
public class SuggestableApplication extends MTApplication {
	private static final long serialVersionUID = 1L;
	
	public static void main(String args[]){
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Suggestable");
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