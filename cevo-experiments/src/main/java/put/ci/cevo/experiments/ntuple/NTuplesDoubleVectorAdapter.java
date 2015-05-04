package put.ci.cevo.experiments.ntuple;

import org.apache.commons.lang3.ArrayUtils;

import put.ci.cevo.framework.operators.IndividualAdapter;
import put.ci.cevo.util.vectors.DoubleVector;
import put.ci.cevo.games.encodings.ntuple.NTuple;
import put.ci.cevo.games.encodings.ntuple.NTuples;
import put.ci.cevo.games.encodings.ntuple.NTuples.Builder;

public final class NTuplesDoubleVectorAdapter implements IndividualAdapter<NTuples, DoubleVector> {

	@Override
	public DoubleVector from(NTuples ntuples) {
		return new DoubleVector(ntuples.weights());
	}

	@Override
	public NTuples from(DoubleVector vector, NTuples template) {
		Builder builder = new NTuples.Builder(template.getSymmetryExpander());
		double[] array = vector.toArray();
		int idx = 0;
		for (NTuple original : template.getMain()) {
			// Split the weight array into subarrays of length equal to the length of the weight vector in the original
			// tuple
			double[] subarray = ArrayUtils.subarray(array, idx, idx + original.getWeights().length);
			builder.add(new NTuple(original.getNumValues(), original.getLocations(), subarray));
			idx += original.getNumWeights();
		}
		return builder.build();
	}
}
