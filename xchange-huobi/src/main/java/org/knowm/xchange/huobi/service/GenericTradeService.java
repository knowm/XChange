package org.knowm.xchange.huobi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.huobi.HuobiAdapters;
import org.knowm.xchange.huobi.dto.trade.HuobiCancelOrderResult;
import org.knowm.xchange.huobi.dto.trade.HuobiOrder;
import org.knowm.xchange.huobi.dto.trade.HuobiPlaceOrderResult;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class GenericTradeService extends BaseExchangeService implements TradeService {

  private final Map<CurrencyPair, Integer> coinTypes;
  private static final OpenOrders noOpenOrders = new OpenOrders(Collections.<LimitOrder>emptyList());
  private final TradeServiceRaw tradeServiceRaw;

  /**
   * Constructor
   *
   * @param tradeServiceRaw
   */
  public GenericTradeService(Exchange exchange, TradeServiceRaw tradeServiceRaw) {

    super(exchange);
    this.tradeServiceRaw = tradeServiceRaw;

    coinTypes = new HashMap<>(2);
    coinTypes.put(CurrencyPair.BTC_CNY, 1);
    coinTypes.put(CurrencyPair.LTC_CNY, 2);
  }

  public TradeServiceRaw getTradeServiceRaw() {
    return this.tradeServiceRaw;
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    // TODO use params for currency pair
    List<LimitOrder> openOrders = new ArrayList<>();
    for (CurrencyPair currencyPair : exchange.getExchangeMetaData().getCurrencyPairs().keySet()) {
      HuobiOrder[] orders = tradeServiceRaw.getOrders(coinTypes.get(currencyPair));

      for (int i = 0; i < orders.length; i++) {
        openOrders.add(HuobiAdapters.adaptOpenOrder(orders[i], currencyPair));
      }
    }

    if (openOrders.isEmpty()) {
      return noOpenOrders;
    }

    return new OpenOrders(openOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    HuobiPlaceOrderResult result = tradeServiceRaw.placeMarketOrder(marketOrder.getType(), coinTypes.get(marketOrder.getCurrencyPair()),
        marketOrder.getTradableAmount());
    return HuobiAdapters.adaptPlaceOrderResult(result);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    HuobiPlaceOrderResult result = tradeServiceRaw.placeLimitOrder(limitOrder.getType(), coinTypes.get(limitOrder.getCurrencyPair()),
        limitOrder.getLimitPrice(), limitOrder.getTradableAmount());
    return HuobiAdapters.adaptPlaceOrderResult(result);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    final long id = Long.parseLong(orderId);

    HuobiCancelOrderResult result = null;
    for (CurrencyPair currencyPair : exchange.getExchangeMetaData().getCurrencyPairs().keySet()) {
      result = tradeServiceRaw.cancelOrder(coinTypes.get(currencyPair), id);

      if (result.getCode() == 0) {
        break;
      } else if (result.getCode() == 26) { // Order does not exist
        continue;
      } else {
        break;
      }
    }
    return result != null && "success".equals(result.getResult());
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      cancelOrder(((CancelOrderByIdParams) orderParams).orderId);
    }
    return false;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }
}
