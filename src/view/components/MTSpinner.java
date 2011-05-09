package view.components;

import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.shapes.MTLine;
import org.mt4j.util.MTColor;
import org.mt4j.util.animation.Animation;
import org.mt4j.util.animation.AnimationEvent;
import org.mt4j.util.animation.IAnimationListener;
import org.mt4j.util.animation.MultiPurposeInterpolator;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.math.Vertex;

import processing.core.PApplet;

public class MTSpinner extends MTEllipse {
	private MTLine sectors[];
	private MTColor opacity[];
	private Animation anim;
	
	public MTSpinner(PApplet pApplet, Vector3D center, float radiusInner,  float radiusOuter, final int sectorCount) {
		super(pApplet, center, radiusOuter, radiusOuter);
		float margin = 0.1f*radiusOuter;
		radiusOuter -= margin;
		this.setStrokeColor(new MTColor(255, 255, 255, 150));
		this.setFillColor(new MTColor(0, 0, 0, 200));
		setComposite(true);
		
		sectors = new MTLine[sectorCount];
		opacity = new MTColor[sectorCount];
		
		double beta = 2 * Math.PI / sectorCount;
		for (int i = 0; i < sectorCount; i++) {
			double alpha = beta * i - Math.PI / 2;
			float cos = (float) Math.cos(alpha);
			float sin = (float) Math.sin(alpha);
			opacity[i] = new MTColor(255,255,255,255/sectorCount*(i+1)) ;
			Vertex start = new Vertex(center.x + radiusInner * cos, center.y + radiusInner * sin);
			Vertex end = new Vertex(center.x + radiusOuter * cos, center.y + radiusOuter * sin);
			sectors[i] = new MTLine(pApplet, start, end);
			sectors[i].setStrokeWeight(0.15f*radiusOuter);
			this.addChild(sectors[i]);
		}
		
		anim = new Animation("spinner", new MultiPurposeInterpolator( sectorCount , 0, 2000, 0, 1f, -1), this);
		anim.addAnimationListener(new IAnimationListener() {
			public void processAnimationEvent(AnimationEvent ae) {
				for (int i = 0; i < sectorCount; i++) {
					int index = (i+Math.round(ae.getValue()))%sectorCount;
					sectors[i].setStrokeColor(opacity[index]);
				}
			}
		});
	}
	
	public void start() {
		anim.start();
	}
	
	public void stop() {
		anim.stop();
	}
	
}
