package controllers;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;

import org.mt4j.MTApplication;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;

import rfid.ReplyEvent;
import rfid.ReplyEventListener;
import rfid.idtronic.evo.desktop.hf.EDHFReader;
import rfid.idtronic.evo.desktop.hf.EDHFReply;
import rfid.idtronic.evo.desktop.hf.iso15693.ReadCommand;
import view.scene.SuggestableScene;


/**
 * The application
 */
public class SuggestableApplication extends MTApplication {
	private static final long serialVersionUID = 1L;
	
	public static void main(String args[]){
		
		initialize();
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Suggestable");
	}

	@Override
	public void startUp() {
		// Create the Suggestable scene
		final SuggestableScene scene = new SuggestableScene(this);
		
		// Show touches on the scene
		scene.registerGlobalInputProcessor(new CursorTracer(this, scene));
		// Add the scene to the application
		this.addScene(scene);
		// Create the reader
		try {
			EDHFReader reader = new EDHFReader("/dev/tty.SLAB_USBtoUART");
			reader.addListener(new ReplyEventListener() {
				
				@Override
				public void receivedReply(ReplyEvent event) {
					EDHFReply reply = (EDHFReply) event.getReply();
					// Reading not good when result=aa0002018487bb
					if (reply.getBytes()[2] != 0x6e)
						return;
					
					scene.processTag(reply);
				}
			});
			reader.open();
			reader.execute(new ReadCommand(2, 1, 30, true));
		} catch (NoSuchPortException e) {
			System.out.println("The port could not be found!");
		} catch (PortInUseException e) {
			System.out.println("The port is in use!");
		}
	}
}