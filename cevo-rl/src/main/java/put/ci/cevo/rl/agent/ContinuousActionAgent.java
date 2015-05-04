package put.ci.cevo.rl.agent;

import org.apache.commons.math3.random.RandomDataGenerator;

import put.ci.cevo.rl.agent.functions.RealFunction;
import put.ci.cevo.rl.environment.ContinuousAction;
import put.ci.cevo.rl.environment.Environment;
import put.ci.cevo.rl.environment.EnvironmentEncoder;
import put.ci.cevo.rl.environment.State;
import put.ci.cevo.rl.environment.Transition;

public class ContinuousActionAgent<S extends State> implements Agent<S, ContinuousAction> {

	private RealFunction function;
	private EnvironmentEncoder<S, ContinuousAction> encoder;

	public ContinuousActionAgent(RealFunction function, EnvironmentEncoder<S, ContinuousAction> encoder) {
		this.function = function;
		this.encoder = encoder;
	}

	@Override
	public void observeTransition(Transition<S, ContinuousAction> transition) {
		// TODO Auto-generated method stub
	}

	@Override
	public ContinuousAction chooseAction(S state, Environment<S, ContinuousAction> environment,
			RandomDataGenerator random) {

		return new ContinuousAction(function.getValue(encoder.encode(state)));
	}
}
