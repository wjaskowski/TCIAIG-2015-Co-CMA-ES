package put.ci.cevo.games.encodings.ntuple;

import static put.ci.cevo.util.RandomUtils.nextInt;

import com.carrotsearch.hppc.IntOpenHashSet;
import org.apache.commons.math3.random.RandomDataGenerator;
import put.ci.cevo.games.board.Board;
import put.ci.cevo.games.board.BoardUtils;
import put.ci.cevo.games.board.RectSize;
import put.ci.cevo.util.RandomFactory;

public class NTupleImprovedRandomFactory implements RandomFactory<NTuple> {

	private final int numValues;
	private final int tupleSize;

	private final double minWeight;
	private final double maxWeight;

	private final RectSize boardSize;

	public NTupleImprovedRandomFactory(int numValues, int tupleSize, RectSize boardSize, double minWeight,
			double maxWeight) {
		this.numValues = numValues;
		this.tupleSize = tupleSize;
		this.minWeight = minWeight;
		this.maxWeight = maxWeight;
		this.boardSize = boardSize;
	}

	@Override
	public NTuple create(RandomDataGenerator random) {
		IntOpenHashSet positionSet = new IntOpenHashSet();
		int row = nextInt(0, boardSize.height() - 1, random);
		int col = nextInt(0, boardSize.width() - 1, random);
		positionSet.add(BoardUtils.toMarginPos(boardSize.width(), row, col));
		for (int j = 1; j < tupleSize; j++) {
			int repeats = 0;
			while (positionSet.size() <= j && repeats
					< 25) { // This way all n-tuples have the same size (unless maxRepeats, just in very rare cases)
				int dir = random.nextInt(0, Board.DIRS.length - 1);
				row += Board.DIRS[dir][0];
				col += Board.DIRS[dir][1];

				row = (row + boardSize.height()) % boardSize.height(); // TODO: This makes no sense, because it harms locality.
				col = (col + boardSize.width()) % boardSize.width(); // It is better to bounce the seed

				positionSet.add(BoardUtils.toMarginPos(boardSize.width(), row, col));

				repeats += 1;
			}
			// TODO: We assume that this is OthelloBoard and positions are margin-based. This should be resolved somehow
		}
		return NTuple.newWithRandomWeights(numValues, positionSet.toArray(), minWeight, maxWeight, random);
	}
}
