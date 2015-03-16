package com.xeiam.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptsy.CryptsyAdapters;
import com.xeiam.xchange.cryptsy.CryptsyCurrencyUtils;
import com.xeiam.xchange.cryptsy.dto.CryptsyOrder.CryptsyOrderType;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyCancelOrderReturn;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyOpenOrdersReturn;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyPlaceOrderReturn;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyTradeHistoryReturn;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamsTimeSpan;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsTimeSpan;

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
   * @param arguments Vararg list of optional (nullable) arguments: (Long) arguments[0] Number of transactions to return (String) arguments[1]
   *        TradableIdentifier (String) arguments[2] TransactionCurrency (Long) arguments[3] Starting ID
   * @return Trades object
   * @throws IOException
   */
  @Override
  public UserTrades getTradeHistory(Object... arguments) throws IOException, ExchangeException {

    Date startDate = new Date(0); // default value
    Date endDate = new Date(); // default value
    if (arguments.length == 2) {
      startDate = (Date) arguments[0];
      endDate = (Date) arguments[1];
    }

    CryptsyTradeHistoryReturn tradeHistoryReturnData = super.getCryptsyTradeHistory(startDate, endDate);

    return CryptsyAdapters.adaptTradeHistory(tradeHistoryReturnData);
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
  public com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams createTradeHistoryParams() {

    return new DefaultTradeHistoryParamsTimeSpan();
  }

}
