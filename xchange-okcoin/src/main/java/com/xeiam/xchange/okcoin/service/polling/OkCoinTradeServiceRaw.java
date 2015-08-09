package com.xeiam.xchange.okcoin.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.okcoin.FuturesContract;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinFuturesOrderResult;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinOrderResult;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinPositionResult;
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

  public OkCoinTradeResult futuresTrade(String symbol, String type, String price, String amount, FuturesContract contract, int matchPrice,
      int leverRate) throws IOException {

    OkCoinTradeResult tradeResult = okCoin.futuresTrade(apikey, symbol, contract.getName(), type, price, amount, matchPrice, leverRate,
        signatureCreator);
    return returnOrThrow(tradeResult);
  }

  public OkCoinTradeResult futuresCancelOrder(long orderId, String symbol, FuturesContract contract) throws IOException {

    OkCoinTradeResult tradeResult = okCoin.futuresCancelOrder(apikey, orderId, symbol, contract.getName(), signatureCreator);
    return returnOrThrow(tradeResult);
  }

  public OkCoinFuturesOrderResult getFuturesOrder(long orderId, String symbol, String currentPage, String pageLength, FuturesContract contract)
      throws IOException {

    OkCoinFuturesOrderResult futuresOrder = okCoin.getFuturesOrder(apikey, orderId, symbol, "1", currentPage, pageLength, contract.getName(),
        signatureCreator);
    return returnOrThrow(futuresOrder);
  }

  public OkCoinPositionResult getFuturesPosition(String symbol, FuturesContract contract) throws IOException {
    OkCoinPositionResult futuresPositionsCross = okCoin.getFuturesPositionsCross(apikey, symbol, contract.getName(), signatureCreator);

    return returnOrThrow(futuresPositionsCross);
  }
}
