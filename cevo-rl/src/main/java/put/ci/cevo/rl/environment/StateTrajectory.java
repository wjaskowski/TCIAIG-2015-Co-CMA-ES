package put.ci.cevo.rl.environment;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;

public class StateTrajectory<S extends State> {

	final List<S> states;

	public int getDepth() {
		return states.size();
	}

	public StateTrajectory(List<S> states) {
		this.states = new ArrayList<S>(states);
	}

	public List<S> getStates() {
		return new ArrayList<S>(states);
	}

	public S getLastState() {
		return states.get(states.size() - 1);
	}

	public StateTrajectory<S> shorten(int depth) {
		return new StateTrajectory<S>(states.subList(0, states.size() - depth));
	}

	public <A extends Action> StateTrajectory<S> lengthen(int depth, Environment<S, A> env, RandomDataGenerator random) {
		S state = getLastState();
		List<S> newStates = new ArrayList<S>(states);
		for (int d = 0; d < depth; d++) {
			Transition<S, A> transition = Transition.getRandomTransitionFromState(env, state, random);
			state = transition.getAfterState();

			if (env.isTerminalState(state)) {
				break;
			} else {
				newStates.add(state);
			}
		}

		return new StateTrajectory<S>(newStates);
	}
}
