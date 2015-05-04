package put.ci.cevo.newexperiments;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import put.ci.cevo.util.ReflectionUtils;

public class ExperimentRunner {

	private static final Logger logger = Logger.getLogger(ExperimentRunner.class);

	private static Experiment createExperiment(String className) throws InstantiationException, IllegalAccessException {
		return (Experiment) ReflectionUtils.forName(className).newInstance();
	}

	/**
	 * @param args
	 *            Experiment class name (must implement Experiment interface)
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		String experimentClassName = args[0];

		Experiment experiment = createExperiment(experimentClassName);
		logger.info("Executing experiment: " + experimentClassName);
		experiment.run(ArrayUtils.subarray(args, 1, args.length));
	}
}
