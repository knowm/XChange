package com.xeiam.xchange.huobi.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.huobi.HuobiAdapters;
import com.xeiam.xchange.huobi.dto.trade.HuobiCancelOrderResult;
import com.xeiam.xchange.huobi.dto.trade.HuobiOrder;
import com.xeiam.xchange.huobi.dto.trade.HuobiPlaceOrderResult;
import com.xeiam.xchange.huobi.service.TradeServiceRaw;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

public class GenericTradeService extends BaseExchangeService implements PollingTradeService {

  private final Map<CurrencyPair, Integer> coinTypes;
  private static final OpenOrders noOpenOrders = new OpenOrders(Collections.<LimitOrder> emptyList());
  private final TradeServiceRaw tradeServiceRaw;

  /**
   * Constructor
   *
   * @param tradeServiceRaw
   */
  public GenericTradeService(Exchange exchange, TradeServiceRaw tradeServiceRaw) {

    super(exchange);
    this.tradeServiceRaw = tradeServiceRaw;

    coinTypes = new HashMap<CurrencyPair, Integer>(2);
    coinTypes.put(CurrencyPair.BTC_CNY, 1);
    coinTypes.put(CurrencyPair.LTC_CNY, 2);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    List<LimitOrder> openOrders = new ArrayList<LimitOrder>();
    for (CurrencyPair currencyPair : exchange.getMetaData().getMarketMetaDataMap().keySet()) {
      HuobiOrder[] orders = tradeServiceRaw.getOrders(coinTypes.get(currencyPair));

      for (int i = 0; i < orders.length; i++) {
        openOrders.add(HuobiAdapters.adaptOpenOrder(orders[i], currencyPair));
      }
    }

    if (openOrders.size() <= 0) {
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
    for (CurrencyPair currencyPair : exchange.getMetaData().getMarketMetaDataMap().keySet()) {
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
  public UserTrades getTradeHistory(Object... arguments) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    throw new NotYetImplementedForExchangeException();
  }
}
