package org.knowm.xchange.itbit.v1.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
import org.knowm.xchange.itbit.v1.ItBitAdapters;
import org.knowm.xchange.itbit.v1.dto.trade.ItBitOrder;
import org.knowm.xchange.itbit.v1.dto.trade.ItBitTradeHistory;
import org.knowm.xchange.service.polling.trade.PollingTradeService;
import org.knowm.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParamTransactionId;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParamsTimeSpan;

public class ItBitTradeService extends ItBitTradeServiceRaw implements PollingTradeService {

  public ItBitTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    List<ItBitOrder> orders = new ArrayList<>();
    for (CurrencyPair currencyPair : getExchangeSymbols()) {
      orders.addAll(Arrays.asList(getItBitOpenOrders(currencyPair)));
    }
    ItBitOrder[] empty = {};
    return ItBitAdapters.adaptPrivateOrders(orders.isEmpty() ? empty : Arrays.copyOf(orders.toArray(), orders.size(), ItBitOrder[].class));
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    return placeItBitLimitOrder(limitOrder).getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    cancelItBitOrder(orderId);
    return true;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    Integer page = ((TradeHistoryParamPaging) params).getPageNumber();
    if (page != null) {
      ++page;
    }

    ItBitTradeHistory userTradeHistory = getUserTradeHistory(
        ((TradeHistoryParamTransactionId) params).getTransactionId(),
        page,
        ((TradeHistoryParamPaging) params).getPageLength(),
        ((TradeHistoryParamsTimeSpan) params).getStartTime(),
        ((TradeHistoryParamsTimeSpan) params).getEndTime()
    );
    return ItBitAdapters.adaptTradeHistory(userTradeHistory);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new ItBitTradeHistoryParams(50, 0, null, null, null);
  }

  public static class ItBitTradeHistoryParams extends DefaultTradeHistoryParamPaging
      implements TradeHistoryParamsTimeSpan, TradeHistoryParamTransactionId, TradeHistoryParamPaging {

    private String txId;
    private Date startTime;
    private Date endTime;

    public ItBitTradeHistoryParams(Integer pageLength, Integer pageNumber, String txId, Date startTime, Date endTime) {
      super(pageLength, pageNumber);
      this.txId = txId;
      this.startTime = startTime;
      this.endTime = endTime;
    }

    @Override public void setTransactionId(String txId) { this.txId = txId; }
    @Override public String getTransactionId() { return txId; }
    @Override public void setStartTime(Date startTime) { this.startTime = startTime; }
    @Override public Date getStartTime() { return startTime; }
    @Override public void setEndTime(Date endTime) { this.endTime = endTime; }
    @Override public Date getEndTime() { return endTime; }
  }

  @Override
  public Collection<Order> getOrder(String... orderIds)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
