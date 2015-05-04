package put.ci.cevo.rl.agent.functions;

import put.ci.cevo.rl.environment.Action;
import put.ci.cevo.rl.environment.DirectEnvironmentEncoder;
import put.ci.cevo.rl.environment.Environment;
import put.ci.cevo.rl.environment.EnvironmentEncoder;
import put.ci.cevo.rl.environment.State;
import put.ci.cevo.rl.environment.Transition;
import put.ci.cevo.util.annotations.AccessedViaReflection;

public class StateValueFunction<S extends State, A extends Action> implements ValueFunction<S, A> {

	private final RealFunction function;
	private final Environment<S, A> model;
	private final EnvironmentEncoder<S, A> encoder;

	@AccessedViaReflection
	public StateValueFunction(RealFunction function, Environment<S, A> environmentModel) {
		this(function, environmentModel, new DirectEnvironmentEncoder<S, A>());
	}

	@AccessedViaReflection
	public StateValueFunction(RealFunction function, Environment<S, A> model, EnvironmentEncoder<S, A> encoder) {
		this.model = model;
		this.function = function;
		this.encoder = encoder;
	}

	@Override
	public double getValue(S state, A action) {
		Transition<S, A> transition = model.computeTransition(state, action);
		S afterState = transition.getAfterState();
		return function.getValue(encoder.encode(afterState));
	}

	@Override
	public void update(S state, A action, double expectedValue, double learningRate) {
		function.update(encoder.encode(state), expectedValue, learningRate);
	}
}
