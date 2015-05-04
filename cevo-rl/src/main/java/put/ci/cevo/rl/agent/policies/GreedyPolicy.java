package put.ci.cevo.rl.agent.policies;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;

import put.ci.cevo.rl.environment.Action;
import put.ci.cevo.rl.environment.State;

public class GreedyPolicy<S extends State, A extends Action> implements ControlPolicy<S, A> {

	private static final double EPS = 0;

	public A chooseBestAction(List<A> actions, double[] values, RandomDataGenerator random) {
		double bestEval = Float.NEGATIVE_INFINITY;
		List<A> bestActions = new ArrayList<A>();

		for (int i = 0; i < actions.size(); i++) {
			double actionValue = values[i];
			if (actionValue == bestEval || Math.abs(actionValue - bestEval) < EPS) {
				bestActions.add(actions.get(i));
			} else if (actionValue > bestEval) {
				bestEval = actionValue;
				bestActions.clear();
				bestActions.add(actions.get(i));
			}
		}

		if (random != null && bestActions.size() > 1) {
			return bestActions.get(random.nextInt(0, bestActions.size() - 1));
		} else {
			return bestActions.get(0);
		}
	}

	@Override
	public A chooseAction(S state, List<A> actions, double[] values, RandomDataGenerator random) {
		if (actions.isEmpty()) {
			return null;
		}

		return chooseBestAction(actions, values, random);
	}
}
