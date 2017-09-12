package org.knowm.xchange.coinbase.service;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.CoinbaseAdapters;
import org.knowm.xchange.coinbase.dto.trade.CoinbaseTransfer;
import org.knowm.xchange.coinbase.dto.trade.CoinbaseTransfers;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * @author jamespedwards42
 */
public final class CoinbaseTradeService extends CoinbaseTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinbaseTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws NotAvailableFromExchangeException, IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, IOException {

    final CoinbaseTransfer transfer = marketOrder.getType().equals(OrderType.BID) ? super.buy(marketOrder.getTradableAmount())
        : super.sell(marketOrder.getTradableAmount());
    return transfer.getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws NotAvailableFromExchangeException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws NotAvailableFromExchangeException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }

  /**
   * Authenticated resource which returns the user’s Bitcoin purchases and sells. Sorted in descending order by creation date.
   *
   * @see <a href="https://coinbase.com/api/doc/1.0/transfers/index.html">coinbase.com/api/doc/1.0/transfers/index.html</a>
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    Integer page = null;
    Integer limit = null;
    if (params instanceof TradeHistoryParamPaging) {
      page = ((TradeHistoryParamPaging) params).getPageNumber() + 1;
      limit = ((TradeHistoryParamPaging) params).getPageLength();
    }

    final CoinbaseTransfers transfers = super.getCoinbaseTransfers(page, limit);
    return CoinbaseAdapters.adaptTrades(transfers);
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    DefaultTradeHistoryParamPaging params = new DefaultTradeHistoryParamPaging();
    params.setPageNumber(0);
    params.setPageLength(100);
    return params;
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

}
