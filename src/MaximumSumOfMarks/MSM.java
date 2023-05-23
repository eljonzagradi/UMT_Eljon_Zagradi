package MaximumSumOfMarks;

public class MSM {

	static int[][][] computeMA(int[][] E) {
		int n = E.length, H = E[0].length - 1;
		int[][] M = new int[n + 1][H + 1], A = new int[n + 1][H + 1];

		for (int h = 0; h < H + 1; h++)
			M[0][h] = 0;

		for (int k = 1; k < n + 1; k++)
			for (int h = 0; h < H + 1; h++) {
				int mkh = Integer.MIN_VALUE, akh = -1;
				for (int hprime = 0; hprime < h + 1; hprime++) {
					int mkh_hprime = E[k - 1][hprime] + M[k - 1][h - hprime];
					if (mkh_hprime > mkh) {
						mkh = mkh_hprime;
						akh = hprime;
					}
				}
				M[k][h] = mkh;
				A[k][h] = akh;
			}
		return new int[][][] { M, A };
	}

	public static int dynamic_programming_computeMSM(int[][] E) {
		int n = E.length, H = E[0].length - 1;
		int[][][] MA = computeMA(E);
		int[][] M = MA[0];
		return M[n][H];
	}

	public static int greedy_algorithm_computeMSM(int[][] matrix) {
		int rows = matrix.length;
		int cols = matrix[0].length - 1;
		int[][] M = new int[rows + 1][cols + 1];

		// Ground cases
		for (int col = 0; col <= cols; col++) {
			M[0][col] = 0;
		}

		// Recurrence cases
		for (int row = 1; row <= rows; row++) {
			for (int col = 0; col <= cols; col++) {
				int maxElement = -1;
				int maxColPrime = 0;
				for (int colPrime = 0; colPrime <= col; colPrime++) {
					if (matrix[row - 1][colPrime] > maxElement) {
						maxElement = matrix[row - 1][colPrime];
						maxColPrime = colPrime;
					}
				}
				M[row][col] = maxElement + M[row - 1][col - maxColPrime];
			}
		}

		int maxSum = Integer.MIN_VALUE;
		for (int col = 0; col <= cols; col++) {
			if (M[rows][col] > maxSum) {
				maxSum = M[rows][col];
			}
		}

		return maxSum;
	}

}
