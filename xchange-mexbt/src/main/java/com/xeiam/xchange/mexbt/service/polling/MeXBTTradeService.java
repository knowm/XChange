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
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.mexbt.dto.MeXBTException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamOffset;
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
   * {@inheritDoc}
   */
  @Override
  public UserTrades getTradeHistory(Object... arguments)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    MeXBTTradeHistoryParams meXBTTradeHistoryParams = (MeXBTTradeHistoryParams) params;
    return adaptUserTrades(getTrades(toCurrencyPair(meXBTTradeHistoryParams.getCurrencyPair()), meXBTTradeHistoryParams.getOffset(),
        meXBTTradeHistoryParams.getCount()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new MeXBTTradeHistoryParams();
  }

  public static class MeXBTTradeHistoryParams extends DefaultTradeHistoryParamCurrencyPair
      implements TradeHistoryParamCurrencyPair, TradeHistoryParamOffset {

    private long startIndex;
    private int count;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOffset(Long offset) {
      this.startIndex = offset;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getOffset() {
      return startIndex;
    }

    public int getCount() {
      return count;
    }

    public void setCount(int count) {
      this.count = count;
    }

  }

}
