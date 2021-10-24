package org.knowm.xchange.ftx.service;

import org.knowm.xchange.ftx.FtxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class FtxPlaceOrderExecutor {
    
    private static final int LIMIT = Integer.getInteger("ftx.place.limit", 2);
    private static final long SLEEP_MILLIS = Long.getLong("ftx.place.sleep", 200L);
    private static final long MAX_SLEEP_MILLIS = Long.getLong("ftx.place.maxSleep", 10_000L);
    private static final long SLEEP_MARGIN_MILLIS = 5;
    
    private final ReentrantLock lock = new ReentrantLock(true); 
    private final List<Long> timestamps;

    public FtxPlaceOrderExecutor() {
        timestamps = new ArrayList<>();
        for(int i = 0; i < LIMIT; i++) {
            timestamps.add(0L);
        }
    }

    public String executePlace(Callable<String> callable) throws IOException {
        long t0 = System.currentTimeMillis();
        lock.lock();
        try {
            long t1 = System.currentTimeMillis();
            if (t1 - t0 > MAX_SLEEP_MILLIS) {
                throw new FtxException("Exceeded waiting time " + MAX_SLEEP_MILLIS + "ms");
            }
            
            long sleepTime = SLEEP_MILLIS + SLEEP_MARGIN_MILLIS - (t1 - timestamps.get(0));
            if (sleepTime > 0) {
                Thread.sleep(sleepTime);
            }
            String result = callable.call();
            
            timestamps.add(System.currentTimeMillis());
            timestamps.remove(0);
            
            return result;
        } catch (Exception e) {
            if (e instanceof IOException) {
                throw (IOException) e;
            }
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException("Error placing order", e);
        } finally {
            lock.unlock();
        }
    }
}
