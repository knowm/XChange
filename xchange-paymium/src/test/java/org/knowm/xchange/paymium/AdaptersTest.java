package org.knowm.xchange.paymium;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.paymium.dto.marketdata.PaymiumMarketDepth;
import org.knowm.xchange.paymium.dto.marketdata.PaymiumTicker;
import org.knowm.xchange.paymium.dto.marketdata.PaymiumTrade;

class AdaptersTest {

  @Test
  void paymiumTickerRequest() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        AdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/paymium/dto/marketdata/Example_TickerData.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    PaymiumTicker PaymiumTicker = mapper.readValue(is, PaymiumTicker.class);

    Ticker genericTicker = PaymiumAdapters.adaptTicker(PaymiumTicker, CurrencyPair.BTC_EUR);

    assertThat(new BigDecimal("20.4")).isEqualTo(genericTicker.getAsk());
    assertThat(new BigDecimal("20.1")).isEqualTo(genericTicker.getBid());
    assertThat(new BigDecimal("20.74")).isEqualTo(genericTicker.getHigh());
    assertThat(new BigDecimal("20.2")).isEqualTo(genericTicker.getLow());
    assertThat(new BigDecimal("20.2")).isEqualTo(genericTicker.getLast());
    assertThat(new BigDecimal("148.80193218")).isEqualTo(genericTicker.getVolume());
  }

  @Test
  void paymiumDepthRequest() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        AdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/paymium/dto/marketdata/Example_DepthData.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    PaymiumMarketDepth PaymiumMarketDepth = mapper.readValue(is, PaymiumMarketDepth.class);

    OrderBook genericOrderBook =
        PaymiumAdapters.adaptMarketDepth(PaymiumMarketDepth, CurrencyPair.BTC_EUR);

    assertThat(new BigDecimal("0.48762"))
        .isEqualTo(genericOrderBook.getAsks().get(0).getOriginalAmount());
    assertThat(new BigDecimal("24.48996"))
        .isEqualTo(genericOrderBook.getAsks().get(0).getLimitPrice());
    assertThat(new BigDecimal("0.40491093"))
        .isEqualTo(genericOrderBook.getBids().get(0).getOriginalAmount());
    assertThat(new BigDecimal("24.001"))
        .isEqualTo(genericOrderBook.getBids().get(0).getLimitPrice());
  }

  @Test
  void paymiumTradesRequest() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        AdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/paymium/dto/marketdata/Example_TradesData.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    PaymiumTrade[] PaymiumTrades = mapper.readValue(is, PaymiumTrade[].class);

    Trades genericTrades = PaymiumAdapters.adaptTrade(PaymiumTrades, CurrencyPair.BTC_EUR);

    assertThat(new BigDecimal("5.0")).isEqualTo(genericTrades.getTrades().get(0).getPrice());
    assertThat(new BigDecimal("980.0"))
        .isEqualTo(genericTrades.getTrades().get(0).getOriginalAmount());
  }
}
