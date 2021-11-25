import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.*;

public class AgglomerativeClustering<T extends Clusterable<T>> implements Clustering<T> {
	double threshold;

	private class PairClusterable implements Comparable<PairClusterable> {
		Set<T> clusterableA;
		Set<T> clusterableB;
		double distance;

		PairClusterable(Set<T> clusterableA, Set<T> clusterableB) {
			this.clusterableA = clusterableA;
			this.clusterableB = clusterableB;
			this.distance = setDistance(clusterableA, clusterableB); // find distance between 2 sets
		}

		@Override
		public int compareTo(PairClusterable other) {
			return Double.compare(this.distance, other.distance);
		}
	}

	public AgglomerativeClustering(double threshold) {
		this.threshold = threshold;
	}

	public double setDistance(Set<T> set1, Set<T> set2) {

		return set1.stream().flatMap(x -> set2.stream().map(x::distance)).min(Double::compare).get();

	}

	public Set<Set<T>> clusterSet(Set<T> elements) {
		Set<Set<T>> clusters = elements.stream().map(elem -> Collections.singleton(elem)).collect(Collectors.toSet());

		while (clusters.size() != 1) {

			PairClusterable ClosestSets = clusters.stream()
					.map(set1 -> clusters.stream().filter(set2 -> !set1.equals(set2))
							.map(set2 -> new PairClusterable(set1, set2)).min(PairClusterable::compareTo).get())
					.min(PairClusterable::compareTo).get();

			Set<T> cluster1 = new HashSet<>();
			cluster1.addAll(ClosestSets.clusterableA);
			Set<T> cluster2 = new HashSet<>();
			cluster2.addAll(ClosestSets.clusterableB);

			if (ClosestSets.distance > threshold) {
				return clusters;
			}

			clusters.remove(cluster1);
			clusters.remove(cluster2);
			cluster1.addAll(cluster2);
			clusters.add(cluster1);

		}
		return clusters;
	}
}