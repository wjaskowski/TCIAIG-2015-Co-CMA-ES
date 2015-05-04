package put.ci.cevo.games.player.minmax;

import put.ci.cevo.rl.environment.GameState;

public interface GameNode {

	public GameState getState(int player);

	public int[] getValidMoves();

	public void makeMove(int move, int player);

	public void undoMove(int move);

}