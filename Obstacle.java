import javafx.scene.image.Image;

/**
* Super class of any collision objects and scrolling background. Holds true coordinates.
* @author Jackson M.
* @version 1.0
*/

public class Obstacle {
	
	Image objImage;
	double dxdt = 0.0;
	double x = 0.0;
	double y = 0.0;

	public Obstacle(Image objImage, double dxdt, double x, double y) {
		this.objImage = objImage;
		this.dxdt = dxdt;
		this.x = x;
		this.y = y;
	}

	public double slide(double x, double dxdt, double dt) {
		return x + dxdt*dt;
	}

	public void slide(double dt) {
		x = x + dxdt*dt;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getDxdt() {
		return dxdt;
	}

	public Image getImage() {
		return objImage;
	}

	public void setTime(double time) {
		double t = time;
	}
	public void setX(double newX) {
		x = newX;
	}

	public void setY(double newY) {
		y = newY;
	}

	public void setImage(Image newImage) {
		objImage = newImage;
	}

	public void setDxdt(double newDxdt) {
		dxdt = newDxdt;
	}
}