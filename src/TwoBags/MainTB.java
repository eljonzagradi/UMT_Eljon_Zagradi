package TwoBags;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import TwoBags.TB.Item;

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

		return valDP == 0 ? 0 : ((valDP - valGA) / valDP) * 100;
	}

	public static void main(String[] args) {

		try {
			File file = new File("..\\UMT_Eljon_Zagradi\\src\\TwoBags\\TB.txt");
			PrintStream stream = new PrintStream(file);
			System.setOut(stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ArrayList<Double> results_v = new ArrayList<>();
		ArrayList<Double> results_d = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#.##");
		final int n = 100, maxSize = 60, maxValue = 37, C0 = 30, C1 = 10, MAX_ITERATIONS = 1000;
		int iteration = 0;

		while (iteration < MAX_ITERATIONS) {

			int[] V = randomArray(n, maxValue);
			int[] S = randomArray(n, maxSize);

			System.out.println("Iteration: " + (iteration + 1));
			System.out.println("V = " + Arrays.toString(V));
			System.out.println("S = " + Arrays.toString(S));
			System.out.println("C0 = " + C0);
			System.out.println("C1 = " + C1);

			List<Item> items = new ArrayList<>();

			// Create a list of items
			for (int i = 0; i < n; i++) {
				items.add(new Item(V[i], S[i]));
			}

			long timeDP = 0, timeGA_v = 0, timeGA_d = 0;
			long startDP = System.nanoTime();
			int valDP = TB.dynamic_programming_computeMV(V, S, C0, C1);
			long endDP = System.nanoTime();

			// Sort items based on value ratio in descending order
			items.sort((a, b) -> Double.compare((double) a.value, (double) a.value));
			long startGA_v = System.nanoTime();
			int valGA_v = TB.greedy_algorithm_computeMV(items, C0, C1);
			long endGA_v = System.nanoTime();

			// Sort items based on density ratio in descending order
			items.sort((a, b) -> Double.compare((double) (a.value / a.size ), (double) (a.value / a.size )));
			long startGA_d = System.nanoTime();
			int valGA_d = TB.greedy_algorithm_computeMV(items, C0, C1);
			long endGA_d = System.nanoTime();

			timeDP = endDP - startDP;
			timeGA_v = endGA_v - startGA_v;
			timeGA_d = endGA_d - startGA_d;
			double result_v = Double.parseDouble(df.format(getR(valDP, valGA_v)));
			double result_d = Double.parseDouble(df.format(getR(valDP, valGA_d)));

			System.out.println("Two Bags Max Value DP: " + valDP + " | Time: " + timeDP + " ns");
			System.out.println("Two Bags Max Value GA_v: " + valGA_v + " | Time: " + timeGA_v + " ns");
			System.out.println("Two Bags Max Value GA_d: " + valGA_d + " | Time: " + timeGA_d + " ns");
			System.out.println("R_v : " + result_v + "%");
			System.out.println("R_d : " + result_d + "%");
			results_v.add(result_v);
			results_d.add(result_d);

			iteration++;
			System.out.println("\n");
		}

	}

}
