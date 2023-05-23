package MaximumValueBag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainMVB {

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
			File file = new File("..\\UMT_Eljon_Zagradi\\src\\MaximumValueBag\\MVB.txt");
			PrintStream stream = new PrintStream(file);
			System.setOut(stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ArrayList<Double> results = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#.##");
		Random ran = new Random();
		final int SIZE = 50, MAX_ITERATIONS = 1000;
		int iteration = 0;

		while (iteration < MAX_ITERATIONS) {

			int[] V = randomArray(SIZE, 50);
			int[] S = randomArray(SIZE, 31);
			int C = 1 + ran.nextInt(40);
			System.out.println("Iteration: " + (iteration + 1));
			System.out.println("V = " + Arrays.toString(V));
			System.out.println("S = " + Arrays.toString(S));
			System.out.println("C = " + C);

			long timeDP = 0, timeGA = 0;
			long startDP = System.nanoTime();
			int valDP = MVB.dynamic_programming_computeM(V, S, C);
			long endDP = System.nanoTime();

			long startGA = System.nanoTime();
			int valGA = MVB.greedy_algorithm_computeM(V, S, C);
			long endGA = System.nanoTime();

			timeDP = endDP - startDP;
			timeGA = endGA - startGA;
			double result = Double.parseDouble(df.format(getR(valDP, valGA)));

			System.out.println("Max Value DP: " + valDP + " | Time: " + timeDP + " ns");
			System.out.println("Max Value GA: " + valGA + " | Time: " + timeGA + " ns");
			System.out.println("R : " + result + "%");
			results.add(result);

			iteration++;
			System.out.println("\n");
		}

	}

}
