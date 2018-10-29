package org.knowm.xchange.okcoin.service;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.okcoin.FuturesContract;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.trade.OkCoinFuturesOrderResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinFuturesTradeHistoryResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinOrderResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinPositionResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinPriceLimit;
import org.knowm.xchange.okcoin.dto.trade.OkCoinTradeResult;
import org.knowm.xchange.okcoin.dto.trade.result.OkCoinBatchTradeResult;
import org.knowm.xchange.okcoin.dto.trade.result.OkCoinFutureExplosiveResult;
import org.knowm.xchange.okcoin.dto.trade.result.OkCoinMoreTradeResult;

public class OkCoinTradeServiceRaw extends OKCoinBaseTradeService {

  protected static final String BATCH_DELIMITER = ",";

  /**
   * Constructor
   *
   * @param exchange
   */
  protected OkCoinTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  /**
   * 下单交易
   *
   * @param symbol
   * @param type
   * @param price
   * @param amount (只能是整数)
   * @return
   * @throws IOException
   */
  public OkCoinTradeResult trade(String symbol, String type, String price, String amount)
      throws IOException {
    OkCoinTradeResult tradeResult =
        okCoin.trade(apikey, symbol, type, price, amount, signatureCreator());
    return returnOrThrow(tradeResult);
  }

  public OkCoinTradeResult placeMarketOrderBuy(String symbol, String type, String price)
      throws IOException {
    OkCoinTradeResult tradeResult =
        okCoin.placeMarketOrderBuy(apikey, symbol, type, price, signatureCreator());
    return returnOrThrow(tradeResult);
  }

  public OkCoinTradeResult placeMarketOrderSell(String symbol, String type, String amount)
      throws IOException {
    OkCoinTradeResult tradeResult =
        okCoin.placeMarketOrderSell(apikey, symbol, type, amount, signatureCreator());
    return returnOrThrow(tradeResult);
  }

  /**
   * 批量下单
   *
   * @param symbol
   * @param type 限价单(buy/sell)
   * @param ordersData "[{price:3,amount:5,type:'sell'},{price:3,amount:3,type:'buy'}]"
   *     最终买卖类型由orders_data 中type 为准，如orders_data不设定type 则由上面type设置为准。 若，上面type没有设置，orderData
   *     必须设置type
   * @return
   * @throws IOException
   */
  public OkCoinMoreTradeResult batchTrade(String symbol, String type, String ordersData)
      throws IOException {
    OkCoinMoreTradeResult tradeResult =
        okCoin.batchTrade(apikey, symbol, type, ordersData, signatureCreator());
    return returnOrThrow(tradeResult);
  }

  /**
   * 单笔订单取消
   *
   * @param orderId
   * @param symbol
   * @return
   * @throws IOException
   */
  public OkCoinTradeResult cancelOrder(long orderId, String symbol) throws IOException {

    OkCoinTradeResult tradeResult = okCoin.cancelOrder(apikey, orderId, symbol, signatureCreator());
    return returnOrThrow(tradeResult);
  }

  /**
   * 多笔订单取消 一次最多取消三个订单
   *
   * @param orderIds
   * @param symbol
   * @return
   * @throws IOException
   */
  public OkCoinBatchTradeResult cancelUpToThreeOrders(Set<Long> orderIds, String symbol)
      throws IOException {
    String ids =
        orderIds.stream().map(Object::toString).collect(Collectors.joining(BATCH_DELIMITER));
    return okCoin.cancelOrders(apikey, ids, symbol, signatureCreator());
  }

  /**
   * 获取用户的未完成的订单信息
   *
   * @param symbol
   * @return
   * @throws IOException
   */
  public OkCoinOrderResult getOrder(String symbol) throws IOException {
    return getOrder(-1, symbol);
  }

  /**
   * 获取用户的订单信息
   *
   * @param orderId
   * @param symbol
   * @return
   * @throws IOException
   */
  public OkCoinOrderResult getOrder(long orderId, String symbol) throws IOException {

    OkCoinOrderResult orderResult = okCoin.getOrder(apikey, orderId, symbol, signatureCreator());
    return returnOrThrow(orderResult);
  }

  /**
   * 批量获取用户订单
   *
   * @param symbol
   * @param type 查询类型 0:未完成的订单 1:已经完成的订单
   * @param orderIds
   * @return
   */
  public OkCoinOrderResult getOrder(String symbol, Integer type, String orderIds)
      throws IOException {

    OkCoinOrderResult orderResult =
        okCoin.getOrders(apikey, type, orderIds, symbol, signatureCreator());
    return returnOrThrow(orderResult);
  }

  /**
   * 获取历史订单信息，只返回最近两天的信息
   *
   * @param symbol
   * @param status
   * @param currentPage
   * @param pageLength
   * @return
   * @throws IOException
   */
  public OkCoinOrderResult getOrderHistory(
      String symbol, String status, String currentPage, String pageLength) throws IOException {

    OkCoinOrderResult orderResult =
        okCoin.getOrderHistory(apikey, symbol, status, currentPage, pageLength, signatureCreator());
    return returnOrThrow(orderResult);
  }

  /** OkCoin.com Futures API */
  /**
   * 合约下单
   *
   * @param symbol
   * @param type
   * @param price
   * @param amount
   * @param contract
   * @param matchPrice
   * @param leverRate
   * @return
   * @throws IOException
   */
  public OkCoinTradeResult futuresTrade(
      String symbol,
      String type,
      String price,
      String amount,
      FuturesContract contract,
      int matchPrice,
      int leverRate)
      throws IOException {

    OkCoinTradeResult tradeResult =
        okCoin.futuresTrade(
            apikey,
            symbol,
            contract.getName(),
            type,
            price,
            amount,
            matchPrice,
            leverRate,
            signatureCreator());
    return returnOrThrow(tradeResult);
  }

  /**
   * 取消合约订单(单个取消，多个取消没有实现处理)
   *
   * @param orderId
   * @param symbol
   * @param contract
   * @return
   * @throws IOException
   */
  public OkCoinTradeResult futuresCancelOrder(long orderId, String symbol, FuturesContract contract)
      throws IOException {

    OkCoinTradeResult tradeResult =
        okCoin.futuresCancelOrder(
            apikey, String.valueOf(orderId), symbol, contract.getName(), signatureCreator());
    return returnOrThrow(tradeResult);
  }

  /**
   * 获取合约订单信息
   *
   * @param orderId
   * @param symbol
   * @param currentPage
   * @param pageLength
   * @param contract
   * @return
   * @throws IOException
   */
  public OkCoinFuturesOrderResult getFuturesOrder(
      long orderId, String symbol, String currentPage, String pageLength, FuturesContract contract)
      throws IOException {

    OkCoinFuturesOrderResult futuresOrder =
        okCoin.getFuturesOrder(
            apikey,
            orderId,
            symbol,
            "1",
            currentPage,
            pageLength,
            contract.getName(),
            signatureCreator());
    return returnOrThrow(futuresOrder);
  }

  public OkCoinPriceLimit getFuturesPriceLimits(CurrencyPair currencyPair, FuturesContract prompt)
      throws IOException {

    return okCoin.getFuturesPriceLimits(OkCoinAdapters.adaptSymbol(currencyPair), prompt.getName());
  }

  public OkCoinFuturesOrderResult getFuturesFilledOrder(
      long orderId, String symbol, String currentPage, String pageLength, FuturesContract contract)
      throws IOException {

    OkCoinFuturesOrderResult futuresOrder =
        okCoin.getFuturesOrder(
            apikey,
            orderId,
            symbol,
            "2",
            currentPage,
            pageLength,
            contract.getName(),
            signatureCreator());
    return returnOrThrow(futuresOrder);
  }

  /**
   * 批量获取合约订单信息
   *
   * @param orderIds
   * @param symbol
   * @param contract
   * @return
   * @throws IOException
   */
  public OkCoinFuturesOrderResult getFuturesOrders(
      String orderIds, String symbol, FuturesContract contract) throws IOException {

    OkCoinFuturesOrderResult futuresOrder =
        okCoin.getFuturesOrders(apikey, orderIds, symbol, contract.getName(), signatureCreator());
    return returnOrThrow(futuresOrder);
  }

  /**
   * 获取OKEX合约交易历史（非个人）
   *
   * @param symbol
   * @param since
   * @param date
   * @return
   * @throws IOException
   */
  public OkCoinFuturesTradeHistoryResult[] getFuturesTradesHistory(
      String symbol, long since, String date) throws IOException {

    OkCoinFuturesTradeHistoryResult[] futuresHistory =
        okCoin.getFuturesTradeHistory(apikey, since, symbol, date, signatureCreator());
    return (futuresHistory);
  }

  /**
   * 获取用户持仓获取OKEX合约账户信息 （全仓）
   *
   * @param symbol
   * @param contract
   * @return
   * @throws IOException
   */
  public OkCoinPositionResult getFuturesPosition(String symbol, FuturesContract contract)
      throws IOException {
    OkCoinPositionResult futuresPositionsCross =
        okCoin.getFuturesPositionsCross(apikey, symbol, contract.getName(), signatureCreator());

    return returnOrThrow(futuresPositionsCross);
  }

  public OkCoinPositionResult getFuturesPosition(String symbol, String contract)
      throws IOException {
    OkCoinPositionResult futuresPositionsCross =
        okCoin.getFuturesPositionsCross(apikey, symbol, contract, signatureCreator());

    return returnOrThrow(futuresPositionsCross);
  }

  /**
   * @param symbol
   * @param contractType
   * @param ordersData
   * @param leverRate
   * @throws IOException
   */
  public OkCoinMoreTradeResult futureBatchTrade(
      String symbol, String contractType, String ordersData, String leverRate) throws IOException {
    return okCoin.futureBatchTrade(
        apikey, symbol, contractType, ordersData, signatureCreator(), leverRate);
  }

  /**
   * 逐仓用户持仓查询
   *
   * @param currencyPair
   * @param contract
   * @return
   * @throws IOException
   */
  public OkCoinPositionResult getFuturesPositionsFixed(
      CurrencyPair currencyPair, FuturesContract contract) throws IOException {
    return okCoin.getFuturesPositionsFixed(
        apikey, OkCoinAdapters.adaptSymbol(currencyPair), contract.getName(), signatureCreator());
  }

  /**
   * 获取合约爆仓单
   *
   * @param pair
   * @param type
   * @param status //状态 0：最近7天未成交 1:最近7天已成交
   * @param currentPage
   * @param pageNumber
   * @param pageLength //每页获取条数，最多不超过50
   * @return
   */
  public OkCoinFutureExplosiveResult futureExplosive(
      CurrencyPair pair,
      FuturesContract type,
      String status,
      Integer currentPage,
      Integer pageNumber,
      Integer pageLength) {
    return okCoin.futureExplosive(
        apikey,
        OkCoinAdapters.adaptSymbol(pair),
        type.getName(),
        status,
        signatureCreator(),
        currentPage,
        pageNumber,
        pageLength);
  }
}
