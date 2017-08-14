package org.knowm.xchange.hitbtc.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.fest.assertions.api.Assertions.assertThat;

public class HitbtcTickerJsonTest {

private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


  @Test
  public void testUnmarshal() throws IOException, ParseException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcTickerJsonTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    HitbtcTicker ticker = mapper.readValue(is, HitbtcTicker.class);

    assertThat(ticker.getAsk()).isEqualTo("4254.90");
    assertThat(ticker.getBid()).isEqualTo("4241.10");
    assertThat(ticker.getLast()).isEqualTo("4255.62");
    assertThat(ticker.getLow()).isEqualTo("3900.00");
    assertThat(ticker.getHigh()).isEqualTo("4333.13");
    assertThat(ticker.getOpen()).isEqualTo("4016.88");
    assertThat(ticker.getVolume()).isEqualTo("1751.81");
    assertThat(ticker.getVolumeQuote()).isEqualTo("7235939.9970");
    assertThat(ticker.getTimestamp()).isEqualTo(simpleDateFormat.parse("2017-08-14T12:14:33.839Z"));
  }

}
