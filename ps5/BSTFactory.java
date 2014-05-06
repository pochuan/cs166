import java.lang.reflect.*;

/**
 * An object that can construct static BSTs from arrays of weights.
 */
public class BSTFactory {
	/** The constructor to invoke. */
	private final Constructor<? extends BST> ctor;
	
	/** The name of the underlying class. */
	private final String className;
	
	/**
	 * Constructs a new BSTFactory that can construct objects of the specified type.
	 * 
	 * @param clazz The Class object representing that class that should be instantiated.
	 */
	public BSTFactory(Class<? extends BST> clazz) {
		assert clazz != null;
		
		/* Store the class name for later use. */
		className = clazz.toString();
		try {
			ctor = clazz.getDeclaredConstructor(double[].class);
		} catch (Exception e) {
			throw new RuntimeException("Couldn't find constructor taking a double[]", e);
		}
	}
	
	/**
	 * Returns the name of the class that will be instantiated by this factory.
	 * 
	 * @return The name of the class to construct.
	 */
	public String getClassName() {
		return className;
	}
	
	/**
	 * Constructs a new BST object given the specified weights on the elements.
	 * 
	 * @param elements The weights assigned to each element.
	 * @return A static BST holding keys with those weights.
	 */
	public BST newInstance(double[] elements) {
		try {
			return ctor.newInstance(elements);
		} catch (Exception e) {
			throw new RuntimeException("Error instantiating class.", e);
		}
	}
}
