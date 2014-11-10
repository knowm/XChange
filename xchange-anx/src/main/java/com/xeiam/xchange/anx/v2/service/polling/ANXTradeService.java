package com.xeiam.xchange.anx.v2.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.anx.ANXUtils;
import com.xeiam.xchange.anx.v2.ANXAdapters;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXTradeResultWrapper;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.*;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.polling.opt.SinceTradeHistoryProvider;
import com.xeiam.xchange.utils.Assert;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author timmolter
 */
public class ANXTradeService extends ANXTradeServiceRaw implements PollingTradeService, SinceTradeHistoryProvider {

  /**
   * Constructor
   *
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public ANXTradeService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification, nonceFactory);
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
    if (args.length > 0)
      from = (Long) args[0];
    if (args.length > 1)
      to = (Long) args[1];

    return getTradeHistory(from, to);
  }

  @Override
  public UserTrades getTradeHistory(UserTrade last) throws ExchangeException, IOException {
    return getTradeHistory(last.getTimestamp().getTime(), null);
  }

  public UserTrades getTradeHistory(Long fromTime, Long toTime) throws ExchangeException, IOException {
    ANXTradeResultWrapper rawTrades = getExecutedANXTrades(fromTime, toTime);
    String error = rawTrades.getError();

    if (error != null)
      throw new IllegalStateException(error);

    return ANXAdapters.adaptUserTrades(rawTrades.getAnxTradeResults());
  }
}
