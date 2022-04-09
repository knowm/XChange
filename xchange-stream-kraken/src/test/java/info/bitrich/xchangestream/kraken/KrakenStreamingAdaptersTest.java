package info.bitrich.xchangestream.kraken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.currency.CurrencyPair.XBT_USD;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Sets;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import java.io.IOException;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;

public class KrakenStreamingAdaptersTest {

  private static final CurrencyPair XBT_EUR = new CurrencyPair(Currency.XBT, Currency.EUR);

  private TreeSet<LimitOrder> bids;
  private TreeSet<LimitOrder> asks;

  @Before
  public void setUp() throws Exception {
    bids = Sets.newTreeSet(Comparator.reverseOrder());
    asks = Sets.newTreeSet();
  }

  @Test
  public void testAdaptOrderbookMessageWithSnapshot() throws IOException {
    JsonNode jsonNode =
        StreamingObjectMapperHelper.getObjectMapper()
            .readTree(this.getClass().getResource("/orderBookMessageSnapshot.json").openStream());
    Assert.assertNotNull(jsonNode);
    OrderBook afterUpdate =
        KrakenStreamingAdapters.adaptOrderbookMessage(
            100, bids, asks, XBT_USD, (ArrayNode) jsonNode);

    assertThat(afterUpdate).isNotNull();
    assertThat(afterUpdate.getAsks()).isNotNull();
    assertThat(afterUpdate.getBids()).isNotNull();
    assertThat(afterUpdate.getTimeStamp()).isEqualTo(Instant.ofEpochMilli(1534614248765L));

    assertThat(afterUpdate.getAsks()).hasSize(3);
    assertThat(afterUpdate.getBids()).hasSize(3);

    LimitOrder firstAsk = afterUpdate.getAsks().get(0);
    assertThat(firstAsk.getLimitPrice()).isEqualByComparingTo("5541.30000");
    assertThat(firstAsk.getOriginalAmount()).isEqualByComparingTo("2.50700000");

    LimitOrder firstBid = afterUpdate.getBids().get(0);
    assertThat(firstBid.getLimitPrice()).isEqualByComparingTo("5541.20000");
    assertThat(firstBid.getOriginalAmount()).isEqualByComparingTo("1.52900000");
  }

  @Test
  public void testAdaptOrderbookMessageWithUpdate() throws IOException {
    JsonNode jsonNode =
        StreamingObjectMapperHelper.getObjectMapper()
            .readTree(this.getClass().getResource("/orderBookMessageSnapshot.json").openStream());
    Assert.assertNotNull(jsonNode);
    KrakenStreamingAdapters.adaptOrderbookMessage(100, bids, asks, XBT_USD, (ArrayNode) jsonNode);

    jsonNode =
        StreamingObjectMapperHelper.getObjectMapper()
            .readTree(this.getClass().getResource("/orderBookMessageUpdate00.json").openStream());
    KrakenStreamingAdapters.adaptOrderbookMessage(100, bids, asks, XBT_USD, (ArrayNode) jsonNode);

    jsonNode =
        StreamingObjectMapperHelper.getObjectMapper()
            .readTree(this.getClass().getResource("/orderBookMessageUpdate01.json").openStream());
    KrakenStreamingAdapters.adaptOrderbookMessage(100, bids, asks, XBT_USD, (ArrayNode) jsonNode);

    jsonNode =
        StreamingObjectMapperHelper.getObjectMapper()
            .readTree(this.getClass().getResource("/orderBookMessageUpdate02.json").openStream());
    OrderBook afterUpdate =
        KrakenStreamingAdapters.adaptOrderbookMessage(
            100, bids, asks, XBT_USD, (ArrayNode) jsonNode);

    assertThat(afterUpdate).isNotNull();
    assertThat(afterUpdate.getAsks()).isNotNull();
    assertThat(afterUpdate.getBids()).isNotNull();
    assertThat(afterUpdate.getTimeStamp()).isEqualTo(Instant.ofEpochMilli(1534614248765L));

    assertThat(afterUpdate.getAsks()).hasSize(4);
    assertThat(afterUpdate.getBids()).hasSize(3);

    LimitOrder firstAsk = afterUpdate.getAsks().get(0);
    assertThat(firstAsk.getLimitPrice()).isEqualByComparingTo("5541.30000");
    assertThat(firstAsk.getOriginalAmount()).isEqualByComparingTo("2.50700000");
  }

  @Test
  public void testAdaptTickerMessage() throws IOException {
    JsonNode jsonNode =
        StreamingObjectMapperHelper.getObjectMapper()
            .readTree(this.getClass().getResource("/ticker.json").openStream());
    Assert.assertNotNull(jsonNode);
    Ticker ticker = KrakenStreamingAdapters.adaptTickerMessage(XBT_EUR, (ArrayNode) jsonNode);

    assertThat(ticker).isNotNull();
    assertThat(ticker.getOpen()).isEqualByComparingTo("9668.90000");
    assertThat(ticker.getAsk()).isEqualByComparingTo("9971.00000");
    assertThat(ticker.getBid()).isEqualByComparingTo("9970.90000");
    assertThat(ticker.getHigh()).isEqualByComparingTo("9990.00000");
    assertThat(ticker.getLow()).isEqualByComparingTo("9653.20000");
    assertThat(ticker.getVwap()).isEqualByComparingTo("9732.48471");
    assertThat(ticker.getVolume()).isEqualByComparingTo("7889.33379900");
    assertThat(ticker.getInstrument()).isEqualTo(XBT_EUR);
  }

  @Test
  public void testAdaptTrades() throws IOException {
    JsonNode jsonNode =
        StreamingObjectMapperHelper.getObjectMapper()
            .readTree(this.getClass().getResource("/trades.json").openStream());
    Assert.assertNotNull(jsonNode);
    List<Trade> trades = KrakenStreamingAdapters.adaptTrades(XBT_EUR, jsonNode);

    assertThat(trades).hasSize(2);

    Trade trade = trades.get(0);
    assertThat(trade.getPrice()).isEqualByComparingTo("5541.20000");
    assertThat(trade.getOriginalAmount()).isEqualByComparingTo("0.15850568");
    assertThat(trade.getTimestamp().getTime()).isEqualTo(1534614057321L);
    assertThat(trade.getType()).isEqualTo(Order.OrderType.ASK);

    trade = trades.get(1);
    assertThat(trade.getPrice()).isEqualByComparingTo("6060.00000");
    assertThat(trade.getOriginalAmount()).isEqualByComparingTo("0.02455000");
    assertThat(trade.getTimestamp().getTime()).isEqualTo(1534614057324L);
    assertThat(trade.getType()).isEqualTo(Order.OrderType.BID);
  }
}
