package view.components;

import javax.media.opengl.GL;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Ray;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.math.Vertex;

import processing.core.PGraphics;

import controllers.SuggestableScene;

public class MTMessage extends AbstractShape {
	private MTTextArea textArea;
	private MTRoundRectangle container;
	private SuggestableScene scene;
	
	public MTMessage(SuggestableScene scene, String text) {
		super(scene.getMTApplication(), new Vertex[0]);
		this.setComposite(true);
		this.scene = scene;
		setText(text);
	}
	
	public MTMessage(SuggestableScene scene, String text, IFont font) {
		super(scene.getMTApplication(), new Vertex[0]);
		this.setComposite(true);
		this.scene = scene;
		setText(text,font);
	}
	
	public void setText(String text) {
		IFont font = FontManager.getInstance().createFont(scene.getMTApplication(), "fonts/Trebuchet MS.ttf", 
				25, 	//Font size
				new MTColor(255,255,255));	//Font color
		
		setText(text, font);
	}
	
	public void setText(String text, IFont font) {	
		if (textArea != null)
			textArea.destroy();
		
		textArea = new MTTextArea(scene.getMTApplication(), font);
		textArea.setText(text);
		
		float width = textArea.getWidthXY(TransformSpace.GLOBAL);
		float height = textArea.getHeightXY(TransformSpace.GLOBAL);
		
		textArea.setNoStroke(true);
		textArea.setNoFill(true);
		
		if (container != null)
			container.destroy();
		
		container = new MTRoundRectangle(scene.getMTApplication(), 0,0,0, width, height, 5, 5);
		container.setFillColor(new MTColor(0, 0, 0, 255));
		container.setStrokeWeight(2.5f);
		container.setStrokeColor(new MTColor(255, 255, 255, 150));
		container.addChild(textArea);
		setVertices(container.getVerticesLocal());
		textArea.setPositionGlobal(container.getCenterPointGlobal());
		addChild(container);
	}
	
	public String getText() {
		return textArea.getText();
	}

	@Override
	protected void drawPureGl(GL gl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector3D getGeometryIntersectionLocal(Ray ray) {
		return container.getGeometryIntersectionLocal(ray);
	}

	@Override
	public boolean isGeometryContainsPointLocal(Vector3D testPoint) {
		return container.isGeometryContainsPointLocal(testPoint);
	}

	@Override
	public Vector3D getCenterPointLocal() {
		return container.getCenterPointLocal();
	}

	@Override
	protected void destroyComponent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawComponent(PGraphics g) {
		// TODO Auto-generated method stub
		
	}
}
