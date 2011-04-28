package view.layers;

import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.math.Vector3D;

import controllers.SuggestableScene;

public class PannLayer extends MTEllipse {
//	private final OrbWidget orbWidget;
	
	public PannLayer(final SuggestableScene scene) {
		super(scene.getMTApplication(), new Vector3D(scene.getMTApplication().getWidth()/2,
					scene.getMTApplication().getHeight()/2), 10000000, 10000000);
		this.setNoFill(true);
		
//		float x = scene.getMTApplication().getWidth()/2;
//		float y = scene.getMTApplication().getHeight()/2;
//		
//		orbWidget = new OrbWidget(scene, x, y);
//		orbWidget.addButton(new ButtonTimeline(scene));
//		orbWidget.addButton(new ButtonKeywords(scene));
//		orbWidget.addButton(new ButtonClearTable(scene));
//		orbWidget.addButton(new ButtonTest(scene));
//		addChild(orbWidget);
		
		//this.removeAllGestureEventListeners();
//		this.addGestureListener(DragProcessor.class, new IGestureEventListener() {
//			@Override
//			public boolean processGestureEvent(MTGestureEvent ge) {
//				DragEvent de = (DragEvent) ge;
//				
//				if (de.getId() == DragEvent.GESTURE_UPDATED) {
//					Vector3D translation = de.getTranslationVect().invertLocal();
//					Vector3D position = orbWidget.getCenterPointGlobal();
//					position.translate(translation);
//					
//					orbWidget.setPositionGlobal(position);
//				}
//				
//				return true;
//			}
//		});
		
		//this.removeAllGestureEventListeners();
		this.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				scene.getOrbWidget().processGestureEvent(ge);
				return true;
			}
		});
	}
	
	
}
