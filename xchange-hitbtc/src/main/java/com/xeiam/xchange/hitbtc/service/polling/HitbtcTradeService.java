package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.meta.ExchangeMetaData;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOrder;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOwnTrade;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

public class HitbtcTradeService extends HitbtcTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public HitbtcTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    HitbtcOrder[] openOrdersRaw = getOpenOrdersRaw();
    return HitbtcAdapters.adaptOpenOrders(openOrdersRaw);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    HitbtcExecutionReport placeMarketOrderRaw = placeMarketOrderRaw(marketOrder);
    checkRejected(placeMarketOrderRaw);
    return placeMarketOrderRaw.getClientOrderId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    HitbtcExecutionReport placeLimitOrderRaw = placeLimitOrderRaw(limitOrder);
    checkRejected(placeLimitOrderRaw);
    return placeLimitOrderRaw.getClientOrderId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    HitbtcExecutionReportResponse cancelOrderRaw = cancelOrderRaw(orderId);
    return cancelOrderRaw.getCancelReject() == null && cancelOrderRaw.getExecutionReport() != null;
  }

  @Override
  public UserTrades getTradeHistory(Object... arguments) throws IOException {

    int startIndex = 0;
    int maxResults = 1000;
    String symbols = "BTCUSD";

    if (arguments.length == 3) {
      startIndex = (Integer) arguments[0];
      maxResults = (Integer) arguments[1];
      symbols = (String) arguments[2]; // comma separated
    }

    HitbtcOwnTrade[] tradeHistoryRaw = getTradeHistoryRaw(startIndex, maxResults, symbols);
    return HitbtcAdapters.adaptTradeHistory(tradeHistoryRaw, (ExchangeMetaData) exchange.getMetaData());
  }

  /**
   * Required parameters: {@link TradeHistoryParamPaging} {@link TradeHistoryParamCurrencyPair}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    TradeHistoryParamPaging pagingParams = (TradeHistoryParamPaging) params;
    Integer count = pagingParams.getPageLength();
    if (count == null) {
      count = 1000;
    }

    Integer pageNumber = pagingParams.getPageNumber();
    int offset = count * (pageNumber != null ? pageNumber : 0);

    CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    if (pair == null) {
      pair = CurrencyPair.BTC_USD;
    }

    HitbtcOwnTrade[] tradeHistoryRaw = getTradeHistoryRaw(offset, count, HitbtcAdapters.adaptCurrencyPair(pair));
    return HitbtcAdapters.adaptTradeHistory(tradeHistoryRaw, (ExchangeMetaData) exchange.getMetaData());
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new HitbtcTradeHistoryParams();
  }

  public static class HitbtcTradeHistoryParams extends DefaultTradeHistoryParamPaging implements TradeHistoryParamCurrencyPair {

    private CurrencyPair pair;

    public HitbtcTradeHistoryParams() {
    }

    public HitbtcTradeHistoryParams(Integer pageLength) {

      super(pageLength);
    }

    public HitbtcTradeHistoryParams(Integer pageNumber, Integer pageLength) {

      super(pageLength, pageNumber);
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
}
