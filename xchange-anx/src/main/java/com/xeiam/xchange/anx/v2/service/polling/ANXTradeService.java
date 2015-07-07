package com.xeiam.xchange.anx.v2.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.anx.ANXUtils;
import com.xeiam.xchange.anx.v2.ANXAdapters;
import com.xeiam.xchange.anx.v2.ANXExchange;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXTradeResultWrapper;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamsTimeSpan;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsTimeSpan;
import com.xeiam.xchange.utils.Assert;
import com.xeiam.xchange.utils.DateUtils;

/**
 * @author timmolter
 */
public class ANXTradeService extends ANXTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param baseExchange
   */
  public ANXTradeService(BaseExchange baseExchange) {

    super(baseExchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    return new OpenOrders(ANXAdapters.adaptOrders(getANXOpenOrders()));
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    return placeANXMarketOrder(marketOrder).getDataString();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    // Validation
    Assert.notNull(limitOrder.getLimitPrice(), "getLimitPrice() cannot be null");
    Assert.notNull(limitOrder.getTradableAmount(), "getTradableAmount() cannot be null");

    if (limitOrder.getTradableAmount().scale() > 8) {
      throw new IllegalArgumentException("tradableAmount scale exceeds max");
    }

    if (limitOrder.getLimitPrice().scale() > ANXUtils.getMaxPriceScale(limitOrder.getCurrencyPair())) {
      throw new IllegalArgumentException("price scale exceeds max");
    }

    String type = limitOrder.getType().equals(OrderType.BID) ? "bid" : "ask";

    BigDecimal amount = limitOrder.getTradableAmount();
    BigDecimal price = limitOrder.getLimitPrice();

    return placeANXLimitOrder(limitOrder.getCurrencyPair(), type, amount, price).getDataString();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    Assert.notNull(orderId, "orderId cannot be null");

    return cancelANXOrder(orderId, "BTC", "EUR").getResult().equals("success");
  }

  /**
   * @param args Accept zero or 2 parameters, both are unix time: Long from, Long to
   */
  @Override
  public UserTrades getTradeHistory(Object... args) throws IOException {

    Long from = null;
    Long to = null;

    if (args.length > 0) {
      from = (Long) args[0];
    }
    if (args.length > 1) {
      to = (Long) args[1];
    }

    return getTradeHistory(from, to);
  }

  private UserTrades getTradeHistory(Long from, Long to) throws IOException {
    ANXTradeResultWrapper rawTrades = getExecutedANXTrades(from, to);
    String error = rawTrades.getError();

    if (error != null) {
      throw new IllegalStateException(error);
    }

    return ANXAdapters.adaptUserTrades(rawTrades.getAnxTradeResults(), ((ANXExchange) exchange).getANXMetaData());
  }

  /**
   * Suported parameter types: {@link TradeHistoryParamsTimeSpan}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws ExchangeException, IOException {

    Long from = null;
    Long to = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan p = (TradeHistoryParamsTimeSpan) params;
      from = DateUtils.toMillisNullSafe(p.getStartTime());
      to = DateUtils.toMillisNullSafe(p.getEndTime());
    }
    return getTradeHistory(from, to);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new DefaultTradeHistoryParamsTimeSpan();
  }
}
