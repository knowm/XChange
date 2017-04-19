package org.knowm.xchange.huobi.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.huobi.BitVcFutures;
import org.knowm.xchange.huobi.dto.trade.BitVcFuturesPositionByContract;

import si.mazi.rescu.RestProxyFactory;

public class BitVcFuturesServiceRaw {

  protected final BitVcFutures bitvc;
  protected final String accessKey;
  protected HuobiDigest digest;

  public BitVcFuturesServiceRaw(Exchange exchange) {
    this.bitvc = RestProxyFactory.createProxy(BitVcFutures.class, "https://api.bitvc.com/futures");
    this.accessKey = exchange.getExchangeSpecification().getApiKey();

    this.digest = new HuobiDigest(exchange.getExchangeSpecification().getSecretKey(), "secretKey");
  }

  public BitVcFuturesPositionByContract getFuturesPositions() {
    final BitVcFuturesPositionByContract positions = bitvc.positions(accessKey, 1, requestTimestamp(), digest);

    return positions;
  }

  protected long requestTimestamp() {
    return System.currentTimeMillis() / 1000;
  }
}
