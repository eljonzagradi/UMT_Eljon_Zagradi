package maximum_value_bag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MVB {

	public static int dynamic_programming_computeM(int[] V, int[] S, int C) {
		int n = V.length;
		int[][] M = new int[n + 1][C + 1];

		// base cases
		for (int c = 0; c < C + 1; c++)
			M[0][c] = 0;
		// general cases
		for (int k = 1; k < n + 1; k++)
			for (int c = 0; c < C + 1; c++)
				if (c - S[k - 1] < 0)
					M[k][c] = M[k - 1][c];
				else
					M[k][c] = max(M[k - 1][c], V[k - 1] + M[k - 1][c - S[k - 1]]);

		return M[V.length][C];
	}

	static int max(int x, int y) {
		if (x >= y)
			return x;
		return y;
	}

	static class Item {
		private int value;
		private int size;

		public Item(int value, int size) {
			this.value = value;
			this.size = size;
		}

		public int getValue() {
			return value;
		}

		public int getSize() {
			return size;
		}
	}

	public static int greedy_algorithm_computeM(int[] V, int[] S, int C) {
		List<Item> items = new ArrayList<>();
		int n = V.length;

		// Create a list of items
		for (int i = 0; i < n; i++) {
			items.add(new Item(V[i], S[i]));
		}

		// Sort items based on value ratio in descending order
		Collections.sort(items, Comparator.comparingInt(Item::getValue).reversed());

		int totalValue = 0;
		int currentCapacity = C;

		// Greedy selection of items
		for (Item item : items) {
			if (item.size <= currentCapacity) {
				totalValue += item.value;
				currentCapacity -= item.size;
			}
		}

		return totalValue;
	}

}
