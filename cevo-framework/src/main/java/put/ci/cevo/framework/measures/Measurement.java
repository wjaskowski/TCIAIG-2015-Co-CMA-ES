package put.ci.cevo.framework.measures;

import com.google.common.base.Preconditions;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.StatisticalSummary;
import put.ci.cevo.framework.interactions.InteractionResult;

import java.util.List;

/**
 * The result of PerformanceMeasure.measure()
 */
public final class Measurement {

	private final StatisticalSummary stats;
	private final int effort;

	public final static class Builder {

		private final DescriptiveStatistics stats = new DescriptiveStatistics();
		private int totalEffort = 0;

		public Builder add(double result, int effort) {
			stats.addValue(result);
			totalEffort += effort;
			return this;
		}

		public Builder add(InteractionResult result) {
			stats.addValue(result.firstResult());
			totalEffort += result.getEffort();
			return this;
		}

		public Builder add(List<InteractionResult> results) {
			results.forEach(this::add);
			return this;
		}

		public Measurement build() {
			return new Measurement(stats, totalEffort);
		}

	}

	public Measurement(StatisticalSummary stats, int effort) {
		Preconditions.checkArgument(effort >= 0);
		this.stats = stats;
		this.effort = effort;
	}

	public int getEffort() {
		return effort;
	}

	public StatisticalSummary stats() {
		return stats;
	}
}
