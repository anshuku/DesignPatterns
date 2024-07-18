package CreationalDesignPattern.Singleton.LazyLoadedSingleton;

/*
 * Lazy loaded singleton class where the same single instance is returned
 * 
 * Lazy loaded singleton class is initialized only when the getInstance is called.
 * This is via lazy initialization
 * 
 * This results in dirty read where thread B reads uncommitted changes of thread A even when
 * the first thread has not committed the data, if thread A roll backs the changes then thread B
 * reads a data which is no longer valid and is inconsistent.
 * 
 * Dirty read - read of uncommitted data.
 * 
 * Here dirty read happens when multiple threads concurrently call the same getInstance method 
 * 
 * This implementation works only in single-threaded environment due to dirty reads.
 */
public class LazyLoadedSingleton {

	private static LazyLoadedSingleton instance;

	private LazyLoadedSingleton() {

	}

	public static LazyLoadedSingleton getInstance() {
		if (null == instance) {
			instance = new LazyLoadedSingleton();
		}
		return instance;
	}

}
