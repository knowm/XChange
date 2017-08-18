package org.knowm.xchange.hitbtc.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.hitbtc.HitbtcAdapters;
import org.knowm.xchange.hitbtc.dto.HitbtcException;
import org.knowm.xchange.hitbtc.dto.account.HitbtcBalance;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcSymbol;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcOrder;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcOwnTrade;

public class HitbtcTradeServiceRaw extends HitbtcAuthenitcatedService {

  // TODO move this to metadata
  private static Map<CurrencyPair, BigDecimal> LOT_SIZES = new HashMap<>();

  public HitbtcTradeServiceRaw(Exchange exchange) {
    super(exchange);
    if (LOT_SIZES.size() == 0) {
      fetchLotsData();
    }
  }

  /**
   * Fetches the lots from the server and updates the possibly outdated LOT_SIZES map.
   */
  private void fetchLotsData() {
    List<HitbtcSymbol> hitbtcSymbols;
    try {
      hitbtcSymbols = new HitbtcMarketDataService(exchange).getHitbtcSymbols();
    } catch (IOException e) {
      // Do nothing, use the existing LOT_SIZES map instead.
      // TODO warning message handling
      return;
    }
    LOT_SIZES.clear();
    for (HitbtcSymbol hitbtcSymbol : hitbtcSymbols) {
      //TODO is this right?
      LOT_SIZES.put(new CurrencyPair(new Currency(hitbtcSymbol.getId()), new Currency(hitbtcSymbol.getId())), hitbtcSymbol.getTickSize());
    }

  }

  public List<HitbtcOrder> getOpenOrdersRaw() throws IOException {

    try {
      return hitbtc().getHitbtcActiveOrders();
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public List<HitbtcOrder> getRecentOrdersRaw(int maxResults) throws IOException {

    try {
      return hitbtc().getHitbtcRecentOrders();
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcExecutionReportResponse placeMarketOrderRawBaseResponse(MarketOrder marketOrder) throws IOException {

    String symbol = marketOrder.getCurrencyPair().base.getCurrencyCode() + marketOrder.getCurrencyPair().counter.getCurrencyCode();

    long nonce = exchange.getNonceFactory().createValue();
    String side = HitbtcAdapters.getSide(marketOrder.getType()).toString();
    String orderId = HitbtcAdapters.createOrderId(marketOrder, nonce);

    try {
      return hitbtc().postHitbtcNewOrder(orderId, symbol, side,
          null, marketOrder.getTradableAmount(), "market", "IOC");

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
      return hitbtc().postHitbtcNewOrder(orderId, symbol, side, limitOrder.getLimitPrice(),
          limitOrder.getTradableAmount(), "limit", "GTC");
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcExecutionReportResponse cancelOrderRaw(String clientOrderId) throws IOException {

    try {
      return hitbtc().cancelSingleOrder(clientOrderId);
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public List<HitbtcOrder> cancelOrdersRaw(String symbol) throws IOException {

    try {
      return hitbtc().cancelAllOrders(symbol);
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public List<HitbtcOwnTrade> getTradeHistoryRaw(int startIndex, int maxResults, String symbols) throws IOException {

    try {
      return hitbtc().getHitbtcTrades();
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public List<HitbtcBalance> getTradingBalance() throws IOException {
    try {
      return hitbtc().getTradingBalance();
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  /**
   * Represent tradableAmount in lots
   *
   * @throws java.lang.IllegalArgumentException if the result were to be less than lot size for given currency pair
   */
  protected BigInteger getLots(Order order) {

    CurrencyPair pair = order.getCurrencyPair();
    BigDecimal lotDivisor = LOT_SIZES.get(pair);

    BigDecimal lots = order.getTradableAmount().divide(lotDivisor, BigDecimal.ROUND_DOWN).setScale(0, BigDecimal.ROUND_DOWN);
    if (lots.compareTo(BigDecimal.ONE) < 0) {
      throw new IllegalArgumentException("Tradable amount too low");
    }
    return lots.toBigIntegerExact();
  }

}
