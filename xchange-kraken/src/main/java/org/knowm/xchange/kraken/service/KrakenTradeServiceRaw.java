package org.knowm.xchange.kraken.service;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.kraken.KrakenUtils;
import org.knowm.xchange.kraken.dto.account.KrakenTradeVolume;
import org.knowm.xchange.kraken.dto.account.results.KrakenTradeVolumeResult;
import org.knowm.xchange.kraken.dto.trade.KrakenOpenPosition;
import org.knowm.xchange.kraken.dto.trade.KrakenOrder;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderResponse;
import org.knowm.xchange.kraken.dto.trade.KrakenStandardOrder;
import org.knowm.xchange.kraken.dto.trade.KrakenStandardOrder.KrakenOrderBuilder;
import org.knowm.xchange.kraken.dto.trade.KrakenTrade;
import org.knowm.xchange.kraken.dto.trade.KrakenType;
import org.knowm.xchange.kraken.dto.trade.results.KrakenCancelOrderResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenCancelOrderResult.KrakenCancelOrderResponse;
import org.knowm.xchange.kraken.dto.trade.results.KrakenClosedOrdersResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenOpenOrdersResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenOpenPositionsResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenOrderResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenQueryOrderResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenQueryTradeResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenTradeHistoryResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenTradeHistoryResult.KrakenTradeHistory;

public class KrakenTradeServiceRaw extends KrakenBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KrakenTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public Map<String, KrakenOrder> getKrakenOpenOrders() throws IOException {

    return getKrakenOpenOrders(false, null);
  }

  public Map<String, KrakenOrder> getKrakenOpenOrders(boolean includeTrades, String userRef)
      throws IOException {

    KrakenOpenOrdersResult result =
        kraken.openOrders(
            includeTrades,
            userRef,
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());

    return checkResult(result).getOrders();
  }

  public Map<String, KrakenOrder> getKrakenClosedOrders() throws IOException {

    return getKrakenClosedOrders(false, null, null, null, null, null);
  }

  public Map<String, KrakenOrder> getKrakenClosedOrders(
      boolean includeTrades,
      String userRef,
      String start,
      String end,
      String offset,
      String closeTime)
      throws IOException {

    KrakenClosedOrdersResult result =
        kraken.closedOrders(
            includeTrades,
            userRef,
            start,
            end,
            offset,
            closeTime,
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());

    return checkResult(result).getOrders();
  }

  public Map<String, KrakenOrder> queryKrakenOrders(String... transactionIds) throws IOException {

    return queryKrakenOrders(false, null, transactionIds);
  }

  public KrakenQueryOrderResult queryKrakenOrdersResult(
      boolean includeTrades, String userRef, String... transactionIds) throws IOException {

    KrakenQueryOrderResult krakenQueryOrderResult =
        kraken.queryOrders(
            includeTrades,
            userRef,
            createDelimitedString(transactionIds),
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());

    return krakenQueryOrderResult;
  }

  public Map<String, KrakenOrder> queryKrakenOrders(
      boolean includeTrades, String userRef, String... transactionIds) throws IOException {

    KrakenQueryOrderResult result =
        kraken.queryOrders(
            includeTrades,
            userRef,
            createDelimitedString(transactionIds),
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());

    return checkResult(result);
  }

  public KrakenTradeHistory getKrakenTradeHistory() throws IOException {

    return getKrakenTradeHistory(null, false, null, null, null);
  }

  public KrakenTradeHistory getKrakenTradeHistory(
      String type, boolean includeTrades, Long start, Long end, Long offset) throws IOException {

    KrakenTradeHistoryResult result =
        kraken.tradeHistory(
            type,
            includeTrades,
            start,
            end,
            offset,
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());

    return checkResult(result);
  }

  public Map<String, KrakenTrade> queryKrakenTrades(String... transactionIds) throws IOException {

    return queryKrakenTrades(false, transactionIds);
  }

  public Map<String, KrakenTrade> queryKrakenTrades(boolean includeTrades, String... transactionIds)
      throws IOException {

    KrakenQueryTradeResult result =
        kraken.queryTrades(
            includeTrades,
            createDelimitedString(transactionIds),
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());

    return checkResult(result);
  }

  public Map<String, KrakenOpenPosition> getOpenPositions() throws IOException {

    return getOpenPositions(false);
  }

  public Map<String, KrakenOpenPosition> getOpenPositions(boolean doCalcs, String... transactionIds)
      throws IOException {

    KrakenOpenPositionsResult result =
        kraken.openPositions(
            createDelimitedString(transactionIds),
            doCalcs,
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());

    return checkResult(result);
  }

  public KrakenOrderResponse placeKrakenMarketOrder(MarketOrder marketOrder) throws IOException {

    KrakenType type = KrakenType.fromOrderType(marketOrder.getType());
    KrakenOrderBuilder orderBuilder =
        KrakenStandardOrder.getMarketOrderBuilder(
                marketOrder.getCurrencyPair(), type, marketOrder.getOriginalAmount())
            .withUserRefId(marketOrder.getUserReference())
            .withOrderFlags(marketOrder.getOrderFlags())
            .withLeverage(marketOrder.getLeverage());

    return placeKrakenOrder(orderBuilder.buildOrder());
  }

  public KrakenOrderResponse placeKrakenSettlePositionOrder(MarketOrder marketOrder)
      throws IOException {

    KrakenType type = KrakenType.fromOrderType(marketOrder.getType());
    KrakenOrderBuilder orderBuilder =
        KrakenStandardOrder.getSettlePositionOrderBuilder(
                marketOrder.getCurrencyPair(), type, marketOrder.getOriginalAmount())
            .withUserRefId(marketOrder.getUserReference());

    return placeKrakenOrder(orderBuilder.buildOrder());
  }

  public KrakenOrderResponse placeKrakenLimitOrder(LimitOrder limitOrder) throws IOException {

    KrakenType type = KrakenType.fromOrderType(limitOrder.getType());
    KrakenOrderBuilder krakenOrderBuilder =
        KrakenStandardOrder.getLimitOrderBuilder(
                limitOrder.getCurrencyPair(),
                type,
                limitOrder.getLimitPrice().toPlainString(),
                limitOrder.getOriginalAmount())
            .withUserRefId(limitOrder.getUserReference())
            .withOrderFlags(limitOrder.getOrderFlags())
            .withLeverage(limitOrder.getLeverage());

    return placeKrakenOrder(krakenOrderBuilder.buildOrder());
  }

  public KrakenOrderResponse placeKrakenOrder(KrakenStandardOrder krakenStandardOrder)
      throws IOException {

    KrakenOrderResult result = null;
    if (!krakenStandardOrder.isValidateOnly()) {
      result =
          kraken.addOrder(
              KrakenUtils.createKrakenCurrencyPair(krakenStandardOrder.getAssetPair()),
              krakenStandardOrder.getType().toString(),
              krakenStandardOrder.getOrderType().toApiFormat(),
              krakenStandardOrder.getPrice(),
              krakenStandardOrder.getSecondaryPrice(),
              krakenStandardOrder.getVolume().toPlainString(),
              krakenStandardOrder.getLeverage(),
              krakenStandardOrder.getPositionTxId(),
              delimitSet(krakenStandardOrder.getOrderFlags()),
              krakenStandardOrder.getStartTime(),
              krakenStandardOrder.getExpireTime(),
              krakenStandardOrder.getUserRefId(),
              krakenStandardOrder.getCloseOrder(),
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory());
    } else {
      result =
          kraken.addOrderValidateOnly(
              KrakenUtils.createKrakenCurrencyPair(krakenStandardOrder.getAssetPair()),
              krakenStandardOrder.getType().toString(),
              krakenStandardOrder.getOrderType().toApiFormat(),
              krakenStandardOrder.getPrice(),
              krakenStandardOrder.getSecondaryPrice(),
              krakenStandardOrder.getVolume().toPlainString(),
              krakenStandardOrder.getLeverage(),
              krakenStandardOrder.getPositionTxId(),
              delimitSet(krakenStandardOrder.getOrderFlags()),
              krakenStandardOrder.getStartTime(),
              krakenStandardOrder.getExpireTime(),
              krakenStandardOrder.getUserRefId(),
              true,
              krakenStandardOrder.getCloseOrder(),
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory());
    }

    return checkResult(result);
  }

  public KrakenCancelOrderResponse cancelKrakenOrder(String orderId) throws IOException {

    KrakenCancelOrderResult result =
        kraken.cancelOrder(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory(),
            orderId);

    return checkResult(result);
  }

  protected KrakenTradeVolume getTradeVolume(CurrencyPair... currencyPairs) throws IOException {

    KrakenTradeVolumeResult result =
        kraken.tradeVolume(
            delimitAssetPairs(currencyPairs),
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());

    return checkResult(result);
  }

  public Map<String, KrakenOrder> getOrders(String... orderIds) throws IOException {

    String orderIdsString = String.join(",", orderIds);

    KrakenQueryOrderResult krakenOrderResult =
        kraken.queryOrders(
            false,
            null,
            orderIdsString,
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());

    return checkResult(krakenOrderResult);
  }
}
