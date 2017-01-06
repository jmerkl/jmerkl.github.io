// Imports
	import java.io.File;
	import java.io.BufferedReader;
	import java.io.BufferedWriter;
	import java.io.FileReader;
	import java.io.FileWriter;
	import java.io.PrintWriter;
	import java.io.FileInputStream;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.util.Arrays;
	import java.util.ArrayList;
	import java.util.Collections;
	import java.util.Comparator;

/**
* Object for reading, writing, storing, and sorting game scores
* @author Jackson M.
* @version 1.0
*/

public class Records {

	public static File file;
	public static String filename;
	public static String delim = ",";
	public static BufferedReader br;
	public static BufferedWriter bw;
	public static PrintWriter pw;
	public static ArrayList<Scores> scoreList = new ArrayList<Scores>();

	public Records(String filename) {
		this.filename = filename;

		try {
			br = new BufferedReader(new FileReader(filename));
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}

		// Read in data from the filename
		String line = null;
		try {
			line = br.readLine();
			while (line != null) {
				String[] halves = line.split(delim);
				scoreList.add(new Scores(halves[0], Integer.parseInt(halves[1])));
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		file = new File(filename);
		if (!file.canWrite()) {
			file.setWritable(true);
		}

		// Sort the scores
		sortScores(scoreList);
	}

	public void updateScores() {
		//Sort scores
		sortScores(scoreList);
		//Write scores to file
		try {
			file.setWritable(true);
			pw = new PrintWriter(new FileWriter(filename), true);
			for (int i = 0; i < scoreList.size(); i++) {
				pw.println(scoreList.get(i).getName() + "," + scoreList.get(i).getScore());
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateScores(Scores newScore) {
		//Sort scores
		scoreList.add(newScore);
		sortScores(scoreList);
		//Write scores to file
		try {
			file.setWritable(true);
			pw = new PrintWriter(new FileWriter(filename), true);
			for (int i = 0; i < scoreList.size(); i++) {
				pw.println(scoreList.get(i).getName() + "," + scoreList.get(i).getScore());
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getScoreList() {
		ArrayList<String> combined = new ArrayList<String>();
		for (int i = 0; i < scoreList.size(); i++) {
			combined.add(scoreList.get(i).getScoreName());
		}
		return combined;
	}

	public ArrayList<Scores> sortScores(ArrayList<Scores> sortList) {
		Collections.sort(sortList, new ScoreComparator());
		return sortList;
	}
}

class ScoreComparator implements Comparator<Scores> {
	@Override
	public int compare(Scores s1, Scores s2) {
		int result = s2.getScore() - s1.getScore();
		if (result == 0) {
			result = s1.getName().compareTo(s2.getName());
		}
		return result;
	}
}