package put.ci.cevo.games;

import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;

import put.ci.cevo.games.board.Board;
import put.ci.cevo.rl.agent.policies.ControlPolicy;
import put.ci.cevo.rl.agent.policies.EpsilonGreedyPolicy;
import put.ci.cevo.rl.agent.policies.GreedyPolicy;
import put.ci.cevo.rl.environment.Action;
import put.ci.cevo.rl.environment.GameState;
import put.ci.cevo.util.annotations.AccessedViaReflection;

public class GamePolicy<S extends GameState, A extends Action> implements ControlPolicy<S, A> {

	private ControlPolicy<S, A> innerPolicy;

	public GamePolicy(double epsilon) {
		if (epsilon == 0) {
			this.innerPolicy = new GreedyPolicy<>();
		} else {
			this.innerPolicy = new EpsilonGreedyPolicy<>(epsilon);
		}

	}

	@AccessedViaReflection
	public GamePolicy(ControlPolicy<S, A> innerPolicy) {
		this.innerPolicy = innerPolicy;
	}

	private boolean isMaxPlayer(int currentPlayer) {
		return (currentPlayer == Board.BLACK);
	}

	@Override
	public A chooseAction(S state, List<A> actions, double[] values, RandomDataGenerator random) {
		if (!isMaxPlayer(state.getCurrentPlayer())) {
			for (int v = 0; v < values.length; v++) {
				values[v] = -values[v];
			}
		}
		return innerPolicy.chooseAction(state, actions, values, random);
	}
}