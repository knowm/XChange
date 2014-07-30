package com.xeiam.xchange.btccentral;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.btccentral.dto.marketdata.BTCCentralMarketDepth;
import com.xeiam.xchange.btccentral.dto.marketdata.BTCCentralTicker;
import com.xeiam.xchange.btccentral.dto.marketdata.BTCCentralTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;

/**
 * @author ObsessiveOrange
 */
public class AdaptersTest {

  @Test
  public void testBTCCentralTickerRequest() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = AdaptersTest.class.getResourceAsStream("/Example_TickerData.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCCentralTicker btcCentralTicker = mapper.readValue(is, BTCCentralTicker.class);

    Ticker genericTicker = BTCCentralAdapters.adaptTicker(btcCentralTicker, CurrencyPair.BTC_EUR);

    assertEquals(genericTicker.getAsk(), new BigDecimal("20.4"));
    assertEquals(genericTicker.getBid(), new BigDecimal("20.1"));
    assertEquals(genericTicker.getHigh(), new BigDecimal("20.74"));
    assertEquals(genericTicker.getLow(), new BigDecimal("20.2"));
    assertEquals(genericTicker.getLast(), new BigDecimal("20.2"));
    assertEquals(genericTicker.getVolume(), new BigDecimal("148.80193218"));

  }

  @Test
  public void testBTCCentralDepthRequest() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = AdaptersTest.class.getResourceAsStream("/Example_DepthData.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCCentralMarketDepth btcCentralMarketDepth = mapper.readValue(is, BTCCentralMarketDepth.class);

    OrderBook genericOrderBook = BTCCentralAdapters.adaptMarketDepth(btcCentralMarketDepth, CurrencyPair.BTC_EUR);

    assertEquals(genericOrderBook.getAsks().get(0).getTradableAmount(), new BigDecimal("0.48762"));
    assertEquals(genericOrderBook.getAsks().get(0).getLimitPrice(), new BigDecimal("24.48996"));
    assertEquals(genericOrderBook.getBids().get(0).getTradableAmount(), new BigDecimal("0.77372456"));
    assertEquals(genericOrderBook.getBids().get(0).getLimitPrice(), new BigDecimal("24.05"));

  }

  @Test
  public void testBTCCentralTradesRequest() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = AdaptersTest.class.getResourceAsStream("/Example_TradesData.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCCentralTrade[] btcCentralTrades = mapper.readValue(is, BTCCentralTrade[].class);

    Trades genericTrades = BTCCentralAdapters.adaptTrade(btcCentralTrades, CurrencyPair.BTC_EUR);

    assertEquals(genericTrades.getTrades().get(0).getPrice(), new BigDecimal("5.0"));
    assertEquals(genericTrades.getTrades().get(0).getTradableAmount(), new BigDecimal("980.0"));

  }

}
