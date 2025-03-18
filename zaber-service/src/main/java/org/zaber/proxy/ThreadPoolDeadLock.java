package org.zaber.proxy;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author : otter
 */
public class ThreadPoolDeadLock {
    ExecutorService executorService= Executors.newSingleThreadExecutor();

    public class RenderPageTask implements Callable<String> {
        public String call() throws Exception{
            Future<String> header, footer;
            header= executorService.submit(()->{
                return "header";
            });
            footer= executorService.submit(()->{
                return "footer";
            });
            return header.get()+ footer.get();
        }
    }

    public void submitTask(){
        executorService.submit(new RenderPageTask());
    }

    public static void main(String[] args) {
        new ThreadPoolDeadLock().submitTask();
    }
}
