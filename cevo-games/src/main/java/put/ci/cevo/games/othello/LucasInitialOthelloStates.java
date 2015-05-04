package put.ci.cevo.games.othello;

import put.ci.cevo.games.board.RectSize;
import put.ci.cevo.games.othello.mdp.OthelloState;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 1000 intial board positions from Runarsson & Lucas, "Preference Learning for Move Prediction and Evaluation Function
 * Approximation in Othello", 2014
 * In all boards black is to play
 *
 *
 */
public final class LucasInitialOthelloStates {
	final List<OthelloState> lucasBoards;

	/**
	 * Some of the Lucas's boards are symmetric duplicates. We exclude them by default, so there is less than 1000 \
	 * boards, actually
	 */
	public LucasInitialOthelloStates() {
		this(true);
	}

	/**
	 * @param excludeDuplicateBoards whether exclude symmetric duplicates
	 */
	public LucasInitialOthelloStates(boolean excludeDuplicateBoards) {
		lucasBoards = readLucasBoardsFromFile(excludeDuplicateBoards);
	}

	private static List<OthelloState> readLucasBoardsFromFile(boolean excludeDuplicateBoards) {
		List<OthelloState> boards = new ArrayList<>();
		try (Scanner scanner = new Scanner(LucasInitialOthelloStates.class.getResourceAsStream("lucas_boards.txt"))) {
			while (scanner.hasNextInt()) {
				int[][] rawBoard = readRawBoard(scanner);
				OthelloBoard board = new OthelloBoard(rawBoard);
				if (excludeDuplicateBoards && isSymmetricDuplicate(board, boards)) {
					continue;
				}
				boards.add(new OthelloState(board, OthelloBoard.BLACK));
			}
		}
		return boards;
	}

	public static void main(String args[]) {
		findSymmetricDuplicates();
	}

	public List<OthelloState> boards() {
		ArrayList<OthelloState> clonedBoards = new ArrayList<>();
		for (OthelloState board : lucasBoards) {
			clonedBoards.add(board.clone());
		}
		return clonedBoards;
	}

	private static void findSymmetricDuplicates() {
		List<OthelloState> boards = new LucasInitialOthelloStates(false).boards();
		int numDuplicates = 0;
		for (int i = 0; i < boards.size(); ++i) {
			for (int j = 0; j < i; ++j) {
				if (boards.get(i).getBoard().equalsBySymmetry(boards.get(j).getBoard())) {
					System.out.println(i + " " + j);
					numDuplicates += 1;
					break;
				}
			}
		}
		System.out.println("Number of duplicates = " + numDuplicates);
	}

	private static int[][] readRawBoard(Scanner scanner) {
		final RectSize SIZE = OthelloBoard.SIZE;
		int[][] rawBoard = new int[SIZE.rows()][SIZE.columns()];
		for (int i = 0; i < SIZE.rows(); ++i) {
			for (int j = 0; j < SIZE.columns(); ++j) {
				rawBoard[i][j] = scanner.nextInt();
			}
		}
		return rawBoard;
	}

	/**
	 * Is board (symmetric) duplicate in boards
	 */
	private static boolean isSymmetricDuplicate(OthelloBoard board, List<OthelloState> boards) {
		List<OthelloBoard> symmetricBoards = board.createSymmetricBoards();
		for (OthelloState lucasInitialStates : boards) {
			for (OthelloBoard b : symmetricBoards) {
				if (b.equals(lucasInitialStates.getBoard())) {
					return true;
				}
			}
		}
		return false;
	}
}
