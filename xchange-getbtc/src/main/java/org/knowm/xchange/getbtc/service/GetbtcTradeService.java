package org.knowm.xchange.getbtc.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.getbtc.GetbtcAdapters;
import org.knowm.xchange.getbtc.dto.params.GetbtcCancelOrderByCurrencyPair;
import org.knowm.xchange.getbtc.dto.params.GetbtcOpenOrdersParams;
import org.knowm.xchange.getbtc.dto.params.GetbtcTradeHistoryParams;
import org.knowm.xchange.getbtc.dto.trade.GetbtcPlaceOrder;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * kevinobamatheus@gmail.com
 *
 * @author kevingates
 */
public class GetbtcTradeService extends GetbtcTradeServiceRaw implements TradeService {
  public GetbtcTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return null;
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams openOrdersParams)
      throws ExchangeException, IOException {
    if (!(openOrdersParams instanceof GetbtcOpenOrdersParams)) {
      throw new ExchangeException("You need to provide the currency pair to get open orders.");
    }
    GetbtcOpenOrdersParams params = (GetbtcOpenOrdersParams) openOrdersParams;
    CurrencyPair currencyPair = params.getCurrencyPair();
    String currency = currencyPairFormat(currencyPair); // etp_btc

    return GetbtcAdapters.convertOpenOrders(getGetbtcOpenOrders(currency), currencyPair);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    return null;
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    BigDecimal amount = limitOrder.getOriginalAmount();
    String currency = currencyPairFormat(limitOrder.getCurrencyPair());
    BigDecimal price = limitOrder.getLimitPrice();
    String type = GetbtcAdapters.convertByType(limitOrder.getType());

    GetbtcPlaceOrder exxPlaceOrder = placeGetbtcOrder(amount, currency, price, type);

    if (exxPlaceOrder != null && exxPlaceOrder.getCode() == 100) {
      return exxPlaceOrder.getId();
    } else {
      return null;
    }
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    String currency = "etp_btc";

    return cancelGetbtcOrder(orderId, currency);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams cancelOrderParams) throws IOException {
    if (!(cancelOrderParams instanceof CancelOrderParams)
        && !(cancelOrderParams instanceof GetbtcCancelOrderByCurrencyPair)) {
      throw new ExchangeException(
          "You need to provide the currency pair and the order id to cancel an order.");
    }
    GetbtcCancelOrderByCurrencyPair params = (GetbtcCancelOrderByCurrencyPair) cancelOrderParams;
    CurrencyPair currencyPair = params.getCurrencyPair();
    String currency = currencyPairFormat(currencyPair); // etp_btc

    return cancelGetbtcOrder(params.getId(), currency);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new GetbtcOpenOrdersParams();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new GetbtcTradeHistoryParams(null, null);
  }

  private String currencyPairFormat(CurrencyPair currencyPair) {
    String currency = currencyPair.toString();
    currency = currency.replace("/", "_");

    return currency;
  }
}
