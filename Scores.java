import java.util.Arrays;
import java.util.Collections;

/**
* Object for handling game scores
* @author Jackson M.
* @version 1.0
*/

public class Scores {
	String name;
	int score;
	public Scores(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public String getName() {
		return name;
	}

	public void setScore(int newScore) {
		score = newScore;
	}

	public void setName(String newName) {
		name = newName;
	}

	public String getScoreName() {
		return name + ": " + Integer.toString(score);
	}
}