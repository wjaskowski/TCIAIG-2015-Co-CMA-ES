package put.ci.cevo.rl.environment;

import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;

public class EnvironmentDecorator<S extends State, A extends Action> implements Environment<S, A> {

	protected Environment<S, A> env;

	@Override
	public Transition<S, A> computeTransition(S state, A action) {
		return env.computeTransition(state, action);
	}

	@Override
	public S getNextState(S state, RandomDataGenerator random) {
		return env.getNextState(state, random);
	}

	@Override
	public List<A> getPossibleActions(S state) {
		return env.getPossibleActions(state);
	}

	@Override
	public S sampleInitialStateDistribution(RandomDataGenerator random) {
		return env.sampleInitialStateDistribution(random);
	}

	@Override
	public boolean isTerminalState(S state) {
		return env.isTerminalState(state);
	}

	@Override
	public double getAgentPerformance(double totalReward, int numSteps, S finalState) {
		return env.getAgentPerformance(totalReward, numSteps, finalState);
	}
}
