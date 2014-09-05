package com.xeiam.xchange.bittrex.v1.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bittrex.v1.BittrexAuthenticated;
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

public class BittrexTradeServiceRaw extends BittrexBasePollingService<BittrexAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BittrexTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BittrexAuthenticated.class, exchangeSpecification);
  }

  public String placeBittrexMarketOrder(MarketOrder marketOrder) throws IOException {

    String pair = BittrexUtils.toPairString(marketOrder.getCurrencyPair());

    if (marketOrder.getType() == OrderType.BID) {

      BittrexTradeResponse response = bittrex.buymarket(apiKey, signatureCreator, String.valueOf(nextNonce()), pair, marketOrder.getTradableAmount().toPlainString());

      if (response.getSuccess()) {
        return response.getResult().getUuid();
      }
      else {
        throw new ExchangeException("Bittrex returned an error: " + response.getMessage());
      }

    }
    else {

      BittrexTradeResponse response = bittrex.sellmarket(apiKey, signatureCreator, String.valueOf(nextNonce()), pair, marketOrder.getTradableAmount().toPlainString());

      if (response.getSuccess()) {
        return response.getResult().getUuid();
      }
      else {
        throw new ExchangeException("Bittrex returned an error: " + response.getMessage());
      }

    }
  }

  public String placeBittrexLimitOrder(LimitOrder limitOrder) throws IOException {

    String pair = BittrexUtils.toPairString(limitOrder.getCurrencyPair());

    if (limitOrder.getType() == OrderType.BID) {
      BittrexTradeResponse response =
          bittrex.buylimit(apiKey, signatureCreator, String.valueOf(nextNonce()), pair, limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString());

      if (response.getSuccess()) {
        return response.getResult().getUuid();
      }
      else {
        throw new ExchangeException("Bittrex returned an error: " + response.getMessage());
      }

    }
    else {
      BittrexTradeResponse response =
          bittrex.selllimit(apiKey, signatureCreator, String.valueOf(nextNonce()), pair, limitOrder.getTradableAmount().toPlainString(), limitOrder.getLimitPrice().toPlainString());

      if (response.getSuccess()) {
        return response.getResult().getUuid();
      }
      else {
        throw new ExchangeException("Bittrex returned an error: " + response.getMessage());
      }
    }
  }

  public boolean cancelBittrexLimitOrder(String uuid) throws IOException {

    BittrexCancelOrderResponse response = bittrex.cancel(apiKey, signatureCreator, String.valueOf(nextNonce()), uuid);

    if (response.getSuccess()) {
      return true;
    }
    else {
      throw new ExchangeException("Bittrex returned an error: " + response.getMessage());
    }

  }

  public List<BittrexOpenOrder> getBittrexOpenOrders() throws IOException {

    BittrexOpenOrdersResponse response = bittrex.openorders(apiKey, signatureCreator, String.valueOf(nextNonce()));

    if (response.getSuccess()) {
      return response.getBittrexOpenOrders();
    }
    else {
      throw new ExchangeException("Bittrex returned an error: " + response.getMessage());
    }

  }

  public List<BittrexUserTrade> getBittrexTradeHistory() throws IOException {

    BittrexTradeHistoryResponse response = bittrex.getorderhistory(apiKey, signatureCreator, String.valueOf(nextNonce()));

    if (response.getSuccess()) {
      return response.getResult();
    }
    else {
      throw new ExchangeException("Bittrex returned an error: " + "Bittrex returned an error: " + response.getMessage());
    }
  }
}