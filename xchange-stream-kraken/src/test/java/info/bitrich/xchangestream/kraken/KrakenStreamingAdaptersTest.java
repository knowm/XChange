package info.bitrich.xchangestream.kraken;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
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

  @Test
  public void testAdaptOrderbookMessageWithSnapshot() throws IOException {
    JsonNode jsonNode =
        StreamingObjectMapperHelper.getObjectMapper()
            .readTree(this.getClass().getResource("/orderBookMessageSnapshot.json").openStream());
    OrderBook beforeUpdate = new OrderBook(null, new ArrayList<>(), new ArrayList<>());
    Assert.assertNotNull(jsonNode);
    OrderBook afterUpdate =
        KrakenStreamingAdapters.adaptOrderbookMessage(beforeUpdate, XBT_EUR, (ArrayNode) jsonNode);

    assertThat(afterUpdate).isNotNull();
    assertThat(afterUpdate.getAsks()).isNotNull();
    assertThat(afterUpdate.getBids()).isNotNull();

    assertThat(afterUpdate.getAsks()).hasSize(25);
    assertThat(afterUpdate.getBids()).hasSize(25);

    LimitOrder firstAsk = afterUpdate.getAsks().get(0);
    assertThat(firstAsk.getLimitPrice()).isEqualByComparingTo("8692");
    assertThat(firstAsk.getOriginalAmount()).isEqualByComparingTo("2.01122372");
    assertThat(firstAsk.getTimestamp().getTime()).isEqualByComparingTo(1561120269000L);

    LimitOrder firstBid = afterUpdate.getBids().get(0);
    assertThat(firstBid.getLimitPrice()).isEqualByComparingTo("8691.9");
    assertThat(firstBid.getOriginalAmount()).isEqualByComparingTo("1.45612927");
    assertThat(firstBid.getTimestamp().getTime()).isEqualByComparingTo(1561120266000L);
  }

  @Test
  public void testAdaptOrderbookMessageWithUpdate() throws IOException {
    JsonNode jsonNode =
        StreamingObjectMapperHelper.getObjectMapper()
            .readTree(this.getClass().getResource("/orderBookMessageUpdate.json").openStream());
    Assert.assertNotNull(jsonNode);
    OrderBook beforeUpdate = new OrderBook(null, new ArrayList<>(), new ArrayList<>());
    OrderBook afterUpdate =
        KrakenStreamingAdapters.adaptOrderbookMessage(beforeUpdate, XBT_EUR, (ArrayNode) jsonNode);

    assertThat(afterUpdate).isNotNull();
    assertThat(afterUpdate.getAsks()).isNotNull();
    assertThat(afterUpdate.getBids()).isNotNull();

    assertThat(afterUpdate.getAsks()).hasSize(1);
    assertThat(afterUpdate.getBids()).hasSize(0);

    LimitOrder firstAsk = afterUpdate.getAsks().get(0);
    assertThat(firstAsk.getLimitPrice()).isEqualByComparingTo("9621");
    assertThat(firstAsk.getOriginalAmount()).isEqualByComparingTo("1.36275258");
    assertThat(firstAsk.getTimestamp().getTime()).isEqualByComparingTo(1561372897000L);
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
    assertThat(trade.getTimestamp().getTime()).isEqualTo(1534614057000L);
    assertThat(trade.getType()).isEqualTo(Order.OrderType.ASK);

    trade = trades.get(1);
    assertThat(trade.getPrice()).isEqualByComparingTo("6060.00000");
    assertThat(trade.getOriginalAmount()).isEqualByComparingTo("0.02455000");
    assertThat(trade.getTimestamp().getTime()).isEqualTo(1534614057000L);
    assertThat(trade.getType()).isEqualTo(Order.OrderType.BID);
  }
}
