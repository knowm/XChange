package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import com.xeiam.xchange.hitbtc.dto.HitbtcException;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcSymbol;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcSymbols;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOrder;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOrdersResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOwnTrade;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcTradeMetaData;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcTradeResponse;

public class HitbtcTradeServiceRaw extends HitbtcBasePollingService {

  protected Map<CurrencyPair, HitbtcTradeMetaData> metadata;

  /**
   * Constructor
   *
   * @param exchange
   */
  public HitbtcTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public HitbtcOrdersResponse getOpenOrdersRawBaseResponse() throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {

    try {
      HitbtcOrdersResponse hitbtcActiveOrders = hitbtc.getHitbtcActiveOrders(signatureCreator, exchange.getNonceFactory(), apiKey);
      return hitbtcActiveOrders;
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcOrder[] getOpenOrdersRaw() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException,
      IOException {

    HitbtcOrdersResponse hitbtcActiveOrders = getOpenOrdersRawBaseResponse();
    return hitbtcActiveOrders.getOrders();
  }

  public HitbtcOrdersResponse getRecentOrdersRawBaseResponse(int max_results) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {

    try {
      HitbtcOrdersResponse hitbtcActiveOrders = hitbtc.getHitbtcRecentOrders(signatureCreator, exchange.getNonceFactory(), apiKey, max_results);
      return hitbtcActiveOrders;
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcOrder[] getRecentOrdersRaw(int max_results) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {

    HitbtcOrdersResponse hitbtcActiveOrders = getRecentOrdersRawBaseResponse(max_results);
    return hitbtcActiveOrders.getOrders();
  }

  public HitbtcExecutionReportResponse placeMarketOrderRawBaseResponse(MarketOrder marketOrder) throws ExchangeException,
      NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    String symbol = marketOrder.getCurrencyPair().baseSymbol + marketOrder.getCurrencyPair().counterSymbol;

    long nonce = exchange.getNonceFactory().createValue();
    String side = HitbtcAdapters.getSide(marketOrder.getType());
    String orderId = HitbtcAdapters.createOrderId(marketOrder, nonce);

    try {
      HitbtcExecutionReportResponse response = hitbtc.postHitbtcNewOrder(signatureCreator, exchange.getNonceFactory(), apiKey, orderId, symbol, side,
          null, getLots(marketOrder), "market", "IOC");

      return response;
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcExecutionReport placeMarketOrderRaw(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {

    HitbtcExecutionReportResponse response = placeMarketOrderRawBaseResponse(marketOrder);
    return response.getExecutionReport();
  }

  public HitbtcExecutionReport placeLimitOrderRaw(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {

    try {
      HitbtcExecutionReportResponse postHitbtcNewOrder = fillHitbtcExecutionReportResponse(limitOrder);
      return postHitbtcNewOrder.getExecutionReport();
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcExecutionReportResponse placeLimitOrderRawReturningHitbtcExecutionReportResponse(LimitOrder limitOrder) throws ExchangeException,
      NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    HitbtcExecutionReportResponse postHitbtcNewOrder = fillHitbtcExecutionReportResponse(limitOrder);
    return postHitbtcNewOrder;
  }

  private HitbtcExecutionReportResponse fillHitbtcExecutionReportResponse(LimitOrder limitOrder) throws IOException {

    String symbol = HitbtcAdapters.adaptCurrencyPair(limitOrder.getCurrencyPair());
    long nonce = exchange.getNonceFactory().createValue();
    String side = HitbtcAdapters.getSide(limitOrder.getType());
    String orderId = HitbtcAdapters.createOrderId(limitOrder, nonce);

    try {
      return hitbtc.postHitbtcNewOrder(signatureCreator, exchange.getNonceFactory(), apiKey, orderId, symbol, side, limitOrder.getLimitPrice(),
          getLots(limitOrder), "limit", "GTC");
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcExecutionReportResponse cancelOrderRaw(String orderId) throws ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, IOException {

    // extract symbol and side from original order id: buy/sell
    String originalSide = HitbtcAdapters.getSide(HitbtcAdapters.readOrderType(orderId));
    String symbol = HitbtcAdapters.readSymbol(orderId);

    try {
      return hitbtc.postHitbtcCancelOrder(signatureCreator, exchange.getNonceFactory(), apiKey, orderId, orderId, symbol, originalSide);
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcExecutionReportResponse cancelOrderRaw(String clientOrderId, String cancelRequestClientOrderId, String symbol, String side)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    try {
      return hitbtc.postHitbtcCancelOrder(signatureCreator, exchange.getNonceFactory(), apiKey, clientOrderId, cancelRequestClientOrderId, symbol,
          side);
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcTradeResponse getTradeHistoryRawBaseResponse(int startIndex, int maxResults, String symbols) throws ExchangeException,
      NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    try {
      HitbtcTradeResponse hitbtcTrades = hitbtc.getHitbtcTrades(signatureCreator, exchange.getNonceFactory(), apiKey, "ts", startIndex, maxResults,
          symbols, "desc", null, null);
      return hitbtcTrades;
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcOwnTrade[] getTradeHistoryRaw(int startIndex, int maxResults, String symbols) throws ExchangeException,
      NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

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
    BigDecimal lotDivisor = metadata.get(pair).getMinimumAmount();

    BigDecimal lots = order.getTradableAmount().divide(lotDivisor, BigDecimal.ROUND_UNNECESSARY);
    if (lots.compareTo(BigDecimal.ONE) < 0) {
      throw new IllegalArgumentException("Tradable amount too low");
    }
    return lots.toBigIntegerExact();
  }

  /**
   * @return Map of currency pairs to their corresponding metadata.
   */
  public Map<CurrencyPair, HitbtcTradeMetaData> getTradeMetaDataMap() throws IOException {

    // // 1. get trading fee from properties file
    // boolean makerFee = CFG.getBoolProperty(HITBTC_ORDER_FEE_POLICY_MAKER);
    // Properties config = CFG.getProperties();
    // String currencyPair =
    // config.getProperty(HITBTC_ORDER_FEE_LISTING_DEFAULT);
    // if (currencyPair == null) {
    // currencyPair = config.getProperty(XCHANGE_ORDER_FEE_LISTING_DEFAULT,
    // CurrencyPair.BTC_USD.toString());
    // }
    // CurrencyPair pair = CurrencyPair.fromString(currencyPair);
    // HitbtcTradeMetaData listingHelper = metadata.get(pair);
    // BigDecimal tradingFee = makerFee ?
    // listingHelper.getProvideLiquidityRate() :
    // listingHelper.getTakeLiquidityRate();

    // 2. get symbols from REST API
    HitbtcSymbols hitbtcSymbols = hitbtc.getSymbols();

    // 3. Create meta data
    Map<CurrencyPair, HitbtcTradeMetaData> result = new HashMap<CurrencyPair, HitbtcTradeMetaData>();
    for (HitbtcSymbol symbol : hitbtcSymbols.getHitbtcSymbols()) {

      CurrencyPair pair = HitbtcAdapters.adaptSymbol(symbol);

      BigDecimal lot = symbol.getLot();

      // TODO look up or poll fee for pair and provide to following method

      HitbtcTradeMetaData hitbtcTradeMetaData = new HitbtcTradeMetaData(null, lot, symbol.getStep().scale(), symbol.getTakeLiquidityRate(),
          symbol.getProvideLiquidityRate());

      result.put(pair, hitbtcTradeMetaData);
    }
    return metadata;
  }

}
