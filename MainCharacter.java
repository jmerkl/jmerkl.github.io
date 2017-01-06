import javafx.scene.image.Image;

/**
* Main character of the game
* @author Jackson M.
* @version 1.0
*/

public class MainCharacter {
	
	Image mcImage;
	boolean jumping;
	boolean doubleJumping;
	boolean alive = true;
	double dxdt = 0.0;
	double dydt = 0.0;
	double x = 0.0;
	double y = 0.0;
	double t = 0.0;

	public MainCharacter(Image mcImage, double dxdt, double dydt, double x, double y) {
		this.mcImage = mcImage;
		this.dxdt = dxdt;
		this.dydt = dydt;
		this.x = x;
		this.y = y;
	}

	public double jump(double yo, double dydt, double g, double dt, double groundLevel) {
		double y = yo + dydt*dt + g*dt*dt;
		if (y > groundLevel) {
			y = groundLevel;
		}
		return y;
	}

	public void jump(double dydt, double g, double dt, double groundLevel) {
		t = t + dt;
		y = y - dydt*t + 0.5*g*t*t;
		if (y > groundLevel) {
			y = groundLevel;
			t = 0;
			jumping = false;
		}
	}

	public void doubleJump(double dydt, double g, double dt, double groundLevel) {
		t = t + dt;
		y = y - 0.2*dydt*t + 0.5*g*t*t;
		if (y > groundLevel) {
			y = groundLevel;
			t = 0;
			doubleJumping = false;
			jumping = false;
		}
	}

	public double slide(double x, double dxdt, double dt) {
		return x + dxdt*dt;
	}

	public void slide(double dt) {
		x = x + dxdt*dt;
	}

	public void revive() {
		alive = true;
	}

	public boolean isAlive() {
		return alive;
	}

	public void death() {
		alive = false;
	}

	public boolean getJump() {
		return jumping;
	}

	public boolean getDoubleJump() {
		return doubleJumping;
	}

	public void setJump(boolean cond) {
		jumping = cond;
		t = 0.0;
	}

	public void setDoubleJump(boolean cond) {
		doubleJumping = cond;
		t = 0.0;
	}

	public boolean isFlying(double groundLevel) {
		return y < groundLevel;
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

	public double getDydt() {
		return dydt;
	}

	public Image getImage() {
		return mcImage;
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
		mcImage = newImage;
	}

	public void setDxdt(double newDxdt) {
		dxdt = newDxdt;
	}

	public void setDydt(double newDydt) {
		dydt = newDydt;
	}
}