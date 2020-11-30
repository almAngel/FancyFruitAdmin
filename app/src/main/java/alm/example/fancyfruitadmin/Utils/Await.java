package alm.example.fancyfruitadmin.Utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import java9.util.concurrent.CompletableFuture;

public class Await<T> {

    public Await() {
    }

    public T get(Resource<?> resource, Callable<T> callback) {
        try {
            // INIT COMPLETABLE FUTURE
            return CompletableFuture.supplyAsync(() -> {
                try {
                    // RETURN RESULT INSIDE CALLBACK
                    return callback.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}