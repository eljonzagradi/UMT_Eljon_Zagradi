package maximum_sum_of_marks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainMSM {

	static int[][] randomArray(int n, int H) {
		int[][] A = new int[n][H + 1];
		Random r = new Random();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < H + 1; j++) {
				A[i][j] = r.nextInt(H + 1);
			}
		}
		return A;
	}

	static void displayE(int[][] E) {
		int L = E.length;
		for (int i = L - 1; i > -1; i--)
			System.out.printf("\ti:%d\t %s\n", i, Arrays.toString(E[i]));
	}

	static double getR(double valDP, double valGA) {
		DecimalFormat df = new DecimalFormat("#.##");
		return valDP == 0 ? 0 : (Double.parseDouble(df.format((valDP - valGA) / valDP)) * 100);
	}

	public static void main(String[] args) {

		try {
			File file = new File("..\\UMT_Eljon_Zagradi\\src\\maximum_sum_of_marks\\MSM.txt");
			PrintStream stream = new PrintStream(file);
			System.setOut(stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ArrayList<Double> results = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#.##");
		final int n = 10, H = 20, MAX_ITERATIONS = 2000;
		int iteration = 0;

		while (iteration < MAX_ITERATIONS) {

			int[][] E = randomArray(n, H);
			System.out.println("Iteration: " + (iteration + 1));
			System.out.println("E = ");
			displayE(E);

			long timeDP = 0, timeGA = 0;
			long startDP = System.nanoTime();
			int valDP = MSM.dynamic_programming_computeMSM(E);
			long endDP = System.nanoTime();

			long startGA = System.nanoTime();
			int valGA = MSM.greedy_algorithm_computeMSM(E);
			long endGA = System.nanoTime();

			timeDP = endDP - startDP;
			timeGA = endGA - startGA;
			double result = Double.parseDouble(df.format(getR(valDP, valGA)));

			System.out.println("Max Sum DP: " + valDP + " | Time: " + timeDP + " ns");
			System.out.println("Max Sum GA: " + valGA + " | Time: " + timeGA + " ns");
			System.out.println("R : " + result + "%");
			results.add(result);

			iteration++;

			System.out.println();
		}

		statistics.StatisticalReport.createHistogram(results);

	}

}
