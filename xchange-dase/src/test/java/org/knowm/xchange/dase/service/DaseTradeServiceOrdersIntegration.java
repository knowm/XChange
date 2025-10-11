package org.knowm.xchange.dase.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dase.DaseAdapters;
import org.knowm.xchange.dase.DaseExchange;
import org.knowm.xchange.dase.dto.marketdata.DaseMarketConfig;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;

/**
 * Authenticated orders integration tests (smoke-level) for place/cancel and open orders. Skips when
 * credentials are missing; placement also requires DASE_TEST_ENABLE_ORDERS=1. Run with: mvn clean
 * verify -DskipIntegrationTests=false
 */
public class DaseTradeServiceOrdersIntegration {

  private static final String DEFAULT_MARKET = "BTC-CZK"; // liquid, 2 price decimals in examples

  private Exchange authenticatedExchangeOrSkip() {
    String apiKey = System.getenv("DASE_API_KEY");
    String secret = System.getenv("DASE_API_SECRET");
    boolean hasCreds = apiKey != null && !apiKey.isEmpty() && secret != null && !secret.isEmpty();
    assumeTrue("DASE_API_KEY/DASE_API_SECRET must be set for authenticated tests", hasCreds);

    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    ExchangeSpecification spec = ex.getDefaultExchangeSpecification();
    spec.setApiKey(apiKey);
    spec.setSecretKey(secret);
    ex.applySpecification(spec);
    return ex;
  }

  @Test
  public void open_orders_live_smoke() throws Exception {
    Exchange ex = authenticatedExchangeOrSkip();
    DaseTradeService svc = new DaseTradeService(ex);

    OpenOrders oo = svc.getOpenOrders();
    assertNotNull(oo);
    // Not asserting count to avoid flakiness across accounts
  }

  @Test
  public void place_and_cancel_limit_order_live() throws Exception {
    boolean enabled = "1".equals(System.getenv("DASE_TEST_ENABLE_ORDERS"));
    assumeTrue("Set DASE_TEST_ENABLE_ORDERS=1 to run live order placement", enabled);

    Exchange ex = authenticatedExchangeOrSkip();
    DaseTradeService svc = new DaseTradeService(ex);
    DaseMarketDataServiceRaw md = new DaseMarketDataServiceRaw(ex);

    String market = System.getenv().getOrDefault("DASE_TEST_MARKET", DEFAULT_MARKET);
    DaseMarketConfig cfg = md.getMarket(market);

    CurrencyPair pair = DaseAdapters.toCurrencyPair(market);
    // Determine minimal valid size/price from market config
    int sizePrecision = cfg == null || cfg.sizePrecision == null ? 8 : cfg.sizePrecision;
    int pricePrecision = cfg == null || cfg.pricePrecision == null ? 2 : cfg.pricePrecision;
    BigDecimal minSize =
        parseDecimalOr(cfg == null ? null : cfg.minOrderSize, new BigDecimal("0.00002"));

    // Pick a very low bid price to avoid execution and reduce required funds
    BigDecimal refBid = md.getTicker(market).getBid();
    if (refBid == null) refBid = BigDecimal.ONE;
    BigDecimal price = refBid.multiply(new BigDecimal("0.1"));
    price = price.setScale(pricePrecision, RoundingMode.DOWN);
    BigDecimal size = minSize.setScale(sizePrecision, RoundingMode.UP);

    LimitOrder lo =
        new LimitOrder.Builder(Order.OrderType.BID, pair)
            .originalAmount(size)
            .limitPrice(price)
            .build();

    String id = null;
    try {
      id = svc.placeLimitOrder(lo);
      assertNotNull(id);
    } finally {
      if (id != null) {
        // Best effort cancel; avoid failing the test if cancel throws
        try {
          assertTrue(svc.cancelOrder(id));
        } catch (Exception ignored) {
          // ignored
        }
      }
    }
    // Optional: probe order existence via batch get
    if (id != null) {
      try {
        assertNotNull(svc.batchGetOrders(Collections.singletonList(id)));
      } catch (Exception ignored) {
        // Some venues may not return recently canceled orders; acceptable
      }
    }
  }

  private static BigDecimal parseDecimalOr(String s, BigDecimal fallback) {
    try {
      return s == null ? fallback : new BigDecimal(s);
    } catch (Exception e) {
      return fallback;
    }
  }
}
