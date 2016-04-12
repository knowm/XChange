package org.knowm.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptsy.CryptsyAdapters;
import org.knowm.xchange.cryptsy.CryptsyCurrencyUtils;
import org.knowm.xchange.cryptsy.dto.CryptsyOrder.CryptsyOrderType;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyCancelOrderReturn;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyOpenOrdersReturn;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyPlaceOrderReturn;
import org.knowm.xchange.cryptsy.dto.trade.CryptsyTradeHistoryReturn;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.polling.trade.PollingTradeService;
import org.knowm.xchange.service.polling.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParamsTimeSpan;

/**
 * @author ObsessiveOrange
 */
public class CryptsyTradeService extends CryptsyTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptsyTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, ExchangeException {

    CryptsyOpenOrdersReturn openOrdersReturnValue = getCryptsyOpenOrders();
    return CryptsyAdapters.adaptOpenOrders(openOrdersReturnValue);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException, ExchangeException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException, ExchangeException {

    CryptsyPlaceOrderReturn result = super.placeCryptsyLimitOrder(CryptsyCurrencyUtils.convertToMarketId(limitOrder.getCurrencyPair()),
        limitOrder.getType() == OrderType.ASK ? CryptsyOrderType.Sell : CryptsyOrderType.Buy, limitOrder.getTradableAmount(),
        limitOrder.getLimitPrice());

    return Integer.toString(result.getReturnValue());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, ExchangeException {

    CryptsyCancelOrderReturn ret = super.cancelSingleCryptsyLimitOrder(Integer.valueOf(orderId));
    return ret.isSuccess();
  }

  /**
   * @param params Can optionally implement {@link TradeHistoryParamsTimeSpan}. All other TradeHistoryParams types will be ignored.
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    CryptsyTradeHistoryReturn tradeHistoryReturnData;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan timeSpan = (TradeHistoryParamsTimeSpan) params;
      tradeHistoryReturnData = super.getCryptsyTradeHistory(timeSpan.getStartTime(), timeSpan.getEndTime());
    } else {
      tradeHistoryReturnData = super.getCryptsyTradeHistory(null, null);
    }
    return CryptsyAdapters.adaptTradeHistory(tradeHistoryReturnData);
  }

  /**
   * Create {@link TradeHistoryParams} that supports {@link TradeHistoryParamsTimeSpan}.
   */

  @Override
  public org.knowm.xchange.service.polling.trade.params.TradeHistoryParams createTradeHistoryParams() {

    return new DefaultTradeHistoryParamsTimeSpan();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
