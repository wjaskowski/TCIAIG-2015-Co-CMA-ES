package put.ci.cevo.framework.measures;

import put.ci.cevo.util.random.ThreadedContext;

public interface PerformanceMeasure<V> {

	public Measurement measure(V subject, ThreadedContext context);

}
