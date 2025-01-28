package org.knowm.xchange.coinex.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.knowm.xchange.coinex.CoinexAdapters;
import org.knowm.xchange.coinex.CoinexExchange;
import org.knowm.xchange.coinex.dto.account.CoinexMarketType;
import org.knowm.xchange.coinex.dto.account.CoinexOrder;
import org.knowm.xchange.coinex.dto.trade.CoinexCancelOrderRequest;
import org.knowm.xchange.coinex.service.params.CoinexOpenOrdersParams;
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

  public CoinexOrder cancelOrder(Long orderId, Instrument instrument) throws IOException {
    CoinexCancelOrderRequest request = CoinexCancelOrderRequest.builder()
        .orderId(orderId)
        .instrument(instrument)
        .marketType(CoinexMarketType.SPOT)
        .build();
    return coinexAuthenticated
        .cancelOrder(apiKey, exchange.getNonceFactory(), coinexV2ParamsDigest, request)
        .getData();
  }

  public CoinexOrder orderStatus(Instrument instrument, String orderId) throws IOException {
    String market = CoinexAdapters.toString(instrument);
    return coinexAuthenticated
        .orderStatus(apiKey, exchange.getNonceFactory(), coinexV2ParamsDigest, market, orderId)
        .getData();
  }

  public List<CoinexOrder> pendingOrders(CoinexOpenOrdersParams coinexOpenOrdersParams) throws IOException {
    String market = CoinexAdapters.toString(coinexOpenOrdersParams.getInstrument());
    Integer page = coinexOpenOrdersParams.getOffset();
    Integer limit = Optional.ofNullable(coinexOpenOrdersParams.getLimit()).orElse(CoinexOpenOrdersParams.DEFAULT_LIMIT);
    return coinexAuthenticated
        .pendingOrders(apiKey, exchange.getNonceFactory(), coinexV2ParamsDigest, market, CoinexAdapters.toString(CoinexMarketType.SPOT),
             null, null, page, limit)
        .getData();
  }
}
