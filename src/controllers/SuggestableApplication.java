package controllers;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.mt4j.MTApplication;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;

import view.elements.observers.RetrievedElementBirthObserver;
import bookshelf.apis.libis.LibisBookProcessor;
import bookshelf.exceptions.BookNotFoundException;
import bookshelf.exceptions.BookshelfUnavailableException;
import bookshelf.exceptions.InvalidBarcodeException;


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
		final SuggestableScene scene = new SuggestableScene(this);
		
		// Show touches on the scene
		scene.registerGlobalInputProcessor(new CursorTracer(this, scene));
		// Add the scene to the application
		this.addScene(scene);
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					LibisBookProcessor lp = null;
					switch (e.getKeyCode()) {
											// An introduction to OO programming with Java
						case KeyEvent.VK_1: lp = scene.getController().getBookByBarcode("009906485");
											break;
											// Understanding OO programming with Java
						case KeyEvent.VK_2: lp = scene.getController().getBookByISBN("0-201-61273-9");
											break;
											// Learning Java
						case KeyEvent.VK_3: lp = scene.getController().getBookByISBN("978-0-596-00873-4");
											break;
											// Object-oriented programming with Java : an introduction. 
						case KeyEvent.VK_4: lp = scene.getController().getBookByISBN("0-13-086900-7");
											break;
											// Data structures and abstractions with Java.
						case KeyEvent.VK_5: lp = scene.getController().getBookByISBN("0-13-204367-X");
											break;
					}
					lp.addObserver(new RetrievedElementBirthObserver(scene));
					lp.setLimit(1);
					Thread thread = new Thread(lp,"Book Processor");
					thread.start();
				} catch (InvalidBarcodeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BookshelfUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BookNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}
}