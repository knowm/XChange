package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParamCount;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParamOffset;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParams;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOrder;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOwnTrade;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class HitbtcTradeService extends HitbtcTradeServiceRaw implements PollingTradeService {

  public HitbtcTradeService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification, nonceFactory);
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcOrder[] openOrdersRaw = getOpenOrdersRaw();
    return HitbtcAdapters.adaptOpenOrders(openOrdersRaw);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcExecutionReport placeMarketOrderRaw = placeMarketOrderRaw(marketOrder);
    checkRejected(placeMarketOrderRaw);
    return placeMarketOrderRaw.getClientOrderId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcExecutionReport placeLimitOrderRaw = placeLimitOrderRaw(limitOrder);
    checkRejected(placeLimitOrderRaw);
    return placeLimitOrderRaw.getClientOrderId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcExecutionReportResponse cancelOrderRaw = cancelOrderRaw(orderId);
    return cancelOrderRaw.getCancelReject() == null && cancelOrderRaw.getExecutionReport() != null;
  }

  @Override
  public UserTrades getTradeHistory(Object... arguments) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    int startIndex = 0;
    int maxResults = 1000;
    String symbols = "BTCUSD";

    if (arguments.length == 3) {
      startIndex = (Integer) arguments[0];
      maxResults = (Integer) arguments[1];
      symbols = (String) arguments[2]; // comma separated
    }

    HitbtcOwnTrade[] tradeHistoryRaw = getTradeHistoryRaw(startIndex, maxResults, symbols);
    return HitbtcAdapters.adaptTradeHistory(tradeHistoryRaw);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    Long _offset = ((TradeHistoryParamOffset) params).getOffset();
    int offset = _offset != null ? (int) (long) _offset : 0;

    Integer count = ((TradeHistoryParamCount) params).getCount();
    if (count == null)
      count = 1000;

    CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    if (pair == null)
      pair = CurrencyPair.BTC_USD;

    HitbtcOwnTrade[] tradeHistoryRaw = getTradeHistoryRaw(offset, count, HitbtcAdapters.adaptCurrencyPair(pair));
    return HitbtcAdapters.adaptTradeHistory(tradeHistoryRaw);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new HitbtcTradeHistoryParams();
  }

  public static class HitbtcTradeHistoryParams implements TradeHistoryParamCount, TradeHistoryParamOffset, TradeHistoryParamCurrencyPair {

    private Integer count;
    private Long offset;
    private CurrencyPair pair;

    public HitbtcTradeHistoryParams() {
    }

    public HitbtcTradeHistoryParams(Integer count) {
      this.count = count;
    }

    public HitbtcTradeHistoryParams(Long offset, Integer count) {
      this.count = count;
      this.offset = offset;
    }

    @Override
    public void setCount(Integer count) {
      this.count = count;
    }

    @Override
    public Integer getCount() {
      return count;
    }

    @Override
    public void setOffset(Long offset) {
      this.offset = offset;
    }

    @Override
    public Long getOffset() {
      return offset;
    }

    @Override
    public void setCurrencyPair(CurrencyPair pair) {

      this.pair = pair;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return pair;
    }
  }

  private void checkRejected(HitbtcExecutionReport executionReport) {

    if ("rejected".equals(executionReport.getExecReportType()))
      throw new ExchangeException("Order rejected, " + executionReport.getOrderRejectReason());
  }

}
