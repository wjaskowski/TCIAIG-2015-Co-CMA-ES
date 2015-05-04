package put.ci.cevo.rl.evaluation;

import org.apache.commons.math3.random.RandomDataGenerator;
import put.ci.cevo.rl.environment.Action;
import put.ci.cevo.rl.environment.Environment;
import put.ci.cevo.rl.environment.State;
import put.ci.cevo.rl.environment.Transition;

public interface MoveSelector<S extends State, A extends Action> {
	Transition<S, A> select(S state, Environment<S, A> env, RandomDataGenerator random);
}
