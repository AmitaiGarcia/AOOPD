import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BitArray implements Clusterable<BitArray> {
	private ArrayList<Boolean> bits;

	public BitArray(String str) {
		List<Boolean> tempList = Arrays.stream(str.split(","))
		.map(Boolean::parseBoolean)
		.collect(Collectors.toList());
		
		bits = new ArrayList<Boolean>(tempList);

	}

	public BitArray(Boolean[] bits) {
		List<Boolean> tempList = Arrays.asList(bits);
		this.bits = new ArrayList<Boolean>(tempList);
	}

	@Override
	public double distance(BitArray other) {
		return IntStream.range(0, bits.size()) 
				.filter(i -> !bits.get(i).equals(other.bits.get(i))) 
				.count();
	}

	public int size() {
		return bits.size();
	}
	
	public static Set<BitArray> readBitArrays(String path) throws IOException {
		try (Stream<String> lines = Files.lines(Paths.get(path))) {
		
			List<BitArray> arrays =
					new ArrayList<>(lines.map(BitArray::new).collect(Collectors.toList()));
			
			Optional<BitArray> longest = arrays.stream().max(Comparator.comparing(BitArray::size));
			if (longest.isPresent()) {
				
				return arrays.stream().filter(a -> a.size() == longest.get().size())
						.collect(Collectors.toSet()); 
			}
			return new HashSet<>();
		}
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
