import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;

import javax.swing.text.html.parser.ParserDelegator;

public class TwoDPoint implements Clusterable<TwoDPoint> {
	double x;
	double y;

	public TwoDPoint(String str) {
		String[] points = str.split(",", 0);
		this.x = Double.parseDouble(points[0]);
		this.y = Double.parseDouble(points[1]);
	}

	public TwoDPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public double distance(TwoDPoint other) {
		return Math.sqrt(((other.x - this.x) * (other.x - this.x)) + ((other.y - this.y) * (other.y - this.y)));
	}

	public static Set<TwoDPoint> readClusterableSet(String path) throws IOException {
		File file = new File(path);
		Scanner scanner = new Scanner(file);
		Set clusters = Collections.emptySet();

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			clusters.add(new TwoDPoint(line));
		}

		scanner.close();
		return clusters;
	}

	@Override
	public String toString() {
		return x + "," + y;
	}

	@Override
	public boolean equals(Object other) {
		TwoDPoint otherP = (TwoDPoint) other;
		return (otherP.x == x && otherP.y == y);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
