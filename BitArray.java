import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class BitArray implements Clusterable<BitArray> {
	private ArrayList<Boolean> bits;

	public BitArray(String str) {
		String[] vals = str.split(",", 0);
		for (int i = 0; i < vals.length; ++i) {
			bits.add(Boolean.parseBoolean(vals[i]));
		}

	}

	public BitArray(boolean[] newBits) {
		for (int i = 0; i < newBits.length; ++i) {
			bits.add(newBits[i]);
		}
	}

	@Override
	public double distance(BitArray other) {
		double count = 0;
		for (int i = 0; i < this.bits.size() || i < other.bits.size(); ++i) {
			if (this.bits.get(i) != other.bits.get(i)) {
				count++;
			}
		}
		return count;
	}

	public static Set<BitArray> readClusterableSet(String path) throws IOException {
		File file = new File(path);
		Scanner scanner = new Scanner(file);
		Set clusters = Collections.emptySet();
		int max = 0;

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] numBits = line.split(",", 0);

			if (numBits.length > max) {
				max = numBits.length;
				clusters = Collections.emptySet();
			}

			if (numBits.length == max) {
				clusters.add(new BitArray(line));
			}
		}

		scanner.close();
		return clusters;
	}

	@Override
	public String toString() {
		return bits.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		BitArray bitArray = (BitArray) o;
		return bits.equals(bitArray.bits);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bits);
	}
}
