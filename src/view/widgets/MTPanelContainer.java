package view.widgets;

import java.util.ArrayList;

import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

public class MTPanelContainer extends MTRectangle {
	private final ArrayList<MTPanel> panels = new ArrayList<MTPanel>();
	
	public MTPanelContainer(PApplet pApplet, float width, float height) {
		super(pApplet,width,height);
		this.setNoFill(true);
		this.setNoStroke(true);
	}
	
	public void addPanel(final MTPanel panel) {
		this.addChild(panel);
		this.addChild(panel.getButton());
		panels.add(panel);
		
		if (panels.size() > 1)
			panel.setVisible(false);
		
		panel.getButton().addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent) ge;
				if (te.getTapID() == TapEvent.TAPPED) {
					switchToPanel(panel);
				}
				return true;
			}
		});
		
		panel.addGestureListener(DragProcessor.class, new IGestureEventListener() {
			Vector3D start = new Vector3D(0, 0);
			
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				DragEvent de = (DragEvent) ge;
				
				if (de.getId() == DragEvent.GESTURE_UPDATED) {
					start = de.getFrom();
				} else if (de.getId() == DragEvent.GESTURE_ENDED) {
					float diff = start.subtractLocal(de.getTo()).getX();
					int next = panels.indexOf(panel);
					
					if (diff > 0)
						next++;
					else
						next--;
					
					if ((next >= 0) && (next < panels.size()))
						switchToPanel(panels.get(next));
				}
				return true;
			}
		});
		
		panel.setPositionRelativeToParent(this.getCenterPointLocal().addLocal(new Vector3D(0,-20)));
		realignIcons();
	}
	
	public void removePanel(MTPanel panel) {
		panels.remove(panel);
		this.removeChild(panel);
		this.removeChild(panel.getButton());
		realignIcons();
	}
	
	private void realignIcons() {
		int amount = panels.size();
		float space = 40;
		
		for (int i=0; i<panels.size(); i++) {
			panels.get(i).getButton().setPositionRelativeToParent(this.getCenterPointLocal().addLocal(new Vector3D(-((amount-1)*space)/2+i*space,getHeightXYGlobal()/2-25)));
		}
	}
	
	private void switchToPanel(MTPanel panel) {
		for (MTPanel aPanel : panels)
			if (aPanel == panel) {
				aPanel.setVisible(true);
			} else {
				aPanel.setVisible(false);
			}
	}
}
