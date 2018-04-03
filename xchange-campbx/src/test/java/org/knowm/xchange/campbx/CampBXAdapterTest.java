package org.knowm.xchange.campbx;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.campbx.dto.marketdata.CampBXOrderBook;
import org.knowm.xchange.campbx.dto.marketdata.CampBXTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;

/** Tests the BitstampAdapter class */
public class CampBXAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CampBXAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/campbx/marketdata/example-full-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CampBXOrderBook bitstampOrderBook = mapper.readValue(is, CampBXOrderBook.class);

    OrderBook orderBook = CampBXAdapters.adaptOrders(bitstampOrderBook, CurrencyPair.BTC_USD);
    assertThat(orderBook.getBids().size()).isEqualTo(36);

    // verify all fields filled
    assertThat(orderBook.getBids().get(0).getLimitPrice().toString()).isEqualTo("13.3");
    assertThat(orderBook.getBids().get(0).getType()).isEqualTo(OrderType.BID);
    assertThat(orderBook.getBids().get(0).getOriginalAmount())
        .isEqualTo(new BigDecimal("0.00021609"));
    assertThat(orderBook.getBids().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CampBXAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/campbx/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CampBXTicker campBXTicker = mapper.readValue(is, CampBXTicker.class);

    Ticker ticker = CampBXAdapters.adaptTicker(campBXTicker, CurrencyPair.BTC_USD);

    assertThat(ticker.getLast()).isEqualTo("13.30");
    assertThat(ticker.getBid()).isEqualTo("13.30");
    assertThat(ticker.getAsk()).isEqualTo("13.52");
  }
}
