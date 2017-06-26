package org.knowm.xchange.gdax;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductStats;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductTicker;
import org.knowm.xchange.gdax.dto.trade.GDAXFill;

import com.fasterxml.jackson.databind.ObjectMapper;

import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

/**
 * Created by Yingzhe on 4/8/2015.
 */
public class GDAXAdaptersTest {

  @Test
  public void parseDateTest() {
    assertThat(GDAXAdapters.parseDate("2017-05-02T15:10:03Z").getTime()).isEqualTo(1493737803000L);
    assertThat(GDAXAdapters.parseDate("2017-05-02T15:10:03.1Z").getTime()).isEqualTo(1493737803100L);
    assertThat(GDAXAdapters.parseDate("2017-05-02T15:10:03.12Z").getTime()).isEqualTo(1493737803120L);
    assertThat(GDAXAdapters.parseDate("2017-05-02T15:10:03.123Z").getTime()).isEqualTo(1493737803123L);
    assertThat(GDAXAdapters.parseDate("2017-05-02T15:10:03.1234567Z").getTime()).isEqualTo(1493737803123L);

    assertThat(GDAXAdapters.parseDate("2017-05-02T15:10:03").getTime()).isEqualTo(1493737803000L);
    assertThat(GDAXAdapters.parseDate("2017-05-02T15:10:03.1").getTime()).isEqualTo(1493737803100L);
    assertThat(GDAXAdapters.parseDate("2017-05-02T15:10:03.12").getTime()).isEqualTo(1493737803120L);
    assertThat(GDAXAdapters.parseDate("2017-05-02T15:10:03.123").getTime()).isEqualTo(1493737803123L);
    assertThat(GDAXAdapters.parseDate("2017-05-02T15:10:03.123456").getTime()).isEqualTo(1493737803123L);

    assertThat(GDAXAdapters.parseDate("2017-06-21T04:52:01.996Z").getTime()).isEqualTo(1498020721996L);
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = GDAXAdaptersTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");
    InputStream is2 = GDAXAdaptersTest.class.getResourceAsStream("/marketdata/example-stats-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GDAXProductTicker coinbaseExTicker = mapper.readValue(is, GDAXProductTicker.class);
    GDAXProductStats coinbaseExStats = mapper.readValue(is2, GDAXProductStats.class);

    Ticker ticker = GDAXAdapters.adaptTicker(coinbaseExTicker, coinbaseExStats, CurrencyPair.BTC_USD);

    assertThat(ticker.getLast().toString()).isEqualTo("246.28000000");
    assertThat(ticker.getBid().toString()).isEqualTo("637");
    assertThat(ticker.getAsk().toString()).isEqualTo("637.11");
    assertThat(ticker.getHigh().toString()).isEqualTo("255.47000000");
    assertThat(ticker.getLow().toString()).isEqualTo("244.29000000");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("4661.70407704"));
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f.format(ticker.getTimestamp());
    assertThat(dateString).isEqualTo("2015-04-08 20:49:06");
  }

  @Test
  public void testTradeHistoryAdapter() throws IOException {
    JacksonObjectMapperFactory factory = new DefaultJacksonObjectMapperFactory();
    ObjectMapper mapper = factory.createObjectMapper();

    InputStream is = getClass().getResourceAsStream("/trade/example-fills.json");
    GDAXFill[] fills = mapper.readValue(is, GDAXFill[].class);

    UserTrades trades = GDAXAdapters.adaptTradeHistory(fills);

    assertThat(trades.getUserTrades()).hasSize(1);

    UserTrade trade = trades.getUserTrades().get(0);

    assertThat(trade.getId()).isEqualTo("470768");
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.ETH_BTC);
    assertThat(trade.getPrice()).isEqualTo("0.05915000");
    assertThat(trade.getTradableAmount()).isEqualTo("0.01000000");
    assertThat(trade.getOrderId()).isEqualTo("b4b3bbb1-e0e3-4532-9413-23123448ce35");
    assertThat(trade.getTimestamp().getTime()).isEqualTo(1493623910243L);
    assertThat(trade.getFeeAmount()).isEqualTo("0.0000017745000000");
    assertThat(trade.getType()).isEqualTo(OrderType.BID);
  }
}
