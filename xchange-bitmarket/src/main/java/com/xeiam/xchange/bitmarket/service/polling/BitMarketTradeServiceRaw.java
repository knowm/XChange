package com.xeiam.xchange.bitmarket.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketCancelResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketTradeResponse;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.exceptions.ExchangeException;

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

    BitMarketCancelResponse response = bitMarketAuthenticated.cancel(apiKey, sign, exchange.getNonceFactory(), Long.getLong(id));

    if (!response.getSuccess()) {
      throw new ExchangeException(String.format("%d: %s", response.getError(), response.getErrorMsg()));
    }

    return response;
  }
}
