package minimum_cost_path;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainMCP {

	static int[][] randomArray(int size, int max) {
		int[][] A = new int[size][size];
		Random r = new Random();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				A[i][j] = r.nextInt(max);
			}
		}
		return A;
	}

	static void displayG(int[][] G) {
		int L = G.length;
		for (int l = L - 1; l > -1; l--)
			System.out.println(Arrays.toString(G[l]));
	}

	static double getR(double valDP, double valGA) {
		DecimalFormat df = new DecimalFormat("#.##");
		return valGA == 0 ? 0 : (Double.parseDouble(df.format((valGA - valDP) / valGA)) * 100);
	}

	public static void main(String[] args) {

		try {
			File file = new File("..\\UMT_Eljon_Zagradi\\src\\minimum_cost_path\\MCP.txt");
			PrintStream stream = new PrintStream(file);
			System.setOut(stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ArrayList<Double> results = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#.##");
		final int SIZE = 10, MAX_ITERATIONS = 2000;
		int iteration = 0;

		while (iteration < MAX_ITERATIONS) {

			int[][] G = randomArray(SIZE, 31);
			System.out.println("Iteration: " + (iteration + 1));
			System.out.println("G = ");
			displayG(G);

			long timeDP = 0, timeGA = 0;
			long startDP = System.nanoTime();
			int valDP = MCP.dynamic_programming_computeMCP(G);
			long endDP = System.nanoTime();

			long startGA = System.nanoTime();
			int valGA = MCP.greedy_algorithm_computeMCP(G);
			long endGA = System.nanoTime();

			timeDP = endDP - startDP;
			timeGA = endGA - startGA;
			double result = Double.parseDouble(df.format(getR(valDP, valGA)));

			System.out.println("Min Cost DP: " + valDP + " | Time: " + timeDP + " ns");
			System.out.println("Min Cost GA: " + valGA + " | Time: " + timeGA + " ns");
			System.out.println("R : " + result + "%");
			results.add(result);

			iteration++;
			System.out.println();
		}

		statistics.StatisticalReport.createHistogram(results);

	}

}
