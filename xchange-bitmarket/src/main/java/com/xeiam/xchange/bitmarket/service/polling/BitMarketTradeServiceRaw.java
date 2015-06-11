package com.xeiam.xchange.bitmarket.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitmarket.BitMarketUtils;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketCancelResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketHistoryTradesResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketTradeResponse;
import com.xeiam.xchange.bitmarket.service.polling.params.BitMarketHistoryParams;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamOffset;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

import java.io.IOException;

/**
 * Created by krzysztoffonal on 25/05/15.
 */
public class BitMarketTradeServiceRaw extends BitMarketBasePollingService {
  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitMarketTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BitMarketOrdersResponse getBitMarketOpenOrders() throws IOException, ExchangeException {

    BitMarketOrdersResponse response = bitMarketAuthenticated.orders(apiKey, sign, exchange.getNonceFactory());

    if (!response.getSuccess()) {
      throw new ExchangeException(String.format("%d: %s", response.getError(), response.getErrorMsg()));
    }

    return response;
  }

  public BitMarketTradeResponse placeBitMarketOrder(LimitOrder order) throws IOException, ExchangeException {

    String market = order.getCurrencyPair().toString().replace("/", "");
    String type = order.getType() == Order.OrderType.ASK ? "buy" : "sell";

    BitMarketTradeResponse response = bitMarketAuthenticated.trade(apiKey, sign, exchange.getNonceFactory(),
        market, type, order.getTradableAmount(), order.getLimitPrice());

    if (!response.getSuccess()) {
      throw new ExchangeException(String.format("%d: %s", response.getError(), response.getErrorMsg()));
    }

    return response;
  }

  public BitMarketCancelResponse cancelBitMarketOrder(String id) throws IOException, ExchangeException {

    BitMarketCancelResponse response = bitMarketAuthenticated.cancel(apiKey, sign, exchange.getNonceFactory(), Long.parseLong(id));

    if (!response.getSuccess()) {
      throw new ExchangeException(String.format("%d: %s", response.getError(), response.getErrorMsg()));
    }

    return response;
  }

  public BitMarketHistoryTradesResponse getBitMarketTradeHistory(TradeHistoryParams params) throws IOException, ExchangeException {

    //default values
    String currencyPair = "BTCPLN";
    int count = 1000;
    long offset = 0;

    if (params instanceof TradeHistoryParamCurrencyPair) {
      currencyPair = BitMarketUtils.CurrencyPairToBitMarketCurrencyPair(((TradeHistoryParamCurrencyPair)params).getCurrencyPair());
    }

    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset)params).getOffset();
    }

    if (params instanceof BitMarketHistoryParams) {
      count = ((BitMarketHistoryParams)params).getCount();
    }

    BitMarketHistoryTradesResponse response = bitMarketAuthenticated.trades(apiKey,
        sign,
        exchange.getNonceFactory(),
        currencyPair,
        count,
        offset);

    if (!response.getSuccess()) {
      throw new ExchangeException(String.format("%d: %s", response.getError(), response.getErrorMsg()));
    }

    return response;
  }
}
