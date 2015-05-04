package put.ci.cevo.games.othello.mdp;

import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;

import put.ci.cevo.rl.environment.Environment;
import put.ci.cevo.rl.environment.Transition;

public class OthelloEnvironment implements Environment<OthelloState, OthelloMove> {

	private static final OthelloRules OTHELLO = new OthelloRules();

	@Override
	public Transition<OthelloState, OthelloMove> computeTransition(OthelloState state, OthelloMove action) {
		OthelloState afterState = OTHELLO.makeMove(state, action);
		double reward = isTerminalState(afterState) ? OTHELLO.getReward(afterState) : 0;
		return new Transition<>(state, action, afterState, reward, isTerminalState(afterState));
	}

	@Override
	public List<OthelloMove> getPossibleActions(OthelloState state) {
		return OTHELLO.findMoves(state);
	}

	@Override
	public OthelloState sampleInitialStateDistribution(RandomDataGenerator random) {
		return OTHELLO.createInitialState();
	}

	@Override
	public boolean isTerminalState(OthelloState state) {
		return OTHELLO.isTerminal(state);
	}

	public Transition<OthelloState, OthelloMove> getEnvironmentChange(OthelloState state) {
		return new Transition<>(state, null, state, 0.0);
	}

	@Override
	public OthelloState getNextState(OthelloState state, RandomDataGenerator random) {
		return state;
	}

	@Override
	public double getAgentPerformance(double totalReward, int numSteps, OthelloState finalState) {
		return OTHELLO.getOutcome(finalState);
	}
}
