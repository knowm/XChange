package org.knowm.xchange.lgo;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.RateLimit;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.lgo.dto.WithCursor;
import org.knowm.xchange.lgo.dto.currency.LgoCurrencies;
import org.knowm.xchange.lgo.dto.currency.LgoCurrency;
import org.knowm.xchange.lgo.dto.key.LgoKey;
import org.knowm.xchange.lgo.dto.marketdata.LgoOrderbook;
import org.knowm.xchange.lgo.dto.order.LgoPlaceLimitOrder;
import org.knowm.xchange.lgo.dto.order.LgoPlaceMarketOrder;
import org.knowm.xchange.lgo.dto.order.LgoPlaceOrder;
import org.knowm.xchange.lgo.dto.order.LgoUnencryptedOrder;
import org.knowm.xchange.lgo.dto.product.LgoLimit;
import org.knowm.xchange.lgo.dto.product.LgoProduct;
import org.knowm.xchange.lgo.dto.product.LgoProductCurrency;
import org.knowm.xchange.lgo.dto.product.LgoProductTotal;
import org.knowm.xchange.lgo.dto.product.LgoProducts;
import org.knowm.xchange.lgo.dto.product.LgoProductsTest;
import org.knowm.xchange.lgo.dto.trade.LgoUserTrade;
import org.knowm.xchange.lgo.dto.trade.LgoUserTrades;

public class LgoAdaptersTest {

  private SimpleDateFormat dateFormat;

  @Before
  public void setUp() {
    dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  @Test
  public void adaptsMetadata() {
    ExchangeMetaData metaData = LgoAdapters.adaptMetadata(emptyMeta(), products(), currencies());

    assertThat(metaData).isNotNull();
    assertThat(metaData.getCurrencies()).hasSize(2).containsKeys(Currency.BTC, Currency.USD);
    assertThat(metaData.getCurrencies().get(Currency.BTC))
        .isEqualToComparingFieldByField(new CurrencyMetaData(8, null));
    assertThat(metaData.getCurrencies().get(Currency.USD))
        .isEqualToComparingFieldByField(new CurrencyMetaData(4, null));
    assertThat(metaData.getCurrencyPairs()).hasSize(1).containsKeys(CurrencyPair.BTC_USD);
  }

  @Test
  public void adaptsKeysIndex() throws IOException {
    InputStream is =
        LgoAdaptersTest.class.getResourceAsStream("/org/knowm/xchange/lgo/key/index.txt");
    String indexFile = IOUtils.toString(is, StandardCharsets.US_ASCII);

    List<LgoKey> keys = LgoAdapters.adaptKeysIndex(indexFile).collect(Collectors.toList());

    assertThat(keys).hasSize(2);
    assertThat(keys.get(0))
        .isEqualToComparingFieldByField(
            new LgoKey(
                "7b5dd30f-47cc-4c72-938b-199b13bc6f72",
                Instant.parse("2019-07-16T09:13:54Z"),
                Instant.parse("2019-07-16T10:13:54Z")));
    assertThat(keys.get(1))
        .isEqualToComparingFieldByField(
            new LgoKey(
                "684700ae-8bc7-4315-8aec-b69ca778339a",
                Instant.parse("2019-07-16T10:03:54Z"),
                Instant.parse("2019-07-16T11:03:54Z")));
  }

  @Test
  public void adaptsBidLimitOrder() {
    Date now = new Date();
    LimitOrder limitOrder =
        new LimitOrder(
            OrderType.BID,
            new BigDecimal("1"),
            CurrencyPair.BTC_USD,
            null,
            now,
            new BigDecimal("6000"));

    LgoPlaceOrder bidOrder = LgoAdapters.adaptLimitOrder(limitOrder);

    assertThat(bidOrder)
        .isEqualToComparingFieldByField(
            new LgoPlaceLimitOrder(
                0, "B", "BTC-USD", new BigDecimal("1"), new BigDecimal("6000"), now.toInstant()));
  }

  @Test
  public void adaptsAskLimitOrder() {
    Date timestamp = new Date();
    LimitOrder limitOrder =
        new LimitOrder(
            OrderType.ASK,
            new BigDecimal("1"),
            CurrencyPair.BTC_USD,
            null,
            timestamp,
            new BigDecimal("6000"));

    LgoPlaceOrder bidOrder = LgoAdapters.adaptLimitOrder(limitOrder);

    assertThat(bidOrder)
        .isEqualToComparingFieldByField(
            new LgoPlaceLimitOrder(
                0,
                "S",
                "BTC-USD",
                new BigDecimal("1"),
                new BigDecimal("6000"),
                timestamp.toInstant()));
  }

  @Test
  public void adaptsBidMarketOrder() {
    Date now = new Date();
    MarketOrder marketOrder =
        new MarketOrder(OrderType.BID, new BigDecimal("1"), CurrencyPair.BTC_USD, null, now);

    LgoPlaceOrder bidOrder = LgoAdapters.adaptEncryptedMarketOrder(marketOrder);

    assertThat(bidOrder)
        .isEqualToComparingFieldByField(
            new LgoPlaceMarketOrder(0, "B", "BTC-USD", new BigDecimal("1"), now.toInstant()));
  }

  @Test
  public void adaptsAskMarketOrder() {
    Date timestamp = new Date();
    MarketOrder marketOrder =
        new MarketOrder(OrderType.ASK, new BigDecimal("1"), CurrencyPair.BTC_USD, null, timestamp);

    LgoPlaceOrder bidOrder = LgoAdapters.adaptEncryptedMarketOrder(marketOrder);

    assertThat(bidOrder)
        .isEqualToComparingFieldByField(
            new LgoPlaceMarketOrder(0, "S", "BTC-USD", new BigDecimal("1"), timestamp.toInstant()));
  }

  @Test
  public void adaptsUnencryptedOrder() {
    Date timestamp = new Date();
    LimitOrder limitOrder =
        new LimitOrder(
            OrderType.ASK,
            new BigDecimal("1"),
            CurrencyPair.BTC_USD,
            null,
            timestamp,
            new BigDecimal("6000"));

    LgoUnencryptedOrder lgoUnencryptedOrder = LgoAdapters.adaptUnencryptedLimitOrder(limitOrder);

    assertThat(lgoUnencryptedOrder.price).isEqualTo("6000");
    assertThat(lgoUnencryptedOrder.quantity).isEqualTo("1");
    assertThat(lgoUnencryptedOrder.productId).isEqualTo("BTC-USD");
    assertThat(lgoUnencryptedOrder.side).isEqualTo("S");
    assertThat(lgoUnencryptedOrder.timestamp).isEqualTo(timestamp.getTime());
    assertThat(lgoUnencryptedOrder.type).isEqualTo("L");
  }

  @Test
  public void adaptsTradeType() {
    assertThat(LgoAdapters.adaptUserTradeType(lgoTrade("B", "T"))).isEqualTo(OrderType.BID);
    assertThat(LgoAdapters.adaptUserTradeType(lgoTrade("B", "M"))).isEqualTo(OrderType.ASK);
    assertThat(LgoAdapters.adaptUserTradeType(lgoTrade("S", "T"))).isEqualTo(OrderType.ASK);
    assertThat(LgoAdapters.adaptUserTradeType(lgoTrade("S", "M"))).isEqualTo(OrderType.BID);
  }

  @Test
  public void adaptsProductId() {
    assertThat(LgoAdapters.adaptProductId("BTC-USD")).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  public void adaptsUserTrades() throws IOException, ParseException {
    WithCursor<LgoUserTrades> lastTrades =
        readResourceAs(
            "/org/knowm/xchange/lgo/trade/example-trades-data.json",
            new TypeReference<WithCursor<LgoUserTrades>>() {});

    UserTrades userTrades = LgoAdapters.adaptUserTrades(lastTrades);

    assertThat(userTrades.getNextPageCursor()).isEqualTo("aGVsbG8=");
    assertThat(userTrades.getUserTrades()).hasSize(2);
    assertThat(userTrades.getUserTrades().get(0))
        .isEqualToComparingFieldByField(
            new UserTrade.Builder()
                .type(OrderType.ASK)
                .originalAmount(new BigDecimal("0.00500000"))
                .currencyPair(CurrencyPair.BTC_USD)
                .price(new BigDecimal("3854.0000"))
                .timestamp(dateFormat.parse("2019-03-05T16:37:17.220Z"))
                .id("2")
                .orderId("155180383648300001")
                .feeAmount(new BigDecimal("0.0096"))
                .feeCurrency(Currency.USD)
                .build());
    assertThat(userTrades.getUserTrades().get(1))
        .isEqualToComparingFieldByField(
            new UserTrade.Builder()
                .type(OrderType.BID)
                .originalAmount(new BigDecimal("0.00829566"))
                .currencyPair(CurrencyPair.BTC_USD)
                .price(new BigDecimal("2410.9000"))
                .timestamp(dateFormat.parse("2019-06-20T15:37:21.855Z"))
                .id("2477363")
                .orderId("156104504046400001")
                .feeAmount(new BigDecimal("0.0100"))
                .feeCurrency(Currency.USD)
                .build());
  }

  @Test
  public void adaptsOrderBook() throws IOException {
    LgoOrderbook lgoOrderbook =
        readResourceAs(
            "/org/knowm/xchange/lgo/marketdata/example-orderbook-data.json",
            new TypeReference<LgoOrderbook>() {});

    OrderBook orderBook = LgoAdapters.adaptOrderBook(lgoOrderbook, CurrencyPair.BTC_USD);

    assertThat(orderBook).isNotNull();
    assertThat(orderBook.getAsks()).hasSize(2);
    assertThat(orderBook.getBids()).hasSize(2);
    assertThat(orderBook.getAsks().get(0))
        .isEqualTo(
            new LimitOrder(
                OrderType.ASK,
                new BigDecimal("4.44440000"),
                CurrencyPair.BTC_USD,
                null,
                null,
                new BigDecimal("2921.9000")));
    assertThat(orderBook.getAsks().get(1))
        .isEqualTo(
            new LimitOrder(
                OrderType.ASK,
                new BigDecimal("8.38460000"),
                CurrencyPair.BTC_USD,
                null,
                null,
                new BigDecimal("2926.5000")));
    assertThat(orderBook.getBids().get(0))
        .isEqualTo(
            new LimitOrder(
                OrderType.BID,
                new BigDecimal("8.35030000"),
                CurrencyPair.BTC_USD,
                null,
                null,
                new BigDecimal("2896.6000")));
    assertThat(orderBook.getBids().get(1))
        .isEqualTo(
            new LimitOrder(
                OrderType.BID,
                new BigDecimal("931.83050000"),
                CurrencyPair.BTC_USD,
                null,
                null,
                new BigDecimal("1850.0000")));
  }

  private <T> T readResourceAs(String path, TypeReference<T> type) throws IOException {
    InputStream is = LgoProductsTest.class.getResourceAsStream(path);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(is, type);
  }

  private LgoUserTrade lgoTrade(String side, String liquidity) {
    return new LgoUserTrade(
        "1",
        "2",
        "BTC-USD",
        new BigDecimal("1"),
        new BigDecimal("1"),
        null,
        new BigDecimal("1"),
        side,
        liquidity);
  }

  private ExchangeMetaData emptyMeta() {
    return new ExchangeMetaData(
        new HashMap<>(), new HashMap<>(), new RateLimit[0], new RateLimit[0], true);
  }

  private LgoCurrencies currencies() {
    return new LgoCurrencies(
        Lists.newArrayList(
            new LgoCurrency("Bitcoin", "BTC", 8), new LgoCurrency("Dollar", "USD", 4)));
  }

  private LgoProducts products() {
    return new LgoProducts(
        Lists.newArrayList(
            new LgoProduct(
                "BTC-USD",
                new LgoProductTotal(limit("10", "50000000")),
                new LgoProductCurrency("BTC", null, limit("0.001", "1000")),
                new LgoProductCurrency("USD", new BigDecimal("0.10"), limit("10", "1000000")))));
  }

  private LgoLimit limit(String min, String max) {
    return new LgoLimit(new BigDecimal(min), new BigDecimal(max));
  }
}
