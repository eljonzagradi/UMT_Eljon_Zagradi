package two_bags;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainTB {

	static int[] randomArray(int n, int max) {
		int[] A = new int[n];
		Random r = new Random();
		for (int i = 0; i < n; i++) {
			A[i] = 1 + r.nextInt(max);
		}
		return A;
	}

	static double getR(double valDP, double valGA) {
		DecimalFormat df = new DecimalFormat("#.##");
		return valDP == 0 ? 0 : (Double.parseDouble(df.format((valDP - valGA) / valDP)) * 100);
	}

	public static void main(String[] args) {

		try {
			File file = new File("..\\UMT_Eljon_Zagradi\\src\\two_bags\\TB.txt");
			PrintStream stream = new PrintStream(file);
			System.setOut(stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ArrayList<Double> results_v = new ArrayList<>();
		ArrayList<Double> results_d = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#.##");
		final int n = 50, maxSize = 27, maxValue = 34, C0 = 24, C1 = 31, MAX_ITERATIONS = 2000;
		int iteration = 0;

		while (iteration < MAX_ITERATIONS) {

			int[] V = randomArray(n, maxValue);
			int[] S = randomArray(n, maxSize);

			System.out.println("Iteration: " + (iteration + 1));
			System.out.println("V = " + Arrays.toString(V));
			System.out.println("S = " + Arrays.toString(S));
			System.out.println("C0 = " + C0);
			System.out.println("C1 = " + C1);

			long timeDP = 0, timeGA_v = 0, timeGA_d = 0;
			long startDP = System.nanoTime();
			int valDP = TB.dynamic_programming_computeMV(V, S, C0, C1);
			long endDP = System.nanoTime();

			// Sort items based on value ratio in descending order
			long startGA_v = System.nanoTime();
			int valGA_v = TB.greedy_algorithm_sorted_by_value_computeMV(V, S, C0, C1);
			long endGA_v = System.nanoTime();

			// Sort items based on density ratio in descending order
			long startGA_d = System.nanoTime();
			int valGA_d = TB.greedy_algorithm_sorted_by_density_computeMV(V, S, C0, C1);
			long endGA_d = System.nanoTime();

			timeDP = endDP - startDP;
			timeGA_v = endGA_v - startGA_v;
			timeGA_d = endGA_d - startGA_d;
			double result_v = Double.parseDouble(df.format(getR(valDP, valGA_v)));
			double result_d = Double.parseDouble(df.format(getR(valDP, valGA_d)));

			System.out.println("Max value of the pair of bags DP: " + valDP + " | Time: " + timeDP + " ns");
			System.out.println("Max value of the pair of bags GA_v: " + valGA_v + " | Time: " + timeGA_v + " ns");
			System.out.println("Max value of the pair of bags GA_d: " + valGA_d + " | Time: " + timeGA_d + " ns");
			System.out.println("R_v : " + result_v + "%");
			System.out.println("R_d : " + result_d + "%");
			results_v.add(result_v);
			results_d.add(result_d);

			iteration++;
			System.out.println();
		}
		System.out.println("Greedy Algorithm sorted by values: ");
		statistics.StatisticalReport.createHistogram(results_v);
		System.out.println("Greedy Algorithm sorted by densities: ");
		statistics.StatisticalReport.createHistogram(results_d);

	}

}
