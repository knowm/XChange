package org.knowm.xchange.coinex.service;

import java.io.IOException;
import org.knowm.xchange.coinex.CoinexAdapters;
import org.knowm.xchange.coinex.CoinexExchange;
import org.knowm.xchange.coinex.dto.account.CoinexOrder;
import org.knowm.xchange.instrument.Instrument;

public class CoinexTradeServiceRaw extends CoinexBaseService {

  public CoinexTradeServiceRaw(CoinexExchange exchange) {
    super(exchange);
  }

  public CoinexOrder createOrder(CoinexOrder coinexOrder) throws IOException {
    return coinexAuthenticated
        .createOrder(apiKey, exchange.getNonceFactory(), coinexV2ParamsDigest, coinexOrder)
        .getData();
  }

  public CoinexOrder orderStatus(Instrument instrument, String orderId) throws IOException {
    String market = CoinexAdapters.toString(instrument);
    return coinexAuthenticated
        .orderStatus(apiKey, exchange.getNonceFactory(), coinexV2ParamsDigest, market, orderId)
        .getData();
  }
}
