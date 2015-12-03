package com.xeiam.xchange.mexbt.service.polling;

import static com.xeiam.xchange.mexbt.MeXBTAdapters.adaptOpenOrders;
import static com.xeiam.xchange.mexbt.MeXBTAdapters.adaptUserTrades;
import static com.xeiam.xchange.mexbt.MeXBTAdapters.toCurrencyPair;
import static com.xeiam.xchange.mexbt.MeXBTAdapters.toSide;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.mexbt.dto.MeXBTException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

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

  public static class MeXBTTradeHistoryParams extends DefaultTradeHistoryParamCurrencyPair implements TradeHistoryParamCurrencyPair, TradeHistoryParamPaging {

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
