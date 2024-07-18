package CreationalDesignPattern.Singleton.DoubleCheckedSingleton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UseDoubleCheckedLockingSingleton {

	public static void main(String[] args) {

		UseDoubleCheckedLockingSingleton udcl = new UseDoubleCheckedLockingSingleton();
		DoubleCheckedLockingSingleton dcls = DoubleCheckedLockingSingleton.getInstance();

		givenDoubleCheckedLazyLoadedImpl_whenCallGetInstance_thenReturnSingleton();

	}

	private static void givenDoubleCheckedLazyLoadedImpl_whenCallGetInstance_thenReturnSingleton() {

		Set<DoubleCheckedLockingSingleton> set = new HashSet<>();

		List<Future<DoubleCheckedLockingSingleton>> futures = new ArrayList<>();

		ExecutorService executorService = Executors.newFixedThreadPool(10);

		Callable<DoubleCheckedLockingSingleton> callableTask = () -> {
			System.out.println("Call called for " + Thread.currentThread().getName());
			return DoubleCheckedLockingSingleton.getInstance();
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
		System.out.println("Set size is " + set.size());

	}

}
