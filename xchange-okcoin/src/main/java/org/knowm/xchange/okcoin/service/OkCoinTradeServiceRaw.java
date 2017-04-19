package org.knowm.xchange.okcoin.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.okcoin.FuturesContract;
import org.knowm.xchange.okcoin.dto.trade.OkCoinFuturesOrderResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinFuturesTradeHistoryResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinOrderResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinPositionResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinTradeResult;

public class OkCoinTradeServiceRaw extends OKCoinBaseTradeService {

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

  /**
   * OkCoin.com Futures API
   **/

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

  public OkCoinFuturesOrderResult getFuturesOrder(long orderId, String symbol, String currentPage, String pageLength,
      FuturesContract contract) throws IOException {

    OkCoinFuturesOrderResult futuresOrder = okCoin.getFuturesOrder(apikey, orderId, symbol, "1", currentPage, pageLength, contract.getName(),
        signatureCreator);
    return returnOrThrow(futuresOrder);
  }

  public OkCoinFuturesOrderResult getFuturesFilledOrder(long orderId, String symbol, String currentPage, String pageLength,
      FuturesContract contract) throws IOException {

    OkCoinFuturesOrderResult futuresOrder = okCoin.getFuturesOrder(apikey, orderId, symbol, "2", currentPage, pageLength, contract.getName(),
        signatureCreator);
    return returnOrThrow(futuresOrder);
  }

  public OkCoinFuturesOrderResult getFuturesOrders(String orderIds, String symbol, FuturesContract contract) throws IOException {

    OkCoinFuturesOrderResult futuresOrder = okCoin.getFuturesOrders(apikey, orderIds, symbol, contract.getName(), signatureCreator);
    return returnOrThrow(futuresOrder);
  }

  public OkCoinFuturesTradeHistoryResult[] getFuturesTradesHistory(String symbol, long since, String date) throws IOException {

    OkCoinFuturesTradeHistoryResult[] futuresHistory = okCoin.getFuturesTradeHistory(apikey, since, symbol, date, signatureCreator);
    return (futuresHistory);
  }

  public OkCoinPositionResult getFuturesPosition(String symbol, FuturesContract contract) throws IOException {
    OkCoinPositionResult futuresPositionsCross = okCoin.getFuturesPositionsCross(apikey, symbol, contract.getName(), signatureCreator);

    return returnOrThrow(futuresPositionsCross);
  }
}
