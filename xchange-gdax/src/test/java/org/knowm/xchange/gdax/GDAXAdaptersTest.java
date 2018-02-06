package org.knowm.xchange.gdax;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductStats;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductTicker;
import org.knowm.xchange.gdax.dto.trade.GDAXFill;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.knowm.xchange.gdax.dto.trade.GDAXOrder;
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
    assertThat(ticker.getOpen().toString()).isEqualTo("254.04000000");
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
    assertThat(trade.getOriginalAmount()).isEqualTo("0.01000000");
    assertThat(trade.getOrderId()).isEqualTo("b4b3bbb1-e0e3-4532-9413-23123448ce35");
    assertThat(trade.getTimestamp().getTime()).isEqualTo(1493623910243L);
    assertThat(trade.getFeeAmount()).isEqualTo("0.0000017745000000");
    assertThat(trade.getType()).isEqualTo(OrderType.BID);
  }

  @Test
  public void testOrderStatusMarketOrderFilled() throws IOException {

    JacksonObjectMapperFactory factory = new DefaultJacksonObjectMapperFactory();
    ObjectMapper mapper = factory.createObjectMapper();

    InputStream is = getClass().getResourceAsStream("/order/example-market-order-filled.json");
    GDAXOrder gdaxOrder = mapper.readValue(is, GDAXOrder.class);

    Order order = GDAXAdapters.adaptOrder(gdaxOrder);

    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.FILLED);
    assertThat(order.getId()).isEqualTo(gdaxOrder.getId());
    assertThat(order.getCurrencyPair()).isEqualTo((CurrencyPair.BTC_USD));
    assertThat(order.getOriginalAmount().equals(new BigDecimal("1.00000000"))).isTrue();
    assertThat(order.getCumulativeAmount()).isEqualTo(new BigDecimal("0.01291771"));
    assertThat(order.getRemainingAmount()).isEqualTo(new BigDecimal("1.0").subtract(new BigDecimal("0.01291771")));
    assertThat(order.getFee()).isEqualTo(new BigDecimal("0.0249376391550000"));
    assertThat(MarketOrder.class.isAssignableFrom(order.getClass())).isTrue();
    assertThat(order.getType()).isEqualTo(OrderType.BID);
    assertThat(order.getTimestamp()).isEqualTo(new Date(1481227745508L));
    assertThat(order.getAveragePrice()).isEqualTo(new BigDecimal("9.9750556620000000").divide(new BigDecimal("0.01291771"), new MathContext(8)));
  }


  @Test
  public void testOrderStatusLimitOrderFilled() throws IOException {

    JacksonObjectMapperFactory factory = new DefaultJacksonObjectMapperFactory();
    ObjectMapper mapper = factory.createObjectMapper();

    InputStream is = getClass().getResourceAsStream("/order/example-limit-order-filled.json");
    GDAXOrder gdaxOrder = mapper.readValue(is, GDAXOrder.class);


    Order order = GDAXAdapters.adaptOrder(gdaxOrder);


    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.FILLED);
    assertThat(order.getId()).isEqualTo("b2cdd7fe-1f4a-495e-8b96-7a4be368f43c");
    assertThat(order.getCurrencyPair()).isEqualTo((CurrencyPair.BTC_USD));
    assertThat(order.getOriginalAmount().equals(new BigDecimal("0.07060351"))).isTrue();
    assertThat(order.getCumulativeAmount()).isEqualTo(new BigDecimal("0.07060351"));
    assertThat(order.getRemainingAmount()).isEqualTo(new BigDecimal("0.00000000"));
    assertThat(order.getFee()).isEqualTo(new BigDecimal("2.6256545174247500"));
    assertThat(LimitOrder.class.isAssignableFrom(order.getClass())).isTrue();
    assertThat(order.getType()).isEqualTo(OrderType.ASK);
    assertThat(order.getTimestamp()).isEqualTo(new Date(1515434144454L));
    assertThat(order.getAveragePrice()).isEqualTo(new BigDecimal("1050.2618069699000000").divide(new BigDecimal("0.07060351"), new MathContext(8)));


  }

}
