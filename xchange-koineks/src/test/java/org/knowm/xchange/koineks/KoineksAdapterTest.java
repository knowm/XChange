package org.knowm.xchange.koineks;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.koineks.dto.marketdata.KoineksTicker;

/** @author semihunaldi */
public class KoineksAdapterTest {

  @Test
  public void testTickerAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        KoineksAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/koineks/dto/marketdata/example-ticker-data.json");
    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KoineksTicker paribuTicker = mapper.readValue(is, KoineksTicker.class);

    Ticker ticker = KoineksAdapters.adaptTicker(paribuTicker, new CurrencyPair("BTC", "TRY"));
    assertThat(ticker).isNotNull();
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("47423.00"));
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("47400.00"));
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("47303.00"));
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("24.62"));
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("47500.00"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("46500.00"));
    assertThat(ticker.getTimestamp().getTime()).isEqualTo(1512481980L);
  }
}
