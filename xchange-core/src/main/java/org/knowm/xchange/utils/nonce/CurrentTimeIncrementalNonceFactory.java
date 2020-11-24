package org.knowm.xchange.utils.nonce;

import si.mazi.rescu.SynchronizedValueFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

public class CurrentTimeIncrementalNonceFactory
        implements SynchronizedValueFactory<Long> {

    private final AtomicLong nonce = new AtomicLong(0);

    private final Supplier<Long> timeFn;

    public CurrentTimeIncrementalNonceFactory(final TimeUnit timeUnit) {
        switch (timeUnit) {
            case SECONDS:
                timeFn = () -> System.currentTimeMillis()/1000;
                break;
            case MILLISECONDS:
                timeFn = System::currentTimeMillis;
                break;
            case MICROSECONDS:
                timeFn = () -> System.nanoTime()/1000;
                break;
            case NANOSECONDS:
                timeFn = System::nanoTime;
                break;
            default:
                throw new IllegalArgumentException(String.format("TimeUnit %s not supported", timeUnit));
        }
    }

    @Override
    public Long createValue() {
        return nonce.updateAndGet(prevNonce -> {
            long newNonce = timeFn.get();

            if (newNonce <= prevNonce) {
                newNonce = prevNonce + 1;
            }
            return newNonce;
        });
    }
}
