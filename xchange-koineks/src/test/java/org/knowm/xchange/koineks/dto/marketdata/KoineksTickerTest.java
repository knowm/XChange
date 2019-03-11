package org.knowm.xchange.koineks.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;

/** @author semihunaldi */
public class KoineksTickerTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KoineksTickerTest.class.getResourceAsStream(
            "/org/knowm/xchange/koineks/dto/marketdata/example-ticker-data.json");
    ObjectMapper mapper = new ObjectMapper();
    KoineksTicker koineksTicker = mapper.readValue(is, KoineksTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(koineksTicker).isNotNull();
    assertThat(koineksTicker.getKoineksBTCTicker()).isNotNull();
    assertThat(koineksTicker.getKoineksETHTicker()).isNotNull();
    assertThat(koineksTicker.getKoineksLTCTicker()).isNotNull();
    assertThat(koineksTicker.getKoineksDASHTicker()).isNotNull();
    assertThat(koineksTicker.getKoineksDOGETicker()).isNotNull();

    KoineksBTCTicker btcTicker = koineksTicker.getKoineksBTCTicker();
    assertThat(btcTicker.getCurrent()).isEqualTo(new BigDecimal("47423.00"));
    assertThat(btcTicker.getAsk()).isEqualTo(new BigDecimal("47400.00"));
    assertThat(btcTicker.getBid()).isEqualTo(new BigDecimal("47303.00"));
    assertThat(btcTicker.getVolume()).isEqualTo(new BigDecimal("24.62"));
    assertThat(btcTicker.getHigh()).isEqualTo(new BigDecimal("47500.00"));
    assertThat(btcTicker.getLow()).isEqualTo(new BigDecimal("46500.00"));
    assertThat(btcTicker.getTimestamp()).isEqualTo("1512481980");
    assertThat(btcTicker.getChangeAmount()).isEqualTo("+423.00");
    assertThat(btcTicker.getChangePercentage()).isEqualTo("0.9");
    assertThat(btcTicker.getShortCode()).isEqualTo(Currency.BTC);
    assertThat(btcTicker.getName()).isEqualTo("Bitcoin");
    assertThat(btcTicker.getCurrency()).isEqualTo(Currency.TRY);
  }
}
