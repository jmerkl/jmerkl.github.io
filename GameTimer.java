/**
* Artificial discrete game timer to replace auto AnimationTimer. Used in song shuffling.
* @author Jackson M.
* @version 1.0
*/

public class GameTimer {

	int songInt = 0;
	double time = 0.0;
	double dt = 0.004;
	boolean reset = true;

	public GameTimer() {}

	public boolean isReset() {
		return reset;
	}

	public double incrementTime() {
		time = time + dt;
		reset = false;
		return time;
	}

	public double getTime() {
		return time;
	}

	public double getIncrement() {
		return dt;
	}

	public void reset() {
		time = 0.0;
		reset = true;
	}

	public int getSongInt() {
		return songInt;
	}

	public void setSongInt(int newInt) {
		songInt = newInt;
	}
}