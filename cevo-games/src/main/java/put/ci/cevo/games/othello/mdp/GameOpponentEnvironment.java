package put.ci.cevo.games.othello.mdp;

import org.apache.commons.math3.random.RandomDataGenerator;

import put.ci.cevo.games.board.Board;
import put.ci.cevo.rl.agent.Agent;
import put.ci.cevo.rl.environment.Action;
import put.ci.cevo.rl.environment.Environment;
import put.ci.cevo.rl.environment.EnvironmentDecorator;
import put.ci.cevo.rl.environment.GameState;
import put.ci.cevo.rl.environment.Transition;

public class GameOpponentEnvironment<S extends GameState, A extends Action> extends EnvironmentDecorator<S, A> {

	private Agent<S, A> opponent;
	private int opponentColor;

	public GameOpponentEnvironment(Environment<S, A> env, Agent<S, A> opponent, int opponentColor) {
		this.env = env;
		this.opponent = opponent;
		this.opponentColor = opponentColor;
	}

	@Override
	public S sampleInitialStateDistribution(RandomDataGenerator random) {
		S state = env.sampleInitialStateDistribution(random);
		if (opponentColor == Board.BLACK) {
			state = getNextState(state, random);
		}
		return state;
	}

	@Override
	public S getNextState(S state, RandomDataGenerator random) {
		if (isTerminalState(state)) {
			return state;
		}

		A move = opponent.chooseAction(state, env, random);
		Transition<S, A> opponentTransition = computeTransition(state, move);
		return opponentTransition.getAfterState();
	}

	@Override
	public double getAgentPerformance(double totalReward, int numSteps, S finalState) {
		if (opponentColor == Board.BLACK) {
			return -env.getAgentPerformance(totalReward, numSteps, finalState);
		} else {
			return env.getAgentPerformance(totalReward, numSteps, finalState);
		}
	}
}
