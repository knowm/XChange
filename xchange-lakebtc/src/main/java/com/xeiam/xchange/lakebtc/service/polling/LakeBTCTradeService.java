package com.xeiam.xchange.lakebtc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.lakebtc.dto.trade.LakeBTCCancelResponse;
import com.xeiam.xchange.lakebtc.dto.trade.LakeBTCOrderResponse;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamsTimeSpan;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

/**
 * Created by cristian.lucaci on 12/19/2014.
 */
public class LakeBTCTradeService extends LakeBTCTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public LakeBTCTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    final LakeBTCOrderResponse response = placeLakeBTCMarketOrder(marketOrder);
    return response.getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    final LakeBTCOrderResponse response = placeLakeBTCLimitOrder(limitOrder);
    return response.getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    final LakeBTCCancelResponse response = cancelLakeBTCOrder(orderId);
    return Boolean.valueOf(response.getResult());
  }

  @Override
  public UserTrades getTradeHistory(Object... arguments) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new DefaultTradeHistoryParamsTimeSpan();
  }

}
