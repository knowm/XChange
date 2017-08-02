package org.knowm.xchange.hitbtc.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.hitbtc.HitbtcAdapters;
import org.knowm.xchange.hitbtc.dto.HitbtcException;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcMultiExecutionReportResponse;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcOrder;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcOrdersResponse;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcOwnTrade;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcTradeResponse;

public class HitbtcTradeServiceRaw extends HitbtcBaseService {

  // TODO move this to metadata
  private static Map<CurrencyPair, BigDecimal> LOT_SIZES = new HashMap<>();

  static {

    LOT_SIZES.put(new CurrencyPair("BCN/BTC"), new BigDecimal("100"));
    LOT_SIZES.put(new CurrencyPair("BTC/EUR"), new BigDecimal("0.01"));
    LOT_SIZES.put(new CurrencyPair("BTC/USD"), new BigDecimal("0.01"));
    LOT_SIZES.put(new CurrencyPair("DASH/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("DOGE/BTC"), new BigDecimal("1000"));
    LOT_SIZES.put(new CurrencyPair("DSH/BTC"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("EMC/BTC"), new BigDecimal("0.1"));
    LOT_SIZES.put(new CurrencyPair("ETH/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("ETH/EUR"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("FCN/BTC"), new BigDecimal("0.01"));
    LOT_SIZES.put(new CurrencyPair("LSK/BTC"), new BigDecimal("0.01"));
    LOT_SIZES.put(new CurrencyPair("LSK/EUR"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("LTC/BTC"), new BigDecimal("0.1"));
    LOT_SIZES.put(new CurrencyPair("LTC/EUR"), new BigDecimal("0.1"));
    LOT_SIZES.put(new CurrencyPair("LTC/USD"), new BigDecimal("0.1"));
    LOT_SIZES.put(new CurrencyPair("NXT/BTC"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("QCN/BTC"), new BigDecimal("0.01"));
    LOT_SIZES.put(new CurrencyPair("SBD/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("SC/BTC"), new BigDecimal("100"));
    LOT_SIZES.put(new CurrencyPair("STEEM/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("STEEM/EUR"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("XDN/BTC"), new BigDecimal("100"));
    LOT_SIZES.put(new CurrencyPair("XEM/BTC"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("XMR/BTC"), new BigDecimal("0.01"));
    LOT_SIZES.put(new CurrencyPair("ARDR/BTC"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("ZEC/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("WAVES/BTC"), new BigDecimal("0.01"));
    LOT_SIZES.put(new CurrencyPair("MAID/BTC"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("AMP/BTC"), new BigDecimal("0.1"));
    LOT_SIZES.put(new CurrencyPair("BTU/BTC"), new BigDecimal("0.0001"));
    LOT_SIZES.put(new CurrencyPair("DGD/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("XLC/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("TIME/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("GNO/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("XMR/USD"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("DASH/USD"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("ETH/USD"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("NXT/USD"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("ZRC/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("ICN/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("SNGLS/BTC"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("1ST/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("TRST/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("BOS/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("REP/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("DCT/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("AEON/BTC"), new BigDecimal("0.1"));
    LOT_SIZES.put(new CurrencyPair("TAAS/BTC"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("PLU/BTC"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("LUN/BTC"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("GUP/BTC"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("SWT/BTC"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("RLC/BTC"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("WINGS/BTC"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("XAUR/BTC"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("TKN/BTC"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("AE/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("PTOY/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("ZEC/USD"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("BCN/USD"), new BigDecimal("10"));
    LOT_SIZES.put(new CurrencyPair("DOGE/USD"), new BigDecimal("10"));
    LOT_SIZES.put(new CurrencyPair("XEM/USD"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("XDN/USD"), new BigDecimal("10"));
    LOT_SIZES.put(new CurrencyPair("MAID/USD"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("WTT/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("ETC/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("NXC/BTC"), new BigDecimal("1"));
    LOT_SIZES.put(new CurrencyPair("CFI/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("BNT/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("PLBT/BTC"), new BigDecimal("0.001"));
    LOT_SIZES.put(new CurrencyPair("XDNCO/BTC"), new BigDecimal("100000"));
    LOT_SIZES.put(new CurrencyPair("XRP/BTC"), new BigDecimal("1"));
  }

  /**
   * Constructor
   *
   * @param exchange
   */
  public HitbtcTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public HitbtcOrdersResponse getOpenOrdersRawBaseResponse() throws IOException {

    try {
      HitbtcOrdersResponse hitbtcActiveOrders = hitbtc.getHitbtcActiveOrders(signatureCreator, exchange.getNonceFactory(), apiKey);
      return hitbtcActiveOrders;
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcOrder[] getOpenOrdersRaw() throws IOException {

    HitbtcOrdersResponse hitbtcActiveOrders = getOpenOrdersRawBaseResponse();
    return hitbtcActiveOrders.getOrders();
  }

  public HitbtcOrdersResponse getRecentOrdersRawBaseResponse(int max_results) throws IOException {

    try {
      HitbtcOrdersResponse hitbtcActiveOrders = hitbtc.getHitbtcRecentOrders(signatureCreator, exchange.getNonceFactory(), apiKey, max_results);
      return hitbtcActiveOrders;
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcOrder[] getRecentOrdersRaw(int max_results) throws IOException {

    HitbtcOrdersResponse hitbtcActiveOrders = getRecentOrdersRawBaseResponse(max_results);
    return hitbtcActiveOrders.getOrders();
  }

  public HitbtcExecutionReportResponse placeMarketOrderRawBaseResponse(MarketOrder marketOrder) throws IOException {

    String symbol = marketOrder.getCurrencyPair().base.getCurrencyCode() + marketOrder.getCurrencyPair().counter.getCurrencyCode();

    long nonce = exchange.getNonceFactory().createValue();
    String side = HitbtcAdapters.getSide(marketOrder.getType()).toString();
    String orderId = HitbtcAdapters.createOrderId(marketOrder, nonce);

    try {
      HitbtcExecutionReportResponse response = hitbtc.postHitbtcNewOrder(signatureCreator, exchange.getNonceFactory(), apiKey, orderId, symbol, side,
          null, getLots(marketOrder), "market", "IOC");

      return response;
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcExecutionReport placeMarketOrderRaw(MarketOrder marketOrder) throws IOException {

    HitbtcExecutionReportResponse response = placeMarketOrderRawBaseResponse(marketOrder);
    return response.getExecutionReport();
  }

  public HitbtcExecutionReport placeLimitOrderRaw(LimitOrder limitOrder) throws IOException {

    try {
      HitbtcExecutionReportResponse postHitbtcNewOrder = fillHitbtcExecutionReportResponse(limitOrder);
      return postHitbtcNewOrder.getExecutionReport();
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcExecutionReportResponse placeLimitOrderRawReturningHitbtcExecutionReportResponse(LimitOrder limitOrder) throws IOException {

    HitbtcExecutionReportResponse postHitbtcNewOrder = fillHitbtcExecutionReportResponse(limitOrder);
    return postHitbtcNewOrder;
  }

  private HitbtcExecutionReportResponse fillHitbtcExecutionReportResponse(LimitOrder limitOrder) throws IOException {

    String symbol = HitbtcAdapters.adaptCurrencyPair(limitOrder.getCurrencyPair());
    long nonce = exchange.getNonceFactory().createValue();
    String side = HitbtcAdapters.getSide(limitOrder.getType()).toString();
    String orderId = HitbtcAdapters.createOrderId(limitOrder, nonce);

    try {
      return hitbtc.postHitbtcNewOrder(signatureCreator, exchange.getNonceFactory(), apiKey, orderId, symbol, side, limitOrder.getLimitPrice(),
          getLots(limitOrder), "limit", "GTC");
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcExecutionReportResponse cancelOrderRaw(String orderId) throws IOException {

    // extract symbol and side from original order id: buy/sell
    String originalSide = HitbtcAdapters.getSide(HitbtcAdapters.readOrderType(orderId)).toString();
    String symbol = HitbtcAdapters.readSymbol(orderId);

    try {
      return hitbtc.postHitbtcCancelOrder(signatureCreator, exchange.getNonceFactory(), apiKey, orderId, orderId, symbol, originalSide);
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcExecutionReportResponse cancelOrderRaw(String clientOrderId, String cancelRequestClientOrderId, String symbol,
      String side) throws IOException {

    try {
      return hitbtc.postHitbtcCancelOrder(signatureCreator, exchange.getNonceFactory(), apiKey, clientOrderId, cancelRequestClientOrderId, symbol,
          side);
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcMultiExecutionReportResponse cancelOrdersRaw(String symbol, String side) throws IOException {

    try {
      return hitbtc.postHitbtcCancelOrders(signatureCreator, exchange.getNonceFactory(), apiKey, symbol, side);
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcTradeResponse getTradeHistoryRawBaseResponse(int startIndex, int maxResults, String symbols) throws IOException {

    try {
      HitbtcTradeResponse hitbtcTrades = hitbtc.getHitbtcTrades(signatureCreator, exchange.getNonceFactory(), apiKey, "ts", startIndex, maxResults,
          symbols, "desc", null, null);
      return hitbtcTrades;
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcOwnTrade[] getTradeHistoryRaw(int startIndex, int maxResults, String symbols) throws IOException {

    HitbtcTradeResponse hitbtcTrades = getTradeHistoryRawBaseResponse(startIndex, maxResults, symbols);
    return hitbtcTrades.getTrades();
  }

  /**
   * Represent tradableAmount in lots
   *
   * @throws java.lang.IllegalArgumentException if the result were to be less than lot size for given currency pair
   */
  protected BigInteger getLots(Order order) {

    CurrencyPair pair = order.getCurrencyPair();
    BigDecimal lotDivisor = LOT_SIZES.get(pair);

    BigDecimal lots = order.getTradableAmount().divide(lotDivisor, BigDecimal.ROUND_UNNECESSARY).setScale(0, BigDecimal.ROUND_DOWN);
    if (lots.compareTo(BigDecimal.ONE) < 0) {
      throw new IllegalArgumentException("Tradable amount too low");
    }
    return lots.toBigIntegerExact();
  }

}
