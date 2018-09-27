package org.knowm.xchange.coinbene.service;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbene.CoinbeneException;
import org.knowm.xchange.coinbene.dto.CoinbeneAdapters;
import org.knowm.xchange.coinbene.dto.CoinbeneResponse;
import org.knowm.xchange.coinbene.dto.trading.CoinbeneLimitOrder;
import org.knowm.xchange.coinbene.dto.trading.CoinbeneOrderResponse;
import org.knowm.xchange.coinbene.dto.trading.CoinbeneOrders;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;

public class CoinbeneTradeServiceRaw extends CoinbeneBaseService {

  protected CoinbeneTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  protected CoinbeneOrderResponse placeCoinbeneLimitOrder(LimitOrder order)
      throws IOException, CoinbeneException {
    Map<String, String> params = getCommonParams();
    params.put("price", order.getLimitPrice().toString());
    params.put("quantity", order.getOriginalAmount().toString());
    params.put("symbol", CoinbeneAdapters.adaptCurrencyPair(order.getCurrencyPair()));
    params.put("type", CoinbeneAdapters.adaptOrderType(order.getType()));

    return checkSuccess(coinbene.placeOrder(formAndSignRequestJson(params)));
  }

  protected CoinbeneResponse cancelCoinbeneOrder(String orderId)
      throws IOException, CoinbeneException {
    Map<String, String> params = getCommonParams();
    params.put("orderid", orderId);

    return checkSuccess(coinbene.cancelOrder(formAndSignRequestJson(params)));
  }

  protected CoinbeneLimitOrder.Container getCoinbeneOrder(String orderId)
      throws IOException, CoinbeneException {
    Map<String, String> params = getCommonParams();
    params.put("orderid", orderId);

    return checkSuccess(coinbene.getOrderStatus(formAndSignRequestJson(params)));
  }

  protected CoinbeneOrders.Container getCoinbeneOpenOrders(CurrencyPair currencyPair)
      throws IOException, CoinbeneException {
    Map<String, String> params = getCommonParams();
    params.put("symbol", CoinbeneAdapters.adaptCurrencyPair(currencyPair));

    return checkSuccess(coinbene.getOpenOrders(formAndSignRequestJson(params)));
  }
}
