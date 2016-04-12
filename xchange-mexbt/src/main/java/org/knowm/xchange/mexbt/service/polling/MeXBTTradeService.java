package org.knowm.xchange.mexbt.service.polling;

import static org.knowm.xchange.mexbt.MeXBTAdapters.adaptOpenOrders;
import static org.knowm.xchange.mexbt.MeXBTAdapters.adaptUserTrades;
import static org.knowm.xchange.mexbt.MeXBTAdapters.toCurrencyPair;
import static org.knowm.xchange.mexbt.MeXBTAdapters.toSide;

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
import org.knowm.xchange.mexbt.dto.MeXBTException;
import org.knowm.xchange.service.polling.trade.PollingTradeService;
import org.knowm.xchange.service.polling.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParams;

public class MeXBTTradeService extends MeXBTTradeServiceRaw implements PollingTradeService {

  public MeXBTTradeService(Exchange exchange) {
    super(exchange);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, IOException {
    return adaptOpenOrders(getMeXBTOpenOrders());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, IOException {
    long serverOrderId = createOrder(toCurrencyPair(marketOrder.getCurrencyPair()), toSide(marketOrder.getType()), 1, marketOrder.getTradableAmount(),
        null).getServerOrderId();
    return String.valueOf(serverOrderId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, IOException {
    long serverOrderId = createOrder(toCurrencyPair(limitOrder.getCurrencyPair()), toSide(limitOrder.getType()), 0, limitOrder.getTradableAmount(),
        limitOrder.getLimitPrice()).getServerOrderId();
    return String.valueOf(serverOrderId);
  }

  @Override
  public Collection<Order> getOrder(String... orderIds)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean cancelOrder(String orderId) throws ExchangeException, IOException {
    boolean cancelled = false;
    for (CurrencyPair currencyPair : getExchangeSymbols()) {
      try {
        cancelOrder(toCurrencyPair(currencyPair), orderId);
        cancelled = true;
        break;
      } catch (MeXBTException e) {
      }
    }
    return cancelled;
  }

  /**
   * @param params Required types: {@link TradeHistoryParamCurrencyPair}, {@link TradeHistoryParamPaging}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();

    TradeHistoryParamPaging paramPaging = (TradeHistoryParamPaging) params;
    int count = paramPaging.getPageLength();
    Long offset = (long) (paramPaging.getPageLength() * count);

    return adaptUserTrades(getTrades(toCurrencyPair(pair), offset, count));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new MeXBTTradeHistoryParams();
  }

  public static class MeXBTTradeHistoryParams extends DefaultTradeHistoryParamCurrencyPair
      implements TradeHistoryParamCurrencyPair, TradeHistoryParamPaging {

    private Integer count;
    private Integer pageLength;

    @Override
    public void setPageLength(Integer pageLength) {
      this.pageLength = pageLength;
    }

    @Override
    public Integer getPageLength() {
      return pageLength;
    }

    @Override
    public void setPageNumber(Integer pageNumber) {
      count = pageNumber;
    }

    @Override
    public Integer getPageNumber() {
      return count;
    }
  }

}
