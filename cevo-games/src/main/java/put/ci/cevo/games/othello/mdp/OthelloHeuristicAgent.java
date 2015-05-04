package put.ci.cevo.games.othello.mdp;

import put.ci.cevo.games.GamePolicy;
import put.ci.cevo.rl.agent.ValueFunctionAgent;
import put.ci.cevo.rl.agent.functions.StateValueFunction;
import put.ci.cevo.rl.agent.functions.wpc.WPC;
import put.ci.cevo.rl.agent.policies.ControlPolicy;

public class OthelloHeuristicAgent extends ValueFunctionAgent<OthelloState, OthelloMove> {

	private static double[] weights = { 1.00f, -0.25f, 0.10f, 0.05f, 0.05f, 0.10f, -0.25f, 1.00f, -0.25f, -0.25f,
		0.01f, 0.01f, 0.01f, 0.01f, -0.25f, -0.25f, 0.10f, 0.01f, 0.05f, 0.02f, 0.02f, 0.05f, 0.01f, 0.10f, 0.05f,
		0.01f, 0.02f, 0.01f, 0.01f, 0.02f, 0.01f, 0.05f, 0.05f, 0.01f, 0.02f, 0.01f, 0.01f, 0.02f, 0.01f, 0.05f, 0.10f,
		0.01f, 0.05f, 0.02f, 0.02f, 0.05f, 0.01f, 0.10f, -0.25f, -0.25f, 0.01f, 0.01f, 0.01f, 0.01f, -0.25f, -0.25f,
		1.00f, -0.25f, 0.10f, 0.05f, 0.05f, 0.10f, -0.25f, 1.00f };

	public OthelloHeuristicAgent() {
		super(new GamePolicy<OthelloState, OthelloMove>(0), new StateValueFunction<OthelloState, OthelloMove>(new WPC(
			weights), new OthelloEnvironment()));
	}

	public OthelloHeuristicAgent(ControlPolicy<OthelloState, OthelloMove> policy) {
		super(policy, new StateValueFunction<OthelloState, OthelloMove>(new WPC(weights), new OthelloEnvironment()));
	}
}
