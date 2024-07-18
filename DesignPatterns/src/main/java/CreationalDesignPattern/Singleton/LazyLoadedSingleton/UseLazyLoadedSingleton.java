package CreationalDesignPattern.Singleton.LazyLoadedSingleton;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UseLazyLoadedSingleton {

	public static void main(String[] args) throws ClassNotFoundException {

		UseLazyLoadedSingleton ulls = new UseLazyLoadedSingleton();

		LazyLoadedSingleton lls = LazyLoadedSingleton.getInstance();

		givenSingleLazyLoadedImpl_whenCallGetInstance_returnSingleton();

		givenSingleLazyLoadedImpl_whenCallGetInstance_returnSingleInstance();
	}

	private static void givenSingleLazyLoadedImpl_whenCallGetInstance_returnSingleInstance()
			throws ClassNotFoundException {

		// while initializing LazyLoadedSingleton with reflection API, it fails due to
		// private constructor
		// get instance always returns a single instance of LazyLoadedSingleton
		Class ulls = Class.forName("CreationalDesignPattern.Singleton.LazyLoadedSingleton.LazyLoadedSingleton");

		try {
			ulls.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error err) {
			err.printStackTrace();
		} catch (Throwable thr) {
			thr.printStackTrace();
		}

		LazyLoadedSingleton lazyLoadedSingleton1 = LazyLoadedSingleton.getInstance();
		LazyLoadedSingleton lazyLoadedSingleton2 = LazyLoadedSingleton.getInstance();

		boolean bothSame = lazyLoadedSingleton1.hashCode() == lazyLoadedSingleton2.hashCode();
		System.out.println("The two singleton classes are equal " + bothSame);

	}

	private static void givenSingleLazyLoadedImpl_whenCallGetInstance_returnSingleton() {

		Set<LazyLoadedSingleton> set = new HashSet<>();

		List<Future<LazyLoadedSingleton>> futures = new ArrayList<>();

		// Creates a thread pool which reuses a fixed number of threads from an
		// unbounded queue.
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		Callable<LazyLoadedSingleton> runnableTask = () -> {
			System.out.println("call called for " + Thread.currentThread().getName());
			return LazyLoadedSingleton.getInstance();
		};

		for (int i = 0; i < 10; i++) {
			futures.add(executorService.submit(runnableTask));
		}

		futures.forEach(e -> {
			try {
				set.add(e.get());
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			}

		});
		executorService.shutdown();
		System.out.println("The size of set is " + set.size());

	}

}
