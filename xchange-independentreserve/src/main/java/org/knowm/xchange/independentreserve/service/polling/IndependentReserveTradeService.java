package org.knowm.xchange.independentreserve.service.polling;

import java.io.IOException;
import java.util.Collection;

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
import org.knowm.xchange.independentreserve.IndependentReserveAdapters;
import org.knowm.xchange.service.polling.trade.PollingTradeService;
import org.knowm.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParams;

public class IndependentReserveTradeService extends IndependentReserveTradeServiceRaw implements PollingTradeService {

  public IndependentReserveTradeService(Exchange exchange) {
    super(exchange);
  }

  /**
   * Assumes asking for the first 50 orders with the currency pair BTCUSD
   */
  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return IndependentReserveAdapters.adaptOpenOrders(getIndependentReserveOpenOrders(CurrencyPair.BTC_USD, 1));
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return independentReservePlaceLimitOrder(CurrencyPair.BTC_USD, limitOrder.getType(), limitOrder.getLimitPrice(), limitOrder.getTradableAmount());
  }

  @Override
  public Collection<Order> getOrder(String... orderIds)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return independentReserveCancelOrder(orderId);
  }

  /**
   * Optional parameters: {@link TradeHistoryParamPaging#getPageNumber()} indexed from 0
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    int pageNumber = ((TradeHistoryParamPaging) params).getPageNumber() + 1;
    return IndependentReserveAdapters.adaptTradeHistory(getIndependentReserveTradeHistory(pageNumber));
  }

  @Override
  public TradeHistoryParamPaging createTradeHistoryParams() {
    return new DefaultTradeHistoryParamPaging(null, 0);
  }
}
