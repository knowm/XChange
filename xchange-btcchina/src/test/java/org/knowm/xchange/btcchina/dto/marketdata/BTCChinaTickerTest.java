package org.knowm.xchange.btcchina.dto.marketdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BTCChinaTickerTest {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testSingleMarket() throws IOException {

    BTCChinaTicker tickerResponse = objectMapper.readValue(getClass().getResourceAsStream("ticker-single-market.json"), BTCChinaTicker.class);
    BTCChinaTickerObject ticker = tickerResponse.getTicker();
    assertEquals(new BigDecimal("2894.97"), ticker.getHigh());
    assertEquals(new BigDecimal("2850.08"), ticker.getLow());
    assertEquals(new BigDecimal("2876.92"), ticker.getBuy());
    assertEquals(new BigDecimal("2883.80"), ticker.getSell());
    assertEquals(new BigDecimal("2875.66"), ticker.getLast());
    assertEquals(new BigDecimal("4133.63800000"), ticker.getVol());
    assertEquals(1396412995L, ticker.getDate());

    ticker = tickerResponse.get("ticker");
    assertNull(ticker);
  }

  @Test
  public void testAllMarket() throws IOException {

    BTCChinaTicker tickerResponse = objectMapper.readValue(getClass().getResourceAsStream("ticker-all-market.json"), BTCChinaTicker.class);

    assertEquals(3, tickerResponse.size());

    BTCChinaTickerObject btccny = tickerResponse.get("ticker_btccny");
    BTCChinaTickerObject ltccny = tickerResponse.get("ticker_ltccny");
    BTCChinaTickerObject ltcbtc = tickerResponse.get("ticker_ltcbtc");

    assertEquals(new BigDecimal("2894.97"), btccny.getHigh());
    assertEquals(new BigDecimal("2850.08"), btccny.getLow());
    assertEquals(new BigDecimal("2880.00"), btccny.getBuy());
    assertEquals(new BigDecimal("2883.86"), btccny.getSell());
    assertEquals(new BigDecimal("2880.00"), btccny.getLast());
    assertEquals(new BigDecimal("4164.41040000"), btccny.getVol());
    assertEquals(1396412841L, btccny.getDate());

    assertEquals(new BigDecimal("78.80"), ltccny.getHigh());
    assertEquals(new BigDecimal("77.50"), ltccny.getLow());
    assertEquals(new BigDecimal("78.22"), ltccny.getBuy());
    assertEquals(new BigDecimal("78.35"), ltccny.getSell());
    assertEquals(new BigDecimal("78.35"), ltccny.getLast());
    assertEquals(new BigDecimal("56443.71000000"), ltccny.getVol());
    assertEquals(1396412841L, btccny.getDate());

    assertEquals(new BigDecimal("0.02800000"), ltcbtc.getHigh());
    assertEquals(new BigDecimal("0.02710000"), ltcbtc.getLow());
    assertEquals(new BigDecimal("0.02720000"), ltcbtc.getBuy());
    assertEquals(new BigDecimal("0.02730000"), ltcbtc.getSell());
    assertEquals(new BigDecimal("0.02720000"), ltcbtc.getLast());
    assertEquals(new BigDecimal("7715.69400000"), ltcbtc.getVol());
    assertEquals(1396412841L, ltcbtc.getDate());
  }

}
