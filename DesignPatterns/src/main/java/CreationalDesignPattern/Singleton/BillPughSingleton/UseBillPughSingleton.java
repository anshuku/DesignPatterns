package CreationalDesignPattern.Singleton.BillPughSingleton;

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
 * Here multiple threads calls the same getInstance concurrently but it always returns the same object reference.
 */
public class UseBillPughSingleton {

	public static void main(String[] args) {

		UseBillPughSingleton ubps = new UseBillPughSingleton();

		givenSynchronizedLazyLoadedImpl_whenCallgetInstance_returnSingleton();

	}

	private static void givenSynchronizedLazyLoadedImpl_whenCallgetInstance_returnSingleton() {

		Set<BillPughSingleton> set = new HashSet<>();
		List<Future<BillPughSingleton>> futures = new ArrayList<>();

		// Creates a thread pool which reuses a fixed number of threads from an
		// unbounded queue.
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		Callable<BillPughSingleton> runnableTask = () -> {
			System.out.println("run called for " + Thread.currentThread().getName());
			return BillPughSingleton.getInstance();
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
