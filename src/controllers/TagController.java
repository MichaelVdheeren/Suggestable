package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import rfid.idtronic.evo.desktop.hf.EDHFReply;
import bookshelf.apis.libis.LibisBarcode;

public class TagController {
	private HashMap<EDHFReply, LibisBarcode> tags;
	
	@SuppressWarnings("unchecked")
	public TagController() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("tags.bin"));
			this.tags = (HashMap<EDHFReply, LibisBarcode>) in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			this.tags = new HashMap<EDHFReply, LibisBarcode>();
			return;
		} catch (IOException e) {
			return;
		} catch (ClassNotFoundException e) {
			this.tags = new HashMap<EDHFReply, LibisBarcode>();
			return;
		}
	}
	
	public boolean containsTag(EDHFReply tag) {
		return this.tags.containsKey(tag);
	}
	
	public void removeTag(EDHFReply tag) {
		this.tags.remove(tag);
		
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("tags.bin"));
			out.writeObject(tags);
			out.close();
		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			return;
		}
	}
	
	public void addTag(EDHFReply tag, LibisBarcode barcode) {
		this.tags.put(tag, barcode);
		
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("tags.bin"));
			out.writeObject(tags);
			out.close();
		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			return;
		}
	}
	
	public LibisBarcode getBarcode(EDHFReply tag) {
		return this.tags.get(tag);
	}
}
