package CreationalDesignPattern.Singleton.LazyLoadedSingleton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * The SynchronizedLazyLoadedSingleton also returns a single instance in a 
 * multi-threaded environment like EagerLoadedSingleton.
 * 
 * The programs loads the singleton object in a lazy fashion but, is an overhead of synchronization.
 */
public class UseSynchronizedLazyLoadedSingleton {

	public static void main(String[] args) {

		UseSynchronizedLazyLoadedSingleton usll = new UseSynchronizedLazyLoadedSingleton();

		SynchronizedLazyLoadedSingleton slls = SynchronizedLazyLoadedSingleton.getInstance();

		givenSynchronizedLazyLoadedImpl_whenCallGetInstance_thenReturnSingleton();
	}

	private static void givenSynchronizedLazyLoadedImpl_whenCallGetInstance_thenReturnSingleton() {

		Set<SynchronizedLazyLoadedSingleton> set = new HashSet<>();

		List<Future<SynchronizedLazyLoadedSingleton>> futures = new ArrayList<>();

		ExecutorService executorService = Executors.newFixedThreadPool(10);

		Callable<SynchronizedLazyLoadedSingleton> callableTask = () -> {
			System.out.println("The call method called for " + Thread.currentThread().getName());
			return SynchronizedLazyLoadedSingleton.getInstance();
		};

		for (int i = 0; i < 10; i++) {
			futures.add(executorService.submit(callableTask));
		}

		futures.forEach(f -> {
			try {
				set.add(f.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		});

		executorService.shutdown();

		System.out.println("The size of set is " + set.size());
	}

}
