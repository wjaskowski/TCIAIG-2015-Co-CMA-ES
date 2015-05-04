package put.ci.cevo.rl.agent.policies;

import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;

import put.ci.cevo.rl.environment.Action;
import put.ci.cevo.rl.environment.State;

public interface ControlPolicy<S extends State, A extends Action> {

	A chooseAction(S state, List<A> actions, double[] values, RandomDataGenerator random);

}
