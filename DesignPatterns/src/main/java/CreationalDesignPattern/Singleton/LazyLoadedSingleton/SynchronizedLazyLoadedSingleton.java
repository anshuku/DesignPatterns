package CreationalDesignPattern.Singleton.LazyLoadedSingleton;

/*
 * Lazy loaded singleton class where the same single instance is returned.
 * 
 * This implementation works in multi-threaded environment.
 * By using synchronized keyword on the method getInstance, we restrict threads from accessing it concurrently.
 * 
 * This is also called Draconian Singleton.
 * 
 * Thread safe - A method or class instance can be accessed by multiple threads at same time without any problem.
 * Despite the class is thread safe(synchronized), there is a performance drawback. 
 * Each time we need the instance of the singleton, we need to acquire a potentially unnecessary lock. 
 * 
 * To fix this we can verify that if we need to create an instance of object
 * only then we will acquire the lock.
 */
public class SynchronizedLazyLoadedSingleton {

	private SynchronizedLazyLoadedSingleton() {

	}

	private static SynchronizedLazyLoadedSingleton instance;

	public static synchronized SynchronizedLazyLoadedSingleton getInstance() {
		if (null == instance) {
			instance = new SynchronizedLazyLoadedSingleton();
		}
		return instance;
	}

}
