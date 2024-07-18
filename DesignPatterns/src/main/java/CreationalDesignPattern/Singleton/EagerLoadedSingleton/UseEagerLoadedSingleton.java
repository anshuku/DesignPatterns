package CreationalDesignPattern.Singleton.EagerLoadedSingleton;

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
 * The below works in a multi-threaded environment but it uses eager loading which is a drawback.
 */
public class UseEagerLoadedSingleton {

	public static void main(String[] args) {

		UseEagerLoadedSingleton uels = new UseEagerLoadedSingleton();
		EagerLoadedSingleton els = EagerLoadedSingleton.getInstance();

		givenEagerLoadedImpl_whenCallGetInstance_thenReturnSingleton();
	}

	private static void givenEagerLoadedImpl_whenCallGetInstance_thenReturnSingleton() {
		Set<EagerLoadedSingleton> set = new HashSet<>();

		ExecutorService executorService = Executors.newFixedThreadPool(10);

		// Multiple threads call callableTask, then call() calls getInstance() to fetch an instance.
		Callable<EagerLoadedSingleton> callableTask = () -> {
			System.out.println("call task called for " + Thread.currentThread().getName());
			return EagerLoadedSingleton.getInstance();
		};

		List<Future<EagerLoadedSingleton>> futures = new ArrayList<>();

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

		System.out.println("The size of the set is " + set.size());

	}

}
