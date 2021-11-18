import java.util.AbstractMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.*;

public class AgglomerativeClustering<T extends Clusterable<T>> implements Clustering<T> {
	double threshold;

	public AgglomerativeClustering(double threshold) {
		this.threshold = threshold;
	}

	public Set<Set<T>> clusterSet(Set<T> elements) {
		while (elements.size() != 1) {

			Optional<SimpleEntry<T, T>> closestPair = elements.stream()
					.flatMap(elem -> elements.stream().filter(other -> !elem.equals(other))
							.map(other -> new AbstractMap.SimpleEntry<T, T>(elem, other)))
					.min(Comparator.comparingDouble(e -> ((T) e.getKey()).distance((T) e.getValue())));

			if (closestPair.getKey().distance(closestPair.getValue()) > threshold) {
				return elements;
			}

		}
	}
}