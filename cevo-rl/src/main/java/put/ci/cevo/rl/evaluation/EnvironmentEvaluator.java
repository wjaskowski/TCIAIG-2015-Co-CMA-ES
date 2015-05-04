package put.ci.cevo.rl.evaluation;

import org.apache.commons.math3.random.RandomDataGenerator;
import put.ci.cevo.rl.environment.Action;
import put.ci.cevo.rl.environment.Environment;
import put.ci.cevo.rl.environment.State;
import put.ci.cevo.rl.environment.Transition;

public class EnvironmentEvaluator<S extends State, A extends Action> {
	private Environment<S, A> env;

	public EnvironmentEvaluator(Environment<S, A> env) {
		this.env = env;
	}

	public double getCumulativeReward(MoveSelector<S, A> moveSelector, RandomDataGenerator random) {
		double totalReward = 0;
		S currentState = env.sampleInitialStateDistribution(random);

		while (!env.isTerminalState(currentState)) {
			Transition<S, A> transition = moveSelector.select(currentState, env, random);
			totalReward += transition.getReward();
			currentState = env.getNextState(transition.getAfterState(), random);
		}

		return totalReward;
	}
}
