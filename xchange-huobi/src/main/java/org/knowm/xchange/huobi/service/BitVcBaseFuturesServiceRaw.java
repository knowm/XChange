package org.knowm.xchange.huobi.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.huobi.BitVcFutures;
import org.knowm.xchange.huobi.dto.trade.BitVcFuturesPositionByContract;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.RestProxyFactory;

public class BitVcBaseFuturesServiceRaw extends BaseExchangeService implements BaseService {

  protected final BitVcFutures bitvc;
  protected final String accessKey;
  protected HuobiDigest digest;

  public BitVcBaseFuturesServiceRaw(Exchange exchange) {

    super(exchange);

    this.bitvc = RestProxyFactory.createProxy(BitVcFutures.class, "https://api.bitvc.com/futures", getClientConfig());
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
