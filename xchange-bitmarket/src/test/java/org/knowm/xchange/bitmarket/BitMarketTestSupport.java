package org.knowm.xchange.bitmarket;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.bitmarket.dto.BitMarketDtoTestSupport;
import org.knowm.xchange.bitmarket.dto.account.BitMarketBalance;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketOrder;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;

public class BitMarketTestSupport extends BitMarketDtoTestSupport {

  protected static final String SPECIFICATION_USERNAME = "admin";
  protected static final String SPECIFICATION_API_KEY = "publicKey";
  protected static final String SPECIFICATION_SECRET_KEY = "secretKey";

  public static final BitMarketBalance PARSED_BALANCE = new BitMarketBalance(createParsedAvailable(), createParsedBlocked());

  public static final BitMarketOrder PARSED_TRADE_ORDER = new BitMarketOrder(31408L, "BTCPLN", new BigDecimal("0.50000000"),
      new BigDecimal("4000.0000"), new BigDecimal("2000.00000000"), "sell", 1432916922L);

  public static final BitMarketBalance PARSED_TRADE_BALANCE = new BitMarketBalance(createParsedTradeAvailable(), createParsedTradeBlocked());

  public static final BitMarketOrderBook PARSED_ORDER_BOOK = new BitMarketOrderBook(
      new BigDecimal[][]{new BigDecimal[]{new BigDecimal("14.6999"), new BigDecimal("20.47")},
          new BigDecimal[]{new BigDecimal("14.7"), new BigDecimal("10.06627287")}},
      new BigDecimal[][]{new BigDecimal[]{new BigDecimal("14.4102"), new BigDecimal("1.55")},
          new BigDecimal[]{new BigDecimal("14.4101"), new BigDecimal("27.77224019")},
          new BigDecimal[]{new BigDecimal("0"), new BigDecimal("52669.33019064")}});

  public static final OrderBook ORDER_BOOK = new OrderBook(null,
      Arrays.asList(new LimitOrder(Order.OrderType.ASK, new BigDecimal("20.47"), CurrencyPair.BTC_AUD, null, null, new BigDecimal("14.6999")),
          new LimitOrder(Order.OrderType.ASK, new BigDecimal("10.06627287"), CurrencyPair.BTC_AUD, null, null, new BigDecimal("14.7"))),
      Arrays.asList(new LimitOrder(Order.OrderType.BID, new BigDecimal("1.55"), CurrencyPair.BTC_AUD, null, null, new BigDecimal("14.4102")),
          new LimitOrder(Order.OrderType.BID, new BigDecimal("27.77224019"), CurrencyPair.BTC_AUD, null, null, new BigDecimal("14.4101")),
          new LimitOrder(Order.OrderType.BID, new BigDecimal("52669.33019064"), CurrencyPair.BTC_AUD, null, null, new BigDecimal("0"))));

  public static final BitMarketTicker PARSED_TICKER = new BitMarketTicker(new BigDecimal("1794.5000"), new BigDecimal("1789.2301"),
      new BigDecimal("1789.2001"), new BigDecimal("1756.5000"), new BigDecimal("1813.5000"), new BigDecimal("1785.8484"),
      new BigDecimal("455.69192487"));

  public static BitMarketTrade[] expectedParsedTrades() {
    return new BitMarketTrade[]{new BitMarketTrade("78455", new BigDecimal("14.6900"), new BigDecimal("27.24579867"), 1450344119L, "ask"),
        new BitMarketTrade("78454", new BigDecimal("14.4105"), new BigDecimal("5.22284399"), 1450343831L, "ask"),
        new BitMarketTrade("78453", new BigDecimal("14.4105"), new BigDecimal("0.10560487"), 1450303414L, "ask")};
  }

  public static Trade[] expectedTrades() {
    return new Trade[]{
        new Trade(Order.OrderType.BID, new BigDecimal("0.10560487"), CurrencyPair.BTC_AUD, new BigDecimal("14.4105"), new Date(1450303414000L),
            "78453"),
        new Trade(Order.OrderType.BID, new BigDecimal("5.22284399"), CurrencyPair.BTC_AUD, new BigDecimal("14.4105"), new Date(1450343831000L),
            "78454"),
        new Trade(Order.OrderType.BID, new BigDecimal("27.24579867"), CurrencyPair.BTC_AUD, new BigDecimal("14.6900"), new Date(1450344119000L),
            "78455")};
  }

  public static Balance[] expectedInfoBalances() {
    return new Balance[]{
        new Balance(Currency.LTC, new BigDecimal("10.390000000000"), new BigDecimal("10.301000000000"), new BigDecimal("0.089"), new BigDecimal("0"),
            new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0")),
        new Balance(Currency.PLN, new BigDecimal("63.566000000000"), new BigDecimal("4.166000000000"), new BigDecimal("59.4"), new BigDecimal("0"),
            new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0")),
        new Balance(Currency.BTC, new BigDecimal("0.029140000000"), new BigDecimal("0.029140000000"), new BigDecimal("0"), new BigDecimal("0"),
            new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"))};
  }

  public static Balance[] expectedBalances() {
    return new Balance[]{new Balance(Currency.BTC, new BigDecimal("20.00000000"), new BigDecimal("10.00000000"), new BigDecimal("10.00000000")),
        new Balance(Currency.AUD, new BigDecimal("40.00000000"), new BigDecimal("20.00000000"), new BigDecimal("20.00000000")),
        new Balance(Currency.PLN, new BigDecimal("60.00000000"), new BigDecimal("30.00000000"), new BigDecimal("30.00000000")),};
  }

  public static LimitOrder[] expectedOrders() {
    return new LimitOrder[]{
        new LimitOrder(Order.OrderType.BID, new BigDecimal("0.20000000"), CurrencyPair.BTC_PLN, "31393", new Date(1432661682000L),
            new BigDecimal("3000.0000")),
        new LimitOrder(Order.OrderType.ASK, new BigDecimal("0.08000000"), CurrencyPair.BTC_PLN, "31391", new Date(1432551696000L),
            new BigDecimal("4140.0000"))};
  }

  public static UserTrade[] expectedUserTrades() {
    return new UserTrade[]{
        new UserTrade(Order.OrderType.BID, new BigDecimal("0.01000000"), CurrencyPair.BTC_PLN, new BigDecimal("875.9898"), new Date(1429901376000L),
            "386637", null, null, Currency.BTC),
        new UserTrade(Order.OrderType.BID, new BigDecimal("0.49000000"), CurrencyPair.BTC_PLN, new BigDecimal("875.9898"), new Date(1429901383000L),
            "386638", null, null, Currency.BTC),
        new UserTrade(Order.OrderType.BID, new BigDecimal("0.50000000"), CurrencyPair.BTC_PLN, new BigDecimal("869.9900"), new Date(1429911236000L),
            "386651", null, null, Currency.BTC),
        new UserTrade(Order.OrderType.BID, new BigDecimal("0.03120150"), CurrencyPair.BTC_PLN, new BigDecimal("865.6667"), new Date(1429965622000L),
            "386750", null, null, Currency.BTC),
        new UserTrade(Order.OrderType.ASK, new BigDecimal("1.08260046"), CurrencyPair.BTC_PLN, new BigDecimal("877.0000"), new Date(1430687948000L),
            "389406", "11852566", new BigDecimal("0.30312011"), Currency.PLN)};
  }

  public static UserTrade[] expectedCpUserTrades() {
    return new UserTrade[]{
        new UserTrade(Order.OrderType.ASK, new BigDecimal("2.140000000"), CurrencyPair.BTC_EUR, new BigDecimal("110.0000"), new Date(1234567890000L),
            "389406", null, null, Currency.EUR),
        new UserTrade(Order.OrderType.BID, new BigDecimal("0.05555555"), CurrencyPair.BTC_EUR, new BigDecimal("115.5555"), new Date(1400000000000L),
            "386750", null, null, Currency.BTC),};
  }

  public static final UserTrade EXPECTED_BM_USER_TRADES = new UserTrade(Order.OrderType.BID, new BigDecimal("0.08888888"), CurrencyPair.BTC_EUR,
      new BigDecimal("210.3333"), new Date(1444444444000L), "386775", null, null, Currency.BTC);

  public static final Ticker TICKER = new Ticker.Builder().bid(new BigDecimal("1789.2301")).ask(new BigDecimal("1794.5000"))
      .last(new BigDecimal("1789.2001")).low(new BigDecimal("1756.5000")).high(new BigDecimal("1813.5000")).currencyPair(CurrencyPair.BTC_AUD)
      .vwap(new BigDecimal("1785.8484")).volume(new BigDecimal("455.69192487")).build();

  public static Map<String, BigDecimal> createAvailable() {
    Map<String, BigDecimal> availableMock = new HashMap<>();
    availableMock.put("BTC", new BigDecimal("10.00000000"));
    availableMock.put("AUD", new BigDecimal("20.00000000"));
    availableMock.put("PLN", new BigDecimal("30.00000000"));

    return availableMock;
  }

  public static Map<String, BigDecimal> createBlocked() {
    Map<String, BigDecimal> blockedMock = new HashMap<>();
    blockedMock.put("BTC", new BigDecimal("10.00000000"));
    blockedMock.put("AUD", new BigDecimal("20.00000000"));
    blockedMock.put("PLN", new BigDecimal("30.00000000"));

    return blockedMock;
  }

  public static Map<String, BigDecimal> createParsedAvailable() {
    Map<String, BigDecimal> availableMock = new HashMap<>();
    availableMock.put("PLN", new BigDecimal("4.166000000000"));
    availableMock.put("BTC", new BigDecimal("0.029140000000"));
    availableMock.put("LTC", new BigDecimal("10.301000000000"));

    return availableMock;
  }

  public static Map<String, BigDecimal> createParsedBlocked() {
    Map<String, BigDecimal> blockedMock = new HashMap<>();
    blockedMock.put("PLN", new BigDecimal("59.4"));
    blockedMock.put("BTC", new BigDecimal("0"));
    blockedMock.put("LTC", new BigDecimal("0.089"));

    return blockedMock;
  }

  public static Map<String, BigDecimal> createParsedTradeAvailable() {
    Map<String, BigDecimal> availableMock = new HashMap<>();
    availableMock.put("PLN", new BigDecimal("1197.00000000"));
    availableMock.put("EUR", new BigDecimal("0.00000000"));
    availableMock.put("BTC", new BigDecimal("27.01000000"));
    availableMock.put("LTC", new BigDecimal("0.00000000"));
    availableMock.put("DOGE", new BigDecimal("0.00000000"));
    availableMock.put("PPC", new BigDecimal("0.00000000"));
    availableMock.put("LiteMineX", new BigDecimal("0.00000000"));

    return availableMock;
  }

  public static Map<String, BigDecimal> createParsedTradeBlocked() {
    Map<String, BigDecimal> blockedMock = new HashMap<>();
    blockedMock.put("PLN", new BigDecimal("570.00000000"));
    blockedMock.put("BTC", new BigDecimal("3.05000000"));
    blockedMock.put("EUR", new BigDecimal("0.00000000"));
    blockedMock.put("LTC", new BigDecimal("0.00000000"));
    blockedMock.put("LiteMineX", new BigDecimal("0.00000000"));

    return blockedMock;
  }

  public Map<String, Map<String, List<BitMarketOrder>>> createOpenOrdersData() throws IOException {
    {
      BitMarketOrdersResponse response = parse("trade/example-orders-data", BitMarketOrdersResponse.class);
      return response.getData();
    }
  }
}
