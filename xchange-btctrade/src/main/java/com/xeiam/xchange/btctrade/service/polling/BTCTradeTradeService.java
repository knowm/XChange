package com.xeiam.xchange.btctrade.service.polling;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.btctrade.BTCTradeAdapters;
import com.xeiam.xchange.btctrade.dto.BTCTradeResult;
import com.xeiam.xchange.btctrade.dto.trade.BTCTradePlaceOrderResult;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class BTCTradeTradeService extends BTCTradeTradeServiceRaw implements PollingTradeService {

  /**
   * @param exchangeSpecification
   */
  public BTCTradeTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OpenOrders getOpenOrders() {

    return BTCTradeAdapters.adaptOpenOrders(getBTCTradeOrders(0, "open"), CurrencyPair.BTC_CNY);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String placeMarketOrder(MarketOrder marketOrder) {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String placeLimitOrder(LimitOrder limitOrder) {

    final BTCTradePlaceOrderResult result;
    if (limitOrder.getType() == OrderType.BID) {
      result = buy(limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
    }
    else {
      result = sell(limitOrder.getTradableAmount(), limitOrder.getLimitPrice());
    }
    return BTCTradeAdapters.adaptPlaceOrderResult(result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean cancelOrder(String orderId) {

    BTCTradeResult result = cancelBTCTradeOrder(orderId);
    return BTCTradeAdapters.adaptResult(result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Trades getTradeHistory(Object... arguments) {

    long since = arguments.length > 0 ? toLong(arguments[0]) : 0L;
    return BTCTradeAdapters.adaptTrades(getBTCTradeOrders(since, "all"), CurrencyPair.BTC_CNY);
  }

}
