package put.ci.cevo.util.vectors;

import java.util.List;

public class DoubleVectors {

	public static DoubleVector mean(List<DoubleVector> points) {
		DoubleVector vector = points.get(0);
		for (int i = 1; i < points.size(); i++) {
			vector = vector.add(points.get(i));
		}
		return vector.divide(points.size());
	}
}
