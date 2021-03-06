package put.ci.cevo.games.othello.players.published;

import java.io.IOException;

import org.apache.commons.collections15.Factory;
import org.apache.commons.io.IOUtils;
import put.ci.cevo.games.board.BoardEvaluationType;
import put.ci.cevo.games.othello.OthelloLeague;
import put.ci.cevo.games.othello.players.OthelloNTuplesPlayer;
import put.ci.cevo.games.othello.players.OthelloPlayer;

/**
 * Manning, E.: Using Resource-Limited Nash Memory to Improve an Othello Evaluation Function. Computational Intelligence
 * and AI in Games, IEEE Transactions on 2(1) (2010) 40-53 This player is similar to {@link Manning2010TCIAG2Player},
 * but it also used mutations of tuple positions Named Nash1-N-Tuple in (Runnarson & Lucas, 2014), so, actually, it is
 * more advanced than {@link Manning2010TCIAG2Player}.
 */
public class Manning2010TCIAG1Player implements Factory<OthelloPlayer> {

	@Override
	public OthelloNTuplesPlayer create() {
		return new OthelloNTuplesPlayer(OthelloLeague.fromOthelloLeagueFormat(PLAYER),
				BoardEvaluationType.OUTPUT_NEGATION);
	}

	static String PLAYER;

	static {
		try {
			PLAYER = IOUtils.toString(Manning2010TCIAG1Player.class.getResourceAsStream("Manning2010TCIAG1.player"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
