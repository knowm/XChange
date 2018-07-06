package org.knowm.xchange.paymium.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.paymium.dto.account.PaymiumOrder;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymiumTradeService extends PaymiumTradeServiceRaw implements TradeService {

  public static class PaymiumHistoryParams implements TradeHistoryParamOffset, TradeHistoryParamLimit {

    private Long offset;

    private Integer limit;

    public PaymiumHistoryParams() {
      this.offset = 0L;
      this.limit = 20;
    }

    public PaymiumHistoryParams(Long offset, Integer limit) {
      this.offset = offset;
      this.limit = limit;
    }

    @Override
    public Long getOffset() {
      return offset;
    }

    @Override
    public void setOffset(final Long offset) {
      this.offset = offset;
    }

    @Override
    public Integer getLimit() {
      return limit;
    }

    public void setLimit(Integer limit) {
      this.limit = limit;
    }
  }

  /**
   * Constructor
   *
   * @param exchange
   */
  public PaymiumTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    Long offset = null;
    Integer limit = null;

    if (params instanceof TradeHistoryParamOffset) {
      final TradeHistoryParamOffset historyParamOffset = (TradeHistoryParamOffset) params;
      offset = historyParamOffset.getOffset();
    }

    if (params instanceof TradeHistoryParamLimit) {
      final TradeHistoryParamLimit historyParamLimit = (TradeHistoryParamLimit) params;
      limit = historyParamLimit.getLimit();
    }

    List <UserTrade> userTrades = new ArrayList();
    List<PaymiumOrder> orders = getPaymiumOrders(offset, limit);

    for (PaymiumOrder order : orders) {
      Order.OrderType orderType = null;
      Currency currencyFee = null;
      BigDecimal fee = null;

      switch (order.getDirection()) {
        case "buy":
          orderType = Order.OrderType.ASK;
          currencyFee = Currency.BTC;
          fee = order.getBtcFee();
          break;
        case "sell":
          orderType = Order.OrderType.BID;
          currencyFee = Currency.EUR;
          fee = order.getCurrencyFee();
          break;
      }

      UserTrade userTrade =
          new UserTrade(
              orderType,
              order.getTradedCurrency(),
              CurrencyPair.BTC_EUR,
              order.getPrice(),
              order.getUpdatedAt(),
              order.getUuid(),
              order.getUuid(),
              fee,
              currencyFee);

      userTrades.add(userTrade);
      System.out.println(userTrade);
    }

    return new UserTrades(userTrades, Trades.TradeSortType.SortByTimestamp);
  }
}
