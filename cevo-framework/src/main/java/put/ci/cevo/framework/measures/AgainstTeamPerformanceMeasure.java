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

public class AgainstTeamPerformanceMeasure<S, T> implements PerformanceMeasure<S> {

	private final InteractionDomain<S, T> domain;
	private final Collection<T> opponents;

	private final int numRepeats;

	/**
	 * The factory is used only once. We test performance against only one player
	 */
	@AccessedViaReflection
	public AgainstTeamPerformanceMeasure(InteractionDomain<S, T> domain, Factory<T> factory, int repeats) {
		this(domain, factory.create(), repeats);
	}

	public AgainstTeamPerformanceMeasure(InteractionDomain<S, T> domain, T opponent, int repeats) {
		this(domain, singletonList(opponent), repeats);
	}

	public AgainstTeamPerformanceMeasure(InteractionDomain<S, T> domain, Collection<T> opponents) {
		this(domain, opponents, 1);
	}

	public AgainstTeamPerformanceMeasure(InteractionDomain<S, T> domain, Collection<T> opponents, int repeats) {
		this.domain = domain;
		this.opponents = opponents;
		this.numRepeats = repeats;
	}

	@Override
	public Measurement measure(final S subject, ThreadedContext context) {
		List<T> jobs = new ArrayList<>();
		for (T opponent : opponents) {
			for (int i = 0; i < numRepeats; ++i) {
				jobs.add(opponent);
			}
		}

		List<InteractionResult> results = context.invoke(
				(opponent, cntx) -> domain.interact(subject, opponent, cntx.getRandomForThread()), jobs).toList();

		return new Measurement.Builder().add(results).build();
	}
}
