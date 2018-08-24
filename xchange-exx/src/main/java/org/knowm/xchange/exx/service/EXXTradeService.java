package org.knowm.xchange.exx.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exx.EXXAdapters;
import org.knowm.xchange.exx.dto.params.EXXCancelOrderByCurrencyPair;
import org.knowm.xchange.exx.dto.params.EXXOpenOrdersParams;
import org.knowm.xchange.exx.dto.params.EXXTradeHistoryParams;
import org.knowm.xchange.exx.dto.trade.EXXPlaceOrder;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class EXXTradeService extends EXXTradeServiceRaw implements TradeService {
  public EXXTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return null;
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams openOrdersParams)
      throws ExchangeException, IOException {
    if (!(openOrdersParams instanceof EXXOpenOrdersParams)) {
      throw new ExchangeException("You need to provide the currency pair to get open orders.");
    }
    EXXOpenOrdersParams params = (EXXOpenOrdersParams) openOrdersParams;
    CurrencyPair currencyPair = params.getCurrencyPair();
    String currency = currencyPairFormat(currencyPair); // etp_btc

    return EXXAdapters.convertOpenOrders(
        getExxOpenOrders(currency, params.getPageIndex(), params.getType()), currencyPair);
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
    String type = EXXAdapters.convertByType(limitOrder.getType());

    EXXPlaceOrder exxPlaceOrder = placeExxOrder(amount, currency, price, type);

    if (exxPlaceOrder != null && exxPlaceOrder.getCode() == 100) {
      return exxPlaceOrder.getId();
    } else {
      return null;
    }
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    String currency = "etp_btc";

    return cancelExxOrder(orderId, currency);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams cancelOrderParams) throws IOException {
    if (!(cancelOrderParams instanceof CancelOrderParams)
        && !(cancelOrderParams instanceof EXXCancelOrderByCurrencyPair)) {
      throw new ExchangeException(
          "You need to provide the currency pair and the order id to cancel an order.");
    }
    EXXCancelOrderByCurrencyPair params = (EXXCancelOrderByCurrencyPair) cancelOrderParams;
    CurrencyPair currencyPair = params.getCurrencyPair();
    String currency = currencyPairFormat(currencyPair); // etp_btc

    return cancelExxOrder(params.getId(), currency);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new EXXOpenOrdersParams();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new EXXTradeHistoryParams(null, null);
  }

  private String currencyPairFormat(CurrencyPair currencyPair) {
    String currency = currencyPair.toString();
    currency = currency.replace("/", "_");

    return currency;
  }
}
