package MinimumCostPath;

public class MCP {

	public static int dynamic_programming_computeMCP(int[][] G) {
		int[][][] MA = computeMA(G);
		int[][] M = MA[0];
		int L = M.length, C = M[0].length;
		return M[L - 1][C - 1];
	}

	static int[][][] computeMA(int[][] G) {
		int L = G.length, C = G[0].length;
		int[][] M = new int[L][C], A = new int[L][C];
		M[0][0] = G[0][0];
		for (int i = 0; i < L; i++)
			for (int j = 0; j < C; j++)
				if (!(i == 0 && j == 0)) {
					int m_e = minCost(M, i, j - 1, L, C), m_ne = minCost(M, i - 1, j - 1, L, C),
							m_n = minCost(M, i - 1, j, L, C);
					if (m_e == min(m_e, m_ne, m_n))
						A[i][j] = 0;
					else if (m_ne == min(m_ne, m_n))
						A[i][j] = 1;
					else
						A[i][j] = 2;
					M[i][j] = min(m_e, m_ne, m_n) + G[i][j];
				}

		return new int[][][] { M, A };
	}

	public static int greedy_algorithm_computeMCP(int[][] G) {
	    int totalCost = G[0][0];
	    int currentRow = 0;
	    int currentCol = 0;

	    while (currentRow != G.length - 1 || currentCol != G[0].length - 1) {
	        int costEast = Integer.MAX_VALUE;
	        int costNortheast = Integer.MAX_VALUE;
	        int costNorth = Integer.MAX_VALUE;

	        if (currentCol + 1 < G[0].length) {
	            costEast = G[currentRow][currentCol + 1];
	        }
	        if (currentRow + 1 < G.length && currentCol + 1 < G[0].length) {
	            costNortheast = G[currentRow + 1][currentCol + 1];
	        }
	        if (currentRow + 1 < G.length) {
	            costNorth = G[currentRow + 1][currentCol];
	        }

	        if (costEast <= costNortheast && costEast <= costNorth) {
	            currentCol += 1; // Move east
	        } else if (costNortheast <= costEast && costNortheast <= costNorth) {
	            currentRow += 1; // Move northeast
	            currentCol += 1;
	        } else {
	            currentRow += 1; // Move north
	        }

	        totalCost += G[currentRow][currentCol];
	    }

	    return totalCost;
	}

	static int minCost(int[][] M, int i, int j, int L, int C) {
		int infinity = Integer.MAX_VALUE;
		if (inGrid(i, j, L, C))
			return M[i][j];
		else
			return infinity;
	}

	static int min(int x, int y) {
		if (x <= y)
			return x;
		return y;
	}

	static int min(int x, int y, int z) {
		return min(min(x, y), z);
	}

	static boolean inGrid(int i, int j, int L, int C) {
		return 0 <= i && i < L && 0 <= j && j < C;
	}
}
