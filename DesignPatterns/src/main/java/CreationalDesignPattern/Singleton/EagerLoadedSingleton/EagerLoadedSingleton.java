package CreationalDesignPattern.Singleton.EagerLoadedSingleton;

/* 
 * This approach works in a multi-threaded environment by using a class level static variable.
 * Here thread safety is achieved as we inline the object creation.
 * 
 * The application loads the static variable immediately on start, even when it's not needed.
 */
public class EagerLoadedSingleton {

	private EagerLoadedSingleton() {

	}

	private static final EagerLoadedSingleton instance = new EagerLoadedSingleton();

	public static EagerLoadedSingleton getInstance() {
		return instance;
	}
}
