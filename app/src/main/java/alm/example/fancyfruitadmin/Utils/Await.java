package alm.example.fancyfruitadmin.Utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java9.util.concurrent.CompletableFuture;

public class Await<T> {

    public Await() {
    }

    public T get(Callable<T> callback) {
        try {
            // INIT COMPLETABLE FUTURE
            ExecutorService exec = Executors.newSingleThreadExecutor();
            return exec.submit(callback).get();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

}