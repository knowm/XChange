package org.knowm.xchange.huobi.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.huobi.BitVc;
import org.knowm.xchange.huobi.BitVcFutures;
import si.mazi.rescu.RestProxyFactory;

public class BitVcFuturesServiceRaw {

    protected final BitVcFutures bitvc;
    protected final String accessKey;
    protected HuobiDigest digest;

    public BitVcFuturesServiceRaw(Exchange exchange) {
        this.bitvc = RestProxyFactory.createProxy(BitVcFutures.class, "https://api.bitvc.com/futures");
        this.accessKey = exchange.getExchangeSpecification().getApiKey();

        /** BitVc Futures expect a different secret key digest name from BitVc spot and Huobi */
        this.digest = new HuobiDigest(exchange.getExchangeSpecification().getSecretKey(), "secretKey");
    }

    protected long requestTimestamp() {
        return System.currentTimeMillis() / 1000;
    }
}
