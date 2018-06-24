package org.knowm.xchange.paymium;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.paymium.dto.marketdata.PaymiumMarketDepth;
import org.knowm.xchange.paymium.dto.marketdata.PaymiumTicker;
import org.knowm.xchange.paymium.dto.marketdata.PaymiumTrade;

public class AdaptersTest {

  @Test
  public void testPaymiumTickerRequest()
      throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is =
        AdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/paymium/dto/marketdata/Example_TickerData.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    PaymiumTicker PaymiumTicker = mapper.readValue(is, PaymiumTicker.class);

    Ticker genericTicker = PaymiumAdapters.adaptTicker(PaymiumTicker, CurrencyPair.BTC_EUR);

    assertEquals(genericTicker.getAsk(), new BigDecimal("20.4"));
    assertEquals(genericTicker.getBid(), new BigDecimal("20.1"));
    assertEquals(genericTicker.getHigh(), new BigDecimal("20.74"));
    assertEquals(genericTicker.getLow(), new BigDecimal("20.2"));
    assertEquals(genericTicker.getLast(), new BigDecimal("20.2"));
    assertEquals(genericTicker.getVolume(), new BigDecimal("148.80193218"));
  }

  @Test
  public void testPaymiumDepthRequest()
      throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is =
        AdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/paymium/dto/marketdata/Example_DepthData.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    PaymiumMarketDepth PaymiumMarketDepth = mapper.readValue(is, PaymiumMarketDepth.class);

    OrderBook genericOrderBook =
        PaymiumAdapters.adaptMarketDepth(PaymiumMarketDepth, CurrencyPair.BTC_EUR);

    assertEquals(genericOrderBook.getAsks().get(0).getOriginalAmount(), new BigDecimal("0.48762"));
    assertEquals(genericOrderBook.getAsks().get(0).getLimitPrice(), new BigDecimal("24.48996"));
    assertEquals(
        genericOrderBook.getBids().get(0).getOriginalAmount(), new BigDecimal("0.40491093"));
    assertEquals(genericOrderBook.getBids().get(0).getLimitPrice(), new BigDecimal("24.001"));
  }

  @Test
  public void testPaymiumTradesRequest()
      throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is =
        AdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/paymium/dto/marketdata/Example_TradesData.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    PaymiumTrade[] PaymiumTrades = mapper.readValue(is, PaymiumTrade[].class);

    Trades genericTrades = PaymiumAdapters.adaptTrade(PaymiumTrades, CurrencyPair.BTC_EUR);

    assertEquals(genericTrades.getTrades().get(0).getPrice(), new BigDecimal("5.0"));
    assertEquals(genericTrades.getTrades().get(0).getOriginalAmount(), new BigDecimal("980.0"));
  }
}
