package put.ci.cevo.rl;

import org.apache.commons.math3.random.RandomDataGenerator;

import put.ci.cevo.rl.agent.Agent;
import put.ci.cevo.rl.environment.Action;
import put.ci.cevo.rl.environment.Environment;
import put.ci.cevo.rl.environment.State;
import put.ci.cevo.rl.environment.Transition;

public class Simulation<S extends State, A extends Action> {

	private final Agent<S, A> agent;
	private final Environment<S, A> env;

	private int numSteps;
	private double totalReward;

	public Simulation(Agent<S, A> agent, Environment<S, A> env) {
		this.agent = agent;
		this.env = env;
		reset();
	}

	public int getNumSteps() {
		return numSteps;
	}

	public double getTotalReward() {
		return totalReward;
	}

	public void reset() {
		numSteps = 0;
		totalReward = 0;
	}

	public S run(RandomDataGenerator random) {
		return run(Integer.MAX_VALUE, random);
	}

	public S run(int maxNumSteps, RandomDataGenerator random) {
		S currentState = env.sampleInitialStateDistribution(random);

		while (!env.isTerminalState(currentState) && (numSteps < maxNumSteps)) {
			A chosenAction = agent.chooseAction(currentState, env, random);
			Transition<S, A> transition = env.computeTransition(currentState, chosenAction);

			agent.observeTransition(transition);
			totalReward += transition.getReward();
			currentState = env.getNextState(transition.getAfterState(), random);
			numSteps++;
		}

		return currentState;
	}
}
