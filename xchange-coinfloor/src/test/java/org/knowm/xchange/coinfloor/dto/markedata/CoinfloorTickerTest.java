package org.knowm.xchange.coinfloor.dto.markedata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CoinfloorTickerTest {
  @Test
  public void unmarshalTest() throws IOException {
    InputStream is = getClass().getResourceAsStream("/marketdata/example-ticker.json");
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorTicker ticker = mapper.readValue(is, CoinfloorTicker.class);

    assertThat(ticker.getLast()).isNull();
    assertThat(ticker.getHigh()).isEqualTo("834.00");
    assertThat(ticker.getLow()).isEqualTo("761.00");
    assertThat(ticker.getVwap()).isEqualTo("800.37");
    assertThat(ticker.getVolume()).isEqualTo("590.1081");
    assertThat(ticker.getBid()).isEqualTo("827.00");
    assertThat(ticker.getAsk()).isEqualTo("832.00");
  }
}
