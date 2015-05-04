package put.ci.cevo.rl.agent;

import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;

import put.ci.cevo.rl.agent.functions.ValueFunction;
import put.ci.cevo.rl.agent.policies.ControlPolicy;
import put.ci.cevo.rl.environment.Action;
import put.ci.cevo.rl.environment.Environment;
import put.ci.cevo.rl.environment.State;
import put.ci.cevo.rl.environment.Transition;
import put.ci.cevo.util.annotations.AccessedViaReflection;

public class ValueFunctionAgent<S extends State, A extends Action> implements Agent<S, A> {

	private ControlPolicy<S, A> policy;
	private ValueFunction<S, A> valueFunction;

	@AccessedViaReflection
	public ValueFunctionAgent(ControlPolicy<S, A> policy, ValueFunction<S, A> valueFunction) {
		this.valueFunction = valueFunction;
		this.policy = policy;
	}

	public ValueFunction<S, A> getValueFunction() {
		return valueFunction;
	}

	public ControlPolicy<S, A> getPolicy() {
		return policy;
	}

	@Override
	public String toString() {
		return valueFunction.toString();
	}

	@Override
	public void observeTransition(Transition<S, A> transition) {
		// ignore transition
	}

	@Override
	public A chooseAction(S state, Environment<S, A> environment, RandomDataGenerator random) {
		List<A> actions = environment.getPossibleActions(state);
		double[] values = new double[actions.size()];
		for (int a = 0; a < actions.size(); a++) {
			values[a] = valueFunction.getValue(state, actions.get(a));
		}
		return policy.chooseAction(state, actions, values, random);
	}
}
