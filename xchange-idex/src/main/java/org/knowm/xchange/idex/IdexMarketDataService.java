package org.knowm.xchange.idex;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HttpsURLConnection;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.idex.dto.*;
import org.knowm.xchange.idex.service.ReturnOrderBookApi;
import org.knowm.xchange.idex.service.ReturnTickerApi;
import org.knowm.xchange.idex.service.ReturnTradeHistoryApi;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import si.mazi.rescu.RestProxyFactory;

public class IdexMarketDataService extends BaseExchangeService implements MarketDataService {

  private ReturnTickerApi returnTickerApi;

  private ReturnOrderBookApi returnOrderBookApi;

  private ReturnTradeHistoryApi returnTradeHistoryApi;

  public IdexMarketDataService(IdexExchange idexExchange) {

    super(idexExchange);

    returnTickerApi =
        RestProxyFactory.createProxy(
            ReturnTickerApi.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());

    returnOrderBookApi =
        RestProxyFactory.createProxy(
            ReturnOrderBookApi.class,
            exchange.getDefaultExchangeSpecification().getSslUri(),
            getClientConfig());

    returnTradeHistoryApi =
        RestProxyFactory.createProxy(
            ReturnTradeHistoryApi.class,
            exchange.getDefaultExchangeSpecification().getSslUri(),
            getClientConfig());
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) {

    ReturnTickerApi proxy = returnTickerApi;
    Ticker ret = null;

    Market market = new Market();
    String market1 = IdexExchange.Companion.getMarket(currencyPair);
    Market market2 = market.market(market1);
    ReturnTickerResponse ticker = null;

    try {
      ticker = proxy.ticker(market2);
    } catch (Exception e) {
      System.err.println(e);
    }

    ret =
        new Ticker.Builder()
            .currencyPair(currencyPair)
            .last(IdexExchange.Companion.safeParse(ticker.getLast()))
            .ask(IdexExchange.Companion.safeParse(ticker.getLowestAsk()))
            .bid(IdexExchange.Companion.safeParse(ticker.getHighestBid()))
            .volume(IdexExchange.Companion.safeParse(ticker.getBaseVolume()))
            .quoteVolume(IdexExchange.Companion.safeParse(ticker.getQuoteVolume()))
            .high(IdexExchange.Companion.safeParse(ticker.getHigh()))
            .low(IdexExchange.Companion.safeParse(ticker.getLow()))
            .build();

    return ret;
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) {
    OrderBook ret = null;
    try {
      ReturnOrderBookResponse returnOrderBookResponse =
          returnOrderBookApi.orderBook(
              new OrderBookReq().market(IdexExchange.Companion.getMarket(currencyPair)));
      ret =
          new OrderBook(
              new Date(),
              returnOrderBookResponse.getAsks().stream()
                  .map(
                      ask -> {
                        BigDecimal limitPrice = IdexExchange.Companion.safeParse(ask.getPrice());
                        BigDecimal originalAmount =
                            IdexExchange.Companion.safeParse(ask.getAmount());
                        String orderHash = ask.getOrderHash();
                        return new LimitOrder.Builder(Order.OrderType.ASK, currencyPair)
                            .limitPrice(limitPrice)
                            .originalAmount(originalAmount)
                            .id(orderHash)
                            .build();
                      }),
              returnOrderBookResponse.getBids().stream()
                  .map(
                      bid -> {
                        BigDecimal limitPrice = IdexExchange.Companion.safeParse(bid.getPrice());
                        BigDecimal originalAmount =
                            IdexExchange.Companion.safeParse(bid.getAmount());
                        String orderHash = bid.getOrderHash();
                        return new LimitOrder.Builder(Order.OrderType.ASK, currencyPair)
                            .limitPrice(limitPrice)
                            .originalAmount(originalAmount)
                            .id(orderHash)
                            .build();
                      }));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ret;
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) {
    Trades ret = null;
    try {
      ret =
          new Trades(
              returnTradeHistoryApi
                  .tradeHistory(
                      new TradeHistoryReq().market(IdexExchange.Companion.getMarket(currencyPair)))
                  .stream()
                  .map(
                      tradeHistoryItem ->
                          new Trade.Builder()
                              .originalAmount(
                                  IdexExchange.Companion.safeParse(tradeHistoryItem.getAmount()))
                              .price(IdexExchange.Companion.safeParse(tradeHistoryItem.getPrice()))
                              .currencyPair(currencyPair)
                              .timestamp(
                                  new Date(tradeHistoryItem.getTimestamp().longValue() * 1000))
                              .id((tradeHistoryItem.getTransactionHash()))
                              .type(
                                  tradeHistoryItem.getType() == IdexBuySell.BUY
                                      ? Order.OrderType.BID
                                      : Order.OrderType.ASK)
                              .build())
                  .sorted(Comparator.comparing(Trade::getTimestamp))
                  .collect(Collectors.toList()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ret;
  }

  public static class Companion {

    private static List<Currency> allBase;
    private List<Currency> allCounter;

    public Companion() {}

    static final List<Currency> getAllBase() {
      if (null == Companion.allBase)
        Companion.allBase =
            Companion.allTickers.keySet().stream()
                .map(it -> it.split("_")[1])
                .distinct()
                .sorted()
                .map(Currency::getInstance)
                .collect(Collectors.toList());
      return Companion.allBase;
    }

    public List<Currency> getAllCounter() {
      if (allCounter == null)
        allCounter =
            Companion.allTickers.keySet().stream()
                .map((String key) -> key.split("_")[0])
                .distinct()
                .sorted()
                .map(Currency::getInstance)
                .collect(Collectors.toList());
      return allCounter;
    }

    static ReturnTickerRequestedWithNull allTickers;

    static {
      try {
        Companion.allTickers = Companion.allTickersStatic();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    /** same as curl -XPOST https://api.idex.market/returnTicker */
    static ReturnTickerRequestedWithNull allTickersStatic() throws IOException {
      ReturnTickerRequestedWithNull o = null;
      javax.net.ssl.HttpsURLConnection c = null;

      c = (HttpsURLConnection) new URL("https://api.idex.market/returnTicker").openConnection();
      c.setRequestMethod("POST");
      c.setRequestProperty("Accept-Encoding", "gzip");
      c.setRequestProperty("User-Agent", "irrelevant");
      ObjectMapper objectMapper = new ObjectMapper();

      try (InputStream inputStream = c.getInputStream();
          GZIPInputStream in = new GZIPInputStream(inputStream);
          InputStreamReader inputStreamReader = new InputStreamReader(in)) {
        o =
            objectMapper
                .readerFor(ReturnTickerRequestedWithNull.class)
                .readValue(inputStreamReader);
      }

      return o;
    }

    static ReturnCurrenciesResponse allCurrenciesStatic() throws IOException {
      HttpsURLConnection c =
          (HttpsURLConnection) new URL("https://api.idex.market/returnCurrencies").openConnection();
      c.setRequestMethod("POST");
      c.setRequestProperty("Accept-Encoding", "gzip");
      c.setRequestProperty("User-Agent", "irrelevant");
      try (InputStreamReader inputStreamReader =
          new InputStreamReader(new GZIPInputStream(c.getInputStream()))) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readerFor(ReturnCurrenciesResponse.class).readValue(inputStreamReader);
      }
    }
  }
}
