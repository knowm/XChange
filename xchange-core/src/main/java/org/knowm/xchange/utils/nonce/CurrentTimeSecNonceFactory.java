package org.knowm.xchange.utils.nonce;

import si.mazi.rescu.SynchronizedValueFactory;

public class CurrentTimeSecNonceFactory implements SynchronizedValueFactory<Long> {

    private long lag = 0;

    public CurrentTimeSecNonceFactory() {
    }

    public CurrentTimeSecNonceFactory(long lag) {
        this.lag = lag;
    }

    @Override
    public Long createValue() {
        return System.currentTimeMillis() / 1000 + lag;
    }

}