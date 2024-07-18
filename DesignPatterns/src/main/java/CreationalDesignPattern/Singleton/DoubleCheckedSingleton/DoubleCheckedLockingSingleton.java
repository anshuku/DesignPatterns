package CreationalDesignPattern.Singleton.DoubleCheckedSingleton;

/*
 * This pattern reduces the number of lock acquisitions by simply checking the locking condition beforehand.
 * This results in a performance boost. This pattern is broken prior to Java 5(volatile) and is verbose.
 * 
 * To fix the issue in Draconian Singleton(Synchronized Lazy loaded Singleton)
 * We can verify if we need to create an instance of Singleton, only then we will acquire the lock.
 * Going further we again want to verify the object to ensure that the operation is atomic.
 * 
 * The instance should be volatile to prevent cache incoherence issues.
 * Java memory model allows publication of partially initialized object which leads to issues.
 * 
 * In the singleton class, instance is marked volatile to ensure that changes made by one thread 
 * is immediately visible to other threads accessing DoubleCheckedLockingSingleton class via getInstance()
 * 
 * Thread safe means multiple threads can access an instance or method of class at same time.
 * 
 * -Volatile-
 * 
 * Volatile modifier ensures visibility of changes in a variable across multiple threads.
 * Volatile keyword prevents the compiler and JVM from optimizing reads and writes to the variable
 * by storing it in a CPU cache(never stores in cache).
 * It forces the variable to be read from and written always to main memory directly.
 * Volatile enforces has-before relationship. Any subsequent read and write can view previous write.
 * 
 * Volatile keywords acts as a flag variable to signal state changes between threads. 
 * A volatile flag can be used to indicate when a thread should stop running.
 * 
 * Volatile is used alongwith synchronization to implement double-checked locking.
 * 
 * Volatile keyword does not provide atomicity and can use to scale quickly. 
 * Atomic variable to be used for atomic operations.
 * 
 * It only guarantees visibility of changes, not mutual exclusion.
 * If I want to synchronize access to a variable to prevent race conditions,
 * use synchronization mechanisms like synchronized blocks or lock objects.
 * 
 * Volatile and synchronized(changes reflect in main memory):
 * Mutual Exclusions: One one thread/process can execute a block of code(critical section) at a time(only synchronized).
 * Visibility: Changes made by one thread to a shared data is visible by other threads(Synchronized and volatile).
 * 
 * A simple x++ or x = x+1 has compound read-modify-write sequence of operations that must happen atomically.
 * In case of volatile, another thread my intervene between the sequence.
 * 
 * A better option is to use a process which delegates synchronization task to JVM. 
 */
public class DoubleCheckedLockingSingleton {

	private DoubleCheckedLockingSingleton() {

	}

	// Volatile modifier allows the change done by 1 thread to be visible by others
	// and multiple threads can modify it.
	private static volatile DoubleCheckedLockingSingleton instance;

	public static DoubleCheckedLockingSingleton getInstance() {
		if (instance == null) {
			// Take lock of the class
			synchronized (DoubleCheckedLockingSingleton.class) {
				// another thread might come in between read-write-modify operation 
				// so to make it atomic use another check
				if (instance == null) {
					instance = new DoubleCheckedLockingSingleton();
				}
			}
		}
		return instance;
	}
}
