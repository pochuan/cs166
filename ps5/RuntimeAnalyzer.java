import java.util.*;

/**
 * Test driver to analyze the runtimes of the various data structures. Once you seem to have the data
 * structures working, use this testing harness to see how fast the structures are in practice!
 */
public class RuntimeAnalyzer {
	public static void main(String[] args) {
		/* Check that we were invoked correctly. */
		if (args.length < 0 || !areAssertionsEnabled()) {
			usage();
		}
			
		/* Get a factory with which we can run some tests. */
		BSTFactory[] factories = new BSTFactory[args.length];
		for (int i = 0; i < args.length; i++) {
			factories[i] = factoryFor(args[i]);
		}
		
		/* Run some tests! */
		for (BSTFactory factory: factories) {
			runDistributionTest(new Random(0), factory, new UniformDistributionGenerator());
		}
		for (BSTFactory factory: factories) {
			runDistributionTest(new Random(0), factory, new ZipfianDistributionGenerator(1.0));
		}
		for (BSTFactory factory: factories) {
			runDistributionTest(new Random(0), factory, new ZipfianDistributionGenerator(1.5));
		}
		for (BSTFactory factory: factories) {
			runDistributionTest(new Random(0), factory, new ZipfianDistributionGenerator(2.0));
		}
		for (BSTFactory factory: factories) {
			runSequentialAccessTest(factory);
		}
		for (BSTFactory factory: factories) {
			runWorkingSetTest(new Random(0), factory, 16);
		}
		for (BSTFactory factory: factories) {
			runWorkingSetTest(new Random(0), factory, 1024);
		}
	}
	
	/**
	 * Prints a usage message and terminates the program.
	 */
	private static void usage() {
		System.out.println("Usage: java -ea RuntimeAnalyzer bst-class*");
		System.out.println("  bst-class should be a space-separated list of all the classes that you want");
		System.out.println("     to run through the test driver.");
		System.out.println("  Remember to run Java with the -ea option to enable assertions.");
		System.exit(-1);
	}
	
	/**
	 * Returns whether assertions are enabled.
	 * 
	 * @return Whether assertions are enabled.
	 */
	private static boolean areAssertionsEnabled() {
		boolean result = false;
		assert result = true;
		return result;
	}
	
	/**
	 * Constructs a factory that can produce objects of the specified BST type.
	 * 
	 * @param className The name of the class to construct.
	 */
	@SuppressWarnings("unchecked")
	private static BSTFactory factoryFor(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			assert BST.class.isAssignableFrom(clazz);
			
			return new BSTFactory((Class<? extends BST>) clazz);
		} catch (Exception e) {
			throw new RuntimeException("Error constructing a factory for " + className, e);
		}
	}
	
	/* The number of lookups to do on the distribution tests. */
	private static final int NUM_LOOKUPS = 10000000;
	
	/**
	 * Times the BST on a particular probability distribution, reporting the elapsed time.
	 * 
	 * @param rgen The random generator to use
	 * @param factory A factory for producing BSTs.
	 * @param distribution The distribution from which the elements should be sampled.
	 */
	private static void runDistributionTest(Random rgen, BSTFactory factory,
			                                DistributionGenerator distribution) {
		System.out.println("Testing " + factory.getClassName() + " on a " + distribution.getName() + " distribution.");
		System.out.println("  Setting up...");
		
		/* Randomly permute the distribution so that the effective distribution follows some pattern,
		 * but there isn't any ordering on 0 ... n - 1.
		 */
		double[] elements = permute(distribution.get(), rgen);
		
		/* Create a BST for those elements. */
		BST bst = factory.newInstance(elements);
		
		/* Construct an alias method generator over the distribution. */
		AliasMethodRandomGenerator keyGen = new AliasMethodRandomGenerator(elements, rgen);
		
		/* Fire off a lot of lookups and see how long it takes! */
		System.out.println("  Running...");
		
		final long startTime = System.nanoTime();
		for (int i = 0; i < NUM_LOOKUPS; i++) {
			bst.contains(keyGen.next());
		}
		final long endTime = System.nanoTime();
		
		System.out.println("Time to do " + NUM_LOOKUPS + " lookups: " + (endTime - startTime) + "ns");
	}
	
	/**
	 * Times the BST on a sequential access, reporting timing information.
	 * 
	 * @param factory A factory for producing BSTs.
	 */
	private static void runSequentialAccessTest(BSTFactory factory) {
		System.out.println("Testing " + factory.getClassName() + " on sequential accesses");
		System.out.println("  Setting up...");
		
		/* Set up a uniform distribution. */
		double[] elements = new double[NUM_LOOKUPS];
		for (int i = 0; i < elements.length; i++) {
			elements[i] = 1.0 / NUM_LOOKUPS;
		}
		
		/* Create a BST for those elements. */
		BST bst = factory.newInstance(elements);
		
		/* Run the sequential access test. */
		System.out.println("  Running...");
		
		final long startTime = System.nanoTime();
		for (int i = 0; i < NUM_LOOKUPS; i++) {
			bst.contains(i);
		}
		final long endTime = System.nanoTime();
		
		System.out.println("Time to do " + NUM_LOOKUPS + " lookups: " + (endTime - startTime) + "ns");
	}
	
	/**
	 * Times the BST on a sample that has several distinct working sets in various areas, reporting the results.
	 * 
	 * @param rgen The random generator to use.
	 * @param factory A factory for producing BSTs.
	 */
	private static void runWorkingSetTest(Random rgen, BSTFactory factory, int numWorkingSets) {
		assert numWorkingSets >= 1;
		
		System.out.println("Testing " + factory.getClassName() + " on accesses with " + numWorkingSets + (numWorkingSets > 1? "" : "s") + " working sets.");
		System.out.println("  Setting up...");
		
		/* Set up a uniform distribution. */
		double[] elements = new double[NUM_LOOKUPS];
		for (int i = 0; i < elements.length; i++) {
			elements[i] = 1.0 / NUM_LOOKUPS;
		}
		
		/* Create a BST for those elements. */
		BST bst = factory.newInstance(elements);
		
		/* Run the sequential access test. */
		System.out.println("  Running...");
		
		final long startTime = System.nanoTime();
		for (int i = 0; i < NUM_LOOKUPS; i++) {
			int index = rgen.nextInt(NUM_LOOKUPS / numWorkingSets) + i / numWorkingSets;
			bst.contains(index);
		}
		final long endTime = System.nanoTime();
		
		System.out.println("Time to do " + NUM_LOOKUPS + " lookups: " + (endTime - startTime) + "ns");
	}
	
	/**
	 * Permutes the specified array, destructively modifying it in the process. This uses the Fisher-Yates
	 * shuffle algorithm.
	 * 
	 * @param arr The array to permute.
	 * @param rgen A random generator to use when permuting it.
	 */
	private static double[] permute(double[] arr, Random rgen) {
		for (int i = 0; i < arr.length; i++) {
			final int swapIndex = rgen.nextInt(arr.length - i);
			final double temp = arr[i];
			arr[i] = arr[swapIndex];
			arr[swapIndex] = temp;
		}
		return arr;
	}
	
	/* An interface representing an object that can generate probability distributions. */
	private static interface DistributionGenerator {
		public double[] get();
		public String getName();
	}
	
	/**
	 * A distribution generator that generates a uniform distribution.
	 */
	private static final class UniformDistributionGenerator implements DistributionGenerator {
		private static final int DISTRIBUTION_SIZE = 1 << 20;
		
		@Override
		public double[] get() {
			double[] result = new double[DISTRIBUTION_SIZE];
			for (int i = 0; i < result.length; i++) {
				result[i] = 1.0 / DISTRIBUTION_SIZE;
			}
			return result;
		}

		@Override
		public String getName() {
			return "uniform";
		}
	}
	
	/**
	 * A distribution generator that generates a Zipfian distribution with specified z parameter.
	 */
	private static final class ZipfianDistributionGenerator implements DistributionGenerator {
		private static final int DISTRIBUTION_SIZE = 1 << 15;
		private final double z;
		
		public ZipfianDistributionGenerator(double z) {
			this.z = z;
		}
		
		
		@Override
		public double[] get() {
			double[] result = new double[DISTRIBUTION_SIZE];
			for (int i = 0; i < result.length; i++) {
				result[i] = 1.0 / Math.pow(i + 1, z);
			}
			
			/* Normalize the table. */
			double sum = 0.0;
			for (double d: result) {
				sum += d;
			}
			for (int i = 0; i < result.length; i++) {
				result[i] /= sum;
			}
			
			return result;
		}

		@Override
		public String getName() {
			return "Zipfian (" + z + ")";
		}
	}
}
