package put.ci.cevo.rl.evaluation;

import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;
import put.ci.cevo.rl.agent.functions.RealFunction;
import put.ci.cevo.rl.environment.Action;
import put.ci.cevo.rl.environment.Environment;
import put.ci.cevo.rl.environment.State;
import put.ci.cevo.rl.environment.Transition;

public class AfterstateEvaluator<S extends State, A extends Action> implements MoveSelector<S,A> {

	private RealFunction function;

	public AfterstateEvaluator(RealFunction function) {
		this.function = function;
	}

	@Override
	public Transition<S, A> select(S state, Environment<S, A> env, RandomDataGenerator random) {
		List<A> actions = env.getPossibleActions(state);
		Transition<S, A> bestTransition = null;
		double bestValue = Double.NEGATIVE_INFINITY;

		for (A action : actions) {
			Transition<S, A> transition = env.computeTransition(state, action);
			double value = transition.getReward() + function.getValue(transition.getAfterState().getFeatures());
			if (value > bestValue) {
				bestTransition = transition;
				bestValue = value;
			}
		}

		return bestTransition;
	}
}
