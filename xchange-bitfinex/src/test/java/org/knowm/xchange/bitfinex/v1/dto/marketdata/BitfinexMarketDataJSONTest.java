package org.knowm.xchange.bitfinex.v1.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitfinex.service.BitfinexAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;

class BitfinexMarketDataJSONTest {

  @Test
  void lendbookMarketData() throws Exception {

    InputStream resourceAsStream =
        BitfinexMarketDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitfinex/v1/dto/marketdata/example-marketdepth-lendbook-data.json");
    BitfinexLendDepth lendDepth =
        new ObjectMapper().readValue(resourceAsStream, BitfinexLendDepth.class);

    assertThat(lendDepth.getAsks().length).isEqualTo(50);
    assertThat(lendDepth.getBids().length).isEqualTo(50);
  }

  @Test
  void marketDepth() throws Exception {

    InputStream resourceAsStream =
        BitfinexMarketDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitfinex/v1/dto/marketdata/example-marketdepth-data.json");
    BitfinexDepth depthRaw = new ObjectMapper().readValue(resourceAsStream, BitfinexDepth.class);
    BitfinexAdapters.OrdersContainer asksOrdersContainer =
        BitfinexAdapters.adaptOrders(depthRaw.getAsks(), CurrencyPair.BTC_EUR, OrderType.ASK);
    BitfinexAdapters.OrdersContainer bidsOrdersContainer =
        BitfinexAdapters.adaptOrders(depthRaw.getBids(), CurrencyPair.BTC_EUR, OrderType.BID);

    assertThat(asksOrdersContainer.getLimitOrders().get(0).getLimitPrice())
        .isEqualTo(new BigDecimal("851.87"));
    assertThat(bidsOrdersContainer.getLimitOrders().get(0).getLimitPrice())
        .isEqualTo(new BigDecimal("849.59"));

    assertThat(asksOrdersContainer.getTimestamp()).isEqualTo(1387060950000L);
    assertThat(bidsOrdersContainer.getTimestamp()).isEqualTo(1387060435000L);
  }
}
