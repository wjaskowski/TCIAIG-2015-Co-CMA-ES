package put.ci.cevo.games.encodings.ntuple.expanders;

/**
 * Performs symmetry expansion only along Y axis.
 */
public class YAxisSymmetryExpander implements SymmetryExpander {

	private static final int DEFAULT_MARGIN = 2;

	private final int boardWidth;

	public YAxisSymmetryExpander(int boardWidth) {
		this(boardWidth, DEFAULT_MARGIN);
	}

	public YAxisSymmetryExpander(int boardWidth, int margin) {
		this.boardWidth = boardWidth + margin;
	}

	@Override
	public int[] getSymmetries(int location) {
		int M = boardWidth - 1;

		int x = location % boardWidth;
		int y = location / boardWidth;

		int[] a = new int[2];
		a[0] = (flat(x, y));
		a[1] = (flat(M - x, y));
		return a;
	}

	private int flat(int x, int y) {
		return x + boardWidth * y;
	}

	@Override
	public int numSymmetries() {
		return 2;
	}

	public int boardWidth() {
		return boardWidth;
	}

}
