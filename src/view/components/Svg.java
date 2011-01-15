package view.components;

import org.mt4j.components.visibleComponents.widgets.MTSvg;
import processing.core.PApplet;

/**
 * Class representing an Svg component.
 */
public class Svg extends MTSvg {
	public Svg(PApplet applet, String fileName) {
        super(applet, fileName);
    }
	
	/**
	 * The reOrthogonalisation of an Svg should be disabled.
	 * This prevents the Svg from disappearing after a while.
	 */
    @Override
    public void reOrthogonalize() {    }
}
