package put.ci.cevo.rl.environment;

public interface EnvironmentEncoder<S extends State, A extends Action> {

	public double[] encode(S state);

	public double[] encode(A action);
}
