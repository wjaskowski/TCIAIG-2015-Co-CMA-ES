package put.ci.cevo.rl.agent;

import org.apache.commons.math3.random.RandomDataGenerator;

import put.ci.cevo.rl.environment.Action;
import put.ci.cevo.rl.environment.Environment;
import put.ci.cevo.rl.environment.State;
import put.ci.cevo.rl.environment.Transition;

public interface Agent<S extends State, A extends Action> {

	void observeTransition(Transition<S, A> transition);

	A chooseAction(S state, Environment<S, A> environment, RandomDataGenerator random);
}
