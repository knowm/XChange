package com.xeiam.xchange.okcoin.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.okcoin.FuturesContract;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinOrderResult;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinTradeResult;

public class OkCoinTradeServiceRaw extends OKCoinBaseTradePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected OkCoinTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public OkCoinTradeResult trade(String symbol, String type, String rate, String amount) throws IOException {

    OkCoinTradeResult tradeResult = okCoin.trade(apikey, symbol, type, rate, amount, signatureCreator);
    return returnOrThrow(tradeResult);
  }

  public OkCoinTradeResult cancelOrder(long orderId, String symbol) throws IOException {

    OkCoinTradeResult tradeResult = okCoin.cancelOrder(apikey, orderId, symbol, signatureCreator);
    return returnOrThrow(tradeResult);
  }

  public OkCoinOrderResult getOrder(long orderId, String symbol) throws IOException {

    OkCoinOrderResult orderResult = okCoin.getOrder(apikey, orderId, symbol, signatureCreator);
    return returnOrThrow(orderResult);
  }

  public OkCoinOrderResult getOrderHistory(String symbol, String status, String currentPage, String pageLength) throws IOException {

    OkCoinOrderResult orderResult = okCoin.getOrderHistory(apikey, symbol, status, currentPage, pageLength, signatureCreator);
    return returnOrThrow(orderResult);
  }

  /** OkCoin.com Futures API **/

  public OkCoinTradeResult futuresTrade(String symbol, String type, String price, String amount, FuturesContract prompt, int matchPrice)
      throws IOException {

    OkCoinTradeResult tradeResult = okCoin.futuresTrade(apikey, symbol, prompt.getName(), type, price, amount, matchPrice, signatureCreator);
    return returnOrThrow(tradeResult);
  }

  public OkCoinTradeResult futuresCancelOrder(long orderId, String symbol, FuturesContract prompt) throws IOException {

    OkCoinTradeResult tradeResult = okCoin.futuresCancelOrder(apikey, orderId, symbol, prompt.getName(), signatureCreator);
    return returnOrThrow(tradeResult);
  }

  public OkCoinOrderResult getFuturesOrder(long orderId, String symbol, String currentPage, String pageLength, FuturesContract prompt)
      throws IOException {

    OkCoinOrderResult orderResult = okCoin.getFuturesOrder(apikey, orderId, symbol, "0", currentPage, pageLength, prompt.getName(), signatureCreator);
    return returnOrThrow(orderResult);
  }
}
