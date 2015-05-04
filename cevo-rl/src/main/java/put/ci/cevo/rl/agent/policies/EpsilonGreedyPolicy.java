package put.ci.cevo.rl.agent.policies;

import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;

import put.ci.cevo.rl.environment.Action;
import put.ci.cevo.rl.environment.State;
import put.ci.cevo.util.annotations.AccessedViaReflection;

public class EpsilonGreedyPolicy<S extends State, A extends Action> extends GreedyPolicy<S, A> {

	private final double epsilon;

	@AccessedViaReflection
	public EpsilonGreedyPolicy(double epsilon) {
		this.epsilon = epsilon;
	}

	@Override
	public A chooseAction(S state, List<A> actions, double[] values, RandomDataGenerator random) {
		if (actions.isEmpty()) {
			return null;
		} else if (actions.size() == 1) {
			return actions.get(0);
		} else if (random.nextUniform(0.0, 1.0) < epsilon) {
			return actions.get(random.nextInt(0, actions.size() - 1));
		} else {
			return chooseBestAction(actions, values, random);
		}
	}
}
