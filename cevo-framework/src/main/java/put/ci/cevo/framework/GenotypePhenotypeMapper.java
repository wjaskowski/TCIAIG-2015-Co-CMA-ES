package put.ci.cevo.framework;

import org.apache.commons.math3.random.RandomDataGenerator;

public interface GenotypePhenotypeMapper<G, P> {

	public P getPhenotype(G genotype, RandomDataGenerator random);

}
