package two_bags;

import java.util.ArrayList;
import java.util.List;

public class TB {

	static int dynamic_programming_computeMV(int[] V, int[] S, int C0, int C1) {
		int n = V.length;
		int[][][] M = new int[n + 1][C0 + 1][C1 + 1];
		for (int k = 1; k < n + 1; k++)
			for (int c0 = 0; c0 < C0 + 1; c0++)
				for (int c1 = 0; c1 < C1 + 1; c1++) {
					int m0 = -1, m1 = -1;
					if (S[k - 1] <= c0)
						m0 = V[k - 1] + M[k - 1][c0 - S[k - 1]][c1];
					if (S[k - 1] <= c1)
						m1 = V[k - 1] + M[k - 1][c0][c1 - S[k - 1]];
					M[k][c0][c1] = max(M[k - 1][c0][c1], max(m0, m1));
				}

		return M[n][C0][C1];
	}

	static int max(int x, int y) {
		if (x >= y)
			return x;
		return y;
	}

	static int greedy_algorithm_computeMV(List<Item> all_items, int C0, int C1) {

		List<Item> remaining_items = new ArrayList<>();

		int totalValue = 0;
		int currentCapacity = C0;

		// Greedy selection of items
		for (Item item : all_items) {
			if (item.size <= currentCapacity) {
				totalValue += item.value;
				currentCapacity -= item.size;
			} else {
				remaining_items.add(item);
			}
		}

		for (Item item : remaining_items) {
			if (item.size <= currentCapacity) {
				totalValue += item.value;
				currentCapacity -= item.size;
			}
		}

		return totalValue;

	}

	static class Item {
		int value;
		int size;

		Item(int value, int size) {
			this.value = value;
			this.size = size;
		}

	}
}
