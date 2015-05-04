package put.ci.cevo.framework.measures;

import org.apache.commons.collections15.Factory;
import put.ci.cevo.framework.interactions.InteractionDomain;
import put.ci.cevo.framework.interactions.InteractionResult;
import put.ci.cevo.util.annotations.AccessedViaReflection;
import put.ci.cevo.util.random.ThreadedContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Measures performance based on a number of games against a given player or a team. The player (or the environment)
 * should be randomized so that the (repeated) result is not deterministic
 *
 * @deprecated Use AgainstTeamPerformanceMeasure which does the same but pararell
 */
@Deprecated
public class AgainstPlayerPerformanceMeasure<S, T> implements PerformanceMeasure<S> {

	private final InteractionDomain<S, T> domain;
	private final List<T> opponents;

	private final int numRepeats;

	/**
	 * The factory is used only once. We test performance against only one player
	 */
	@AccessedViaReflection
	public AgainstPlayerPerformanceMeasure(InteractionDomain<S, T> domain, Factory<T> factory, int repeats) {
		this(domain, factory.create(), repeats);
	}

	@AccessedViaReflection
	public AgainstPlayerPerformanceMeasure(InteractionDomain<S, T> domain, Collection<T> opponents) {
		this(domain, opponents, 1);
	}

	@AccessedViaReflection
	public AgainstPlayerPerformanceMeasure(InteractionDomain<S, T> domain, T opponent, int repeats) {
		this(domain, singletonList(opponent), repeats);
	}

	/**
	 * @param opponents Opponents to interact against
	 * @param repeats   number of interactions to make against the opponent
	 */
	public AgainstPlayerPerformanceMeasure(InteractionDomain<S, T> domain, Collection<T> opponents,
			int repeats) {
		this.domain = domain;
		this.opponents = new ArrayList<>(opponents);
		this.numRepeats = repeats;
	}

	/**
	 * Measure performance of the subject
	 */
	@Override
	public Measurement measure(S subject, ThreadedContext context) {
		final Measurement.Builder measurementBuilder = new Measurement.Builder();
		for (T opponent : opponents) {
			for (int i = 0; i < numRepeats; ++i) {
				InteractionResult result = domain.interact(subject, opponent, context.getRandomForThread());
				measurementBuilder.add(result);
			}
		}
		return measurementBuilder.build();
	}
}
