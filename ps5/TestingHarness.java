import java.util.*;

/**
 * A simple testing harness to help you test your implementations.
 */
public class TestingHarness {
	public static void main(String[] args) {
		/* Check that we were invoked correctly. */
		if (args.length < 1 || args.length > 2 || !areAssertionsEnabled()) {
			usage();
		}
		
		/* Get a factory for the provided class. */
		BSTFactory factory = factoryFor(args[0]);
		
		/* Obtain a random generator based on the user flags. */
		Random rgen = randomSeedFor(args);
		
		/* Run tests of various sizes. */
		for (int size = 0; size <= MAX_SIZE; size++) {
			System.out.println("Testing size " + size);
			runTests(factory, size, rgen);
		}
		System.out.println("All tests completed!");
	}
	
	/**
	 * Returns the random generator that should be used in this program based on the arguments list.
	 * 
	 * @param args The program arguments.
	 * @return The random generator to use.
	 */
	private static Random randomSeedFor(String[] args) {
		return args.length == 1? new Random() : new Random(Long.parseLong(args[1]));
	}
	
	/**
	 * Runs some tests comparing the reference implementation and the test implementation.
	 * 
	 * @param factory Solution to test.
	 * @param size The size of the input arrays to use
	 * @param rgen The random generator to use.
	 */
	private static void runTests(BSTFactory factory, int size, Random rgen) {
		double[] distribution = randomDiscreteDistribution(size, rgen);
		BST tree = factory.newInstance(distribution);
		
		checkAllPresent(tree, size);
		
		/* This test makes no sense if there are no entries. */
		if (size > 0) {
			fireRandomCharges(tree, new AliasMethodRandomGenerator(distribution, rgen));
		}
	}
	
	/**
	 * Confirms that all elements that should be in the BST are actually there.
	 * 
	 * @param tree The tree to check.
	 * @param size The size of the probability distribution.
	 */
	private static void checkAllPresent(BST tree, int size) {
		for (int i = 0; i < size; i++) {
			assert tree.contains(i) : "Could not find number " + i + " in the tree.";
		}
		
		/* Try some wrong values. */
		for (int i = -1; i >= -10; i--) {
			assert !tree.contains(i) : "Found wrong value " + i + " in the tree.";
		}
		for (int i = size; i <= size + 5; i++) {
			assert !tree.contains(i) : "Found wrong value " + i + " in the tree.";
		}
	}
	
	/** The number of probes to make into the BST. */
	private static final int NUM_PROBES = 1000;
	
	/**
	 * Looks up random elements that are in range to confirm that searching works correctly.
	 * 
	 * @param tree The tree to test.
	 * @param keyGen A discrete distribution to sample from.
	 */
	private static void fireRandomCharges(BST tree, AliasMethodRandomGenerator keyGen) {
		for (int i = 0; i < NUM_PROBES; i++) {
			assert tree.contains(keyGen.next()) : "Value was missing during random probing.";
		}
	}
	
	/**
	 * Generates a random discrete distribution using the specified random generator.
	 * 
	 * @param size The number of entries in the distribution.
	 * @param rgen The underlying random source.
	 * @return The probability distribution.
	 */
	private static double[] randomDiscreteDistribution(int size, Random rgen) {
		/* Generate an array of values to use. */
		double[] values = new double[size];
		for (int i = 0; i < values.length; i++) {
			values[i] = rgen.nextDouble();
		}
		
		/* Normalize those values. */
		double sum = 0.0;
		for (double value: values) {
			sum += value;
		}
		for (int i = 0; i < values.length; i++) {
			values[i] /= sum;
		}
		
		/* Create an alias method table to generate random values from this distribution. */
		return values;
	}
	
	/** Maximimum size of a test to run. */
	private static final int MAX_SIZE = 150;
	
	/**
	 * Prints a usage message and terminates the program.
	 */
	private static void usage() {
		System.out.println("Usage: java -ea TestingHarness bst-class [random-seed]");
		System.out.println("  bst-class should be the name of the BST class you want to test.");
		System.out.println("  random-seed is an optional long to use as a random seed.");
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
}
