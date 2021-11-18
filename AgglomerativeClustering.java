import java.util.Collections;
import java.util.Set;
import java.util.stream.*;

public class AgglomerativeClustering<T extends Clusterable<T>> implements Clustering<T> {
	double threshold;
	private class PairClusterable implements Comparable<PairClusterable>{
		T clusterableA;
		T clusterableB;
		double distance;

		PairClusterable( T clusterableA, T clusterableB){
			this.clusterableA = clusterableA;
			this.clusterableB = clusterableB;
			this.distance = clusterableA.distance(clusterableB);
		}

		@Override
		public int compareTo(PairClusterable other) {
			return Double.compare(this.distance, other.distance);
		}
	}
	public AgglomerativeClustering(double threshold) {
		this.threshold = threshold;
	}

	public Set<Set<T>> clusterSet(Set<T> elements) {
		Set<Set<T>> clusters = elements.stream().map(Set::of).collect(Collectors.toSet());
		while (elements.size() != 1) {

			PairClusterable closestPair = elements.stream()
					.map(elem -> elements.stream()
					.filter(other -> !elem.equals(other))
					.map(other -> new PairClusterable(elem, other))
					.min(PairClusterable::compareTo).get())
					.min(PairClusterable::compareTo).get();

					
					Set<T> cluster1 = Collections.emptySet();
					cluster1.add(closestPair.clusterableA);
					Set<T> cluster2 = Collections.emptySet();
					cluster2.add(closestPair.clusterableB);
								
			if (closestPair.distance > threshold) {
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