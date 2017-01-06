import javafx.scene.image.Image;

/**
* Any collision obstacle in the game
* @author Jackson M.
* @version 1.0
*/

public class Spike extends Obstacle {
	
	Image objImage;
	double dxdt = 0.0;
	double x = 0.0;
	double y = 0.0;
	double t = 0.0;
	int i = -1;

	public Spike(Image objImage, double dxdt, double x, double y) {
		super(objImage, dxdt, x, y);
		this.objImage = objImage;
		this.dxdt = dxdt;
		this.x = x;
		this.y = y;
	}

	public double scroll(double x, double dt) {
		t = t + dt;
		x = x + dxdt*t;
		return x;
	}

	public int counter() {
		return i++;
	}

	public void pass() {
		t = 0;
	}
}