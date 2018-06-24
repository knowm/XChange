package org.knowm.xchange.bitcoinaverage;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTicker;
import org.knowm.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTickerJSONTest;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

/** Tests the BitcoinAverageAdapter class */
public class BitcoinAverageAdapterTest {

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitcoinAverageTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinaverage/dto/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinAverageTicker BitcoinAverageTicker = mapper.readValue(is, BitcoinAverageTicker.class);

    Ticker ticker = BitcoinAverageAdapters.adaptTicker(BitcoinAverageTicker, CurrencyPair.BTC_USD);
    System.out.println(ticker.toString());

    assertThat(ticker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(ticker.getLast().toString()).isEqualTo("629.45");
    assertThat(ticker.getBid().toString()).isEqualTo("628.2");
    assertThat(ticker.getAsk().toString()).isEqualTo("631.21");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("118046.63"));
  }
}
