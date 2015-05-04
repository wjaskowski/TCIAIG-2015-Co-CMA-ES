package put.ci.cevo.games.othello.mdp;

import static put.ci.cevo.games.othello.OthelloBoard.opponent;

import java.util.ArrayList;
import java.util.List;

import put.ci.cevo.games.GameRules;
import put.ci.cevo.games.board.Board;
import put.ci.cevo.games.board.BoardUtils;
import put.ci.cevo.games.othello.OthelloBoard;

public class OthelloRules implements GameRules<OthelloState, OthelloMove> {

	private static final int MAX_PLAYER = OthelloBoard.BLACK;

	@Override
	public OthelloState createInitialState() {
		return new OthelloState(new OthelloBoard(), OthelloBoard.BLACK);
	}

	@Override
	public boolean isTerminal(OthelloState state) {
		OthelloBoard board = state.getBoard();
		for (int row = 0; row < board.getHeight(); row++) {
			for (int col = 0; col < board.getWidth(); col++) {
				if (canPlace(board, row, col, state.getCurrentPlayer())
					|| canPlace(board, row, col, opponent(state.getCurrentPlayer()))) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public List<OthelloMove> findMoves(OthelloState state) {
		OthelloBoard board = state.getBoard();
		List<OthelloMove> actions = new ArrayList<OthelloMove>();
		for (int row = 0; row < board.getHeight(); row++) {
			for (int col = 0; col < board.getWidth(); col++) {
				if (canPlace(board, row, col, state.getCurrentPlayer())) {
					actions.add(new OthelloMove(row, col));
				}
			}
		}
		return actions;
	}

	@Override
	public int getOutcome(OthelloState state) {
		Board board = state.getBoard();
		return (BoardUtils.countPieces(board, Board.BLACK) - BoardUtils.countPieces(board, Board.WHITE));
	}

	@Override
	public OthelloState makeMove(OthelloState state, OthelloMove action) {
		OthelloBoard board = state.getBoard();

		if (action == null) {
			return new OthelloState(board.clone(), opponent(state.getCurrentPlayer()));
		}
		OthelloBoard nextBoard = board.createAfterState(action.getRow(), action.getCol(), state.getCurrentPlayer());
		return new OthelloState(nextBoard, opponent(state.getCurrentPlayer()));
	}

	public boolean canPlace(OthelloBoard board, int row, int col, int player) {
		int move = OthelloBoard.toPos(row, col);
		return board.isValidMove(move, player);
	}

	@Override
	public boolean isMaxPlayer(int currentPlayer) {
		return (currentPlayer == MAX_PLAYER);
	}

	@Override
	public double getReward(OthelloState state) {
		int outcome = getOutcome(state);
		if (outcome < 0) {
			return -1;
		} else if (outcome > 0) {
			return 1;
		} else {
			return 0;
		}
	}
}
