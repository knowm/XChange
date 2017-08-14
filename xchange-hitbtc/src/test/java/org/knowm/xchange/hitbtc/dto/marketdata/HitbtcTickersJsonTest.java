package org.knowm.xchange.hitbtc.dto.marketdata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class HitbtcTickersJsonTest {

  private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


  @Test
  public void tesetUnmarshal() throws IOException, ParseException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcTickersJsonTest.class.getResourceAsStream("/marketdata/example-tickers-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();


    List<HitbtcTicker> tickersList = mapper.readValue(is, new TypeReference<List<HitbtcTicker>>() { });


    Map<String, HitbtcTicker> tickers = new HashMap<>();
    for (HitbtcTicker hitbtcTicker : tickersList) {
      tickers.put(hitbtcTicker.getSymbol(), hitbtcTicker);
    }

    assertThat(tickers).hasSize(170);

    HitbtcTicker ticker = tickers.get("BTCUSD");

    assertThat(ticker.getAsk()).isEqualTo("4249.31");
    assertThat(ticker.getBid()).isEqualTo("4222.22");
    assertThat(ticker.getLast()).isEqualTo("4233.97");
    assertThat(ticker.getHigh()).isEqualTo("4333.13");
    assertThat(ticker.getLow()).isEqualTo("3900.00");
    assertThat(ticker.getVolume()).isEqualTo("1717.91");
    assertThat(ticker.getTimestamp()).isEqualTo(simpleDateFormat.parse("2017-08-14T13:20:01.933Z"));
  }
}
