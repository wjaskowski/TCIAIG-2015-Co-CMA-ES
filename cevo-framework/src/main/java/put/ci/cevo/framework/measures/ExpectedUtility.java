package put.ci.cevo.framework.measures;

import put.ci.cevo.framework.factories.PopulationFactory;
import put.ci.cevo.framework.factories.StaticPopulationFactory;
import put.ci.cevo.framework.interactions.InteractionDomain;
import put.ci.cevo.framework.interactions.InteractionResult;
import put.ci.cevo.util.annotations.AccessedViaReflection;
import put.ci.cevo.util.random.ThreadedContext;
import put.ci.cevo.util.random.ThreadedContext.Worker;

import java.util.Collection;
import java.util.List;

import static com.google.common.base.Objects.toStringHelper;

public class ExpectedUtility<S, T> implements PerformanceMeasure<S> {

	private final InteractionDomain<S, T> domain;
	private final StaticPopulationFactory<T> sampleFactory;

	private final int sampleSize;

	@AccessedViaReflection
	public ExpectedUtility(InteractionDomain<S, T> domain, PopulationFactory<T> sampleFactory, int sampleSize,
			ThreadedContext context) {
		this(domain, new StaticPopulationFactory<>(sampleFactory, sampleSize, context.getRandomForThread()), sampleSize);
	}

	@AccessedViaReflection
	public ExpectedUtility(InteractionDomain<S, T> domain, Collection<T> sample) {
		this(domain, new StaticPopulationFactory<>(sample), sample.size());
	}

	@AccessedViaReflection
	public ExpectedUtility(InteractionDomain<S, T> domain, StaticPopulationFactory<T> sampleFactory) {
		this(domain, sampleFactory, sampleFactory.getPopulationSize());
	}

	@AccessedViaReflection
	public ExpectedUtility(InteractionDomain<S, T> domain, StaticPopulationFactory<T> sampleFactory, int sampleSize) {
		this.domain = domain;
		this.sampleFactory = sampleFactory;
		this.sampleSize = sampleSize;
	}

	@Override
	public Measurement measure(final S subject, ThreadedContext context) {
		List<T> population = sampleFactory.createPopulation(sampleSize, context.getRandomForThread());

		List<InteractionResult> result = context.invoke(new Worker<T, InteractionResult>() {
			@Override
			public InteractionResult process(T opponent, ThreadedContext context) {
				return domain.interact(subject, opponent, context.getRandomForThread());
			}
		}, population).toList();

		return new Measurement.Builder().add(result).build();
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("sample", sampleSize).toString();
	}

}
