package com.xeiam.xchange.btcmarkets.service.polling;

import static com.xeiam.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btcmarkets.BTCMarketsAdapters;
import com.xeiam.xchange.btcmarkets.BTCMarketsExchange;
import com.xeiam.xchange.btcmarkets.dto.BTCMarketsException;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsOrder;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsOrders;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsPlaceOrderResponse;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsTradeHistory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsTimeSpan;

/**
 * @author Matija Mazi
 */
public class BTCMarketsTradeService extends BTCMarketsTradeServiceRaw implements PollingTradeService {

  private final CurrencyPair currencyPair;

  public BTCMarketsTradeService(Exchange exchange) {
    super(exchange);
    CurrencyPair cp = null;
    try {
      cp = (CurrencyPair) exchange.getExchangeSpecification().getExchangeSpecificParameters().get(BTCMarketsExchange.CURRENCY_PAIR);
    } catch (ClassCastException ignored) { }
    if (cp == null) {
      throw new IllegalArgumentException("The CURRENCY_PAIR exchange-specific parameter must be set in the exchange specification.");
    }
    currencyPair = cp;
  }

  @Override
  public String placeMarketOrder(MarketOrder order) throws IOException, BTCMarketsException {
    return placeOrder(order.getCurrencyPair(), order.getType(), order.getTradableAmount(), BigDecimal.ZERO, BTCMarketsOrder.Type.Market);
  }

  @Override
  public String placeLimitOrder(LimitOrder order) throws IOException, BTCMarketsException {
    return placeOrder(order.getCurrencyPair(), order.getType(), order.getTradableAmount(), order.getLimitPrice(), BTCMarketsOrder.Type.Limit);
  }

  private String placeOrder(
      CurrencyPair currencyPair,
      Order.OrderType orderSide,
      BigDecimal amount,
      BigDecimal price,
      BTCMarketsOrder.Type orderType
  ) throws IOException {
    BTCMarketsOrder.Side side = orderSide == BID ? BTCMarketsOrder.Side.Bid : BTCMarketsOrder.Side.Ask;
    final BTCMarketsPlaceOrderResponse orderResponse = placeBTCMarketsOrder(currencyPair, amount, price, side, orderType);
    return Long.toString(orderResponse.getId());
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, BTCMarketsException {
    BTCMarketsOrders openOrders = getBTCMarketsOpenOrders(currencyPair, 50, null);

    return BTCMarketsAdapters.adaptOpenOrders(openOrders);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, BTCMarketsException {
    return cancelBTCMarketsOrder(Long.parseLong(orderId)).getSuccess();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Integer limit = null;
    if (params instanceof TradeHistoryParamPaging) {
      final TradeHistoryParamPaging paging = (TradeHistoryParamPaging) params;
      limit = paging.getPageLength();
    }
    Date since = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      since = ((TradeHistoryParamsTimeSpan) params).getStartTime();
    }
    final BTCMarketsTradeHistory response = getBTCMarketsUserTransactions(currencyPair, limit, since);
    return BTCMarketsAdapters.adaptTradeHistory(response.getTrades(), currencyPair);
  }

  @Override
  public HistoryParams createTradeHistoryParams() {
    return new HistoryParams();
  }

  public static class HistoryParams implements TradeHistoryParamPaging, TradeHistoryParamsTimeSpan {
    private Integer limit = 100;
    private Date since;

    @Override
    public Integer getPageLength() {
      return limit;
    }

    @Override
    public void setPageLength(Integer pageLength) {
      this.limit = pageLength;
    }

    @Override
    public Integer getPageNumber() {
      throw new UnsupportedOperationException();
    }

    @Override
    public void setPageNumber(Integer pageNumber) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void setStartTime(Date startTime) {
      since = startTime;
    }

    @Override
    public Date getStartTime() {
      return since;
    }

    @Override
    public void setEndTime(Date endTime) {
      throw new UnsupportedOperationException();
    }

    @Override
    public Date getEndTime() {
      throw new UnsupportedOperationException();
    }
  }
}
