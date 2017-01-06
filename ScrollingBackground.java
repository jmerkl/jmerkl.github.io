import javafx.scene.image.Image;

/**
* Governs coordinates of side scrolling background and when to reset for parallax effect.
* @author Jackson M.
* @version 1.0
*/

public class ScrollingBackground extends Obstacle {
	
	Image objImage;
	boolean reset = false;
	double dxdt = 0.0;
	double x = 0.0;
	double xx = 0.0; //Absolute distance traveled
	double y = 0.0;
	double t = 0.0;

	public ScrollingBackground(Image objImage, double dxdt, double x, double y) {
		super(objImage, dxdt, x, y);
		this.objImage = objImage;
		this.dxdt = dxdt;
		this.x = x;
		this.y = y;
	}

	public double scroll(double x, double dt) {
		t = t + dt;
		x = x + dxdt*t;
		xx = xx + dxdt*t;
		if (-x > objImage.getWidth()/4.0) {
			x = 0;
			t = 0;
			reset = true;
		}
		return x;
	}

	public double getAbsDist() {
		return -xx;
	}

	public boolean isReset() {
		return reset;
	}

	public void setReset(boolean resetCommand) {
		reset = resetCommand;
	}
}