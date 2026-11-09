package org.knowm.xchange.zaif;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.zaif.dto.marketdata.ZaifFullBook;

class ZaifAdaptersTest {

  @Test
  void fullBookAdapter() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        ZaifAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/zaif/marketdata/example-fullbook-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    ZaifFullBook zaifFullBook = mapper.readValue(is, ZaifFullBook.class);

    OrderBook orderBook = ZaifAdapters.adaptOrderBook(zaifFullBook, CurrencyPair.BTC_JPY);

    assertThat(orderBook.getBids().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_JPY);

    assertThat(orderBook.getBids().get(0).getLimitPrice()).isEqualTo("934920");
    assertThat(orderBook.getBids().get(0).getOriginalAmount()).isEqualTo("0.03");

    assertThat(orderBook.getAsks().get(0).getLimitPrice()).isEqualTo("935355");
    assertThat(orderBook.getAsks().get(0).getOriginalAmount()).isEqualTo("0.02");
  }
}
