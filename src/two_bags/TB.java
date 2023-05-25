package two_bags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

	static int greedy_algorithm_sorted_by_value_computeMV(int[] V, int[] S, int C0, int C1) {
		List<Item> items = new ArrayList<>();
		for (int i = 0; i < V.length; i++) {
			items.add(new Item(V[i], S[i]));
		}

		// Sort items by value in descending order
		Collections.sort(items, Comparator.comparingInt(Item::getValue).reversed());

		return computeMV(items, C0, C1);
	}

	static int greedy_algorithm_sorted_by_density_computeMV(int[] V, int[] S, int C0, int C1) {
		List<Item> items = new ArrayList<>();
		for (int i = 0; i < V.length; i++) {
			items.add(new Item(V[i], S[i]));
		}

		// Sort items by density (value/size) in descending order
		Collections.sort(items, Comparator.comparingDouble(Item::getDensity).reversed());

		return computeMV(items, C0, C1);
	}

	static int computeMV(List<Item> all_items, int C0, int C1) {
		List<Item> remaining_items = new ArrayList<>();

		int totalValue = 0;
		int currentCapacity = C0;

		// Greedy selection of items for C0
		for (Item item : all_items) {
			if (item.size <= currentCapacity) {
				totalValue += item.value;
				currentCapacity -= item.size;
			} else {
				remaining_items.add(item);
			}
		}

		currentCapacity = C1; // Reset currentCapacity for C1

		// Greedy selection of remaining items for C1
		for (Item item : remaining_items) {
			if (item.size <= currentCapacity) {
				totalValue += item.value;
				currentCapacity -= item.size;
			}
		}

		return totalValue;
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

		public double getDensity() {
			return (double) value / size;
		}
	}
}
