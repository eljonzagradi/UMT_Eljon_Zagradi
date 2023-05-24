package statistics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StatisticalReport {

	public static void createHistogram(ArrayList<Double> results) {
		// Calculate histogram
		Map<Double, Long> histogram = results.stream()
				.collect(Collectors.groupingBy(i -> i, TreeMap::new, Collectors.counting()));

		// Find maximum frequency
		long maxFrequency = Collections.max(histogram.values());

		// Print histogram
		System.out.println("Histogram:");
		DecimalFormat df = new DecimalFormat("#.##");

		// Calculate the maximum width for value labels
		int maxLabelWidth = histogram.keySet().stream().map(df::format).mapToInt(String::length).max().orElse(0);

		// Print y-axis values and histogram bars
		for (long i = maxFrequency; i > 0; i--) {
			System.out.printf("%" + maxLabelWidth + "s |  ", i);
			for (Map.Entry<Double, Long> entry : histogram.entrySet()) {
				long frequency = entry.getValue();
				if (frequency >= i) {
					System.out.print("*   ");
				} else {
					System.out.print("    ");
				}
			}
			System.out.println();
		}

		// Print x-axis labels
		System.out.print(String.format("%" + (maxLabelWidth + 3) + "s", " "));
		for (double value : histogram.keySet()) {
			System.out.print(String.format("%-" + (maxLabelWidth + 2) + "s", df.format(value)+ "%"));
		}
		System.out.println();

		// Calculate the statistical values
		double mean = calculateMean(results);
		double standardDeviation = calculateStandardDeviation(results, mean);
		double median = calculateMedian(results);
		double maximum = calculateMaximum(results);
		double meanOfOutliers = calculateMeanOfOutliers(results);

		// Print the calculated statistical values
		System.out.println("\nMean: " + df.format(mean));
		System.out.println("Standard Deviation: " + df.format(standardDeviation));
		System.out.println("Median: " + df.format(median));
		System.out.println("Maximum: " + df.format(maximum));
		System.out.println("Mean of Outliers: " + df.format(meanOfOutliers) + "\n");
	}

	// Calculate the mean
	private static double calculateMean(ArrayList<Double> results) {
		double sum = 0;
		for (double value : results) {
			sum += value;
		}
		return sum / results.size();
	}

	// Calculate the standard deviation
	private static double calculateStandardDeviation(ArrayList<Double> results, double mean) {
		double sumSquaredDiff = 0;
		for (double value : results) {
			double diff = value - mean;
			sumSquaredDiff += diff * diff;
		}
		double variance = sumSquaredDiff / results.size();
		return Math.sqrt(variance);
	}

	// Calculate the median
	private static double calculateMedian(ArrayList<Double> results) {
		Collections.sort(results);
		int size = results.size();
		if (size % 2 == 0) {
			int middleIndex = size / 2;
			return (results.get(middleIndex - 1) + results.get(middleIndex)) / 2.0;
		} else {
			return results.get(size / 2);
		}
	}

	// Calculate the maximum
	private static double calculateMaximum(ArrayList<Double> results) {
		return Collections.max(results);
	}

	// Calculate the mean of outliers (5% highest relative distances)
	private static double calculateMeanOfOutliers(ArrayList<Double> results) {
		int outliersCount = (int) (results.size() * 0.05);
		ArrayList<Double> sortedResults = new ArrayList<>(results);
		Collections.sort(sortedResults, Collections.reverseOrder()); // Sort in descending order
		ArrayList<Double> outliers = new ArrayList<>(sortedResults.subList(0, outliersCount));
		return calculateMean(outliers);
	}
}