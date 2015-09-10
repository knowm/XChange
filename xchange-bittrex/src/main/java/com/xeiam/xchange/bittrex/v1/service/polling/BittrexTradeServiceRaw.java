package com.xeiam.xchange.bittrex.v1.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bittrex.v1.BittrexUtils;
import com.xeiam.xchange.bittrex.v1.dto.trade.BittrexCancelOrderResponse;
import com.xeiam.xchange.bittrex.v1.dto.trade.BittrexOpenOrder;
import com.xeiam.xchange.bittrex.v1.dto.trade.BittrexOpenOrdersResponse;
import com.xeiam.xchange.bittrex.v1.dto.trade.BittrexTradeHistoryResponse;
import com.xeiam.xchange.bittrex.v1.dto.trade.BittrexTradeResponse;
import com.xeiam.xchange.bittrex.v1.dto.trade.BittrexUserTrade;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.exceptions.ExchangeException;

public class BittrexTradeServiceRaw extends BittrexBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public String placeBittrexMarketOrder(MarketOrder marketOrder) throws IOException {

    String pair = BittrexUtils.toPairString(marketOrder.getCurrencyPair());

    if (marketOrder.getType() == OrderType.BID) {

      BittrexTradeResponse response = bittrexAuthenticated.buymarket(apiKey, signatureCreator, exchange.getNonceFactory(), pair,
          marketOrder.getTradableAmount().toPlainString());

      if (response.getSuccess()) {
        return response.getResult().getUuid();
      } else {
        throw new ExchangeException(response.getMessage());
      }

    } else {

      BittrexTradeResponse response = bittrexAuthenticated.sellmarket(apiKey, signatureCreator, exchange.getNonceFactory(), pair,
          marketOrder.getTradableAmount().toPlainString());

      if (response.getSuccess()) {
        return response.getResult().getUuid();
      } else {
        throw new ExchangeException(response.getMessage());
      }

    }
  }

  public String placeBittrexLimitOrder(LimitOrder limitOrder) throws IOException {

    String pair = BittrexUtils.toPairString(limitOrder.getCurrencyPair());

    if (limitOrder.getType() == OrderType.BID) {
      BittrexTradeResponse response = bittrexAuthenticated.buylimit(apiKey, signatureCreator, exchange.getNonceFactory(), pair,
          limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString());

      if (response.getSuccess()) {
        return response.getResult().getUuid();
      } else {
        throw new ExchangeException(response.getMessage());
      }

    } else {
      BittrexTradeResponse response = bittrexAuthenticated.selllimit(apiKey, signatureCreator, exchange.getNonceFactory(), pair,
          limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString());

      if (response.getSuccess()) {
        return response.getResult().getUuid();
      } else {
        throw new ExchangeException(response.getMessage());
      }
    }
  }

  public boolean cancelBittrexLimitOrder(String uuid) throws IOException {

    BittrexCancelOrderResponse response = bittrexAuthenticated.cancel(apiKey, signatureCreator, exchange.getNonceFactory(), uuid);

    if (response.getSuccess()) {
      return true;
    } else {
      throw new ExchangeException(response.getMessage());
    }

  }

  public List<BittrexOpenOrder> getBittrexOpenOrders() throws IOException {

    BittrexOpenOrdersResponse response = bittrexAuthenticated.openorders(apiKey, signatureCreator, exchange.getNonceFactory());

    if (response.getSuccess()) {
      return response.getBittrexOpenOrders();
    } else {
      throw new ExchangeException(response.getMessage());
    }

  }

  public List<BittrexUserTrade> getBittrexTradeHistory() throws IOException {

    BittrexTradeHistoryResponse response = bittrexAuthenticated.getorderhistory(apiKey, signatureCreator, exchange.getNonceFactory());

    if (response.getSuccess()) {
      return response.getResult();
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }
}