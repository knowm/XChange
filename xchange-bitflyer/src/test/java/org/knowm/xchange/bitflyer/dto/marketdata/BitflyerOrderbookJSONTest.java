package org.knowm.xchange.bitflyer.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class BitflyerOrderbookJSONTest {
  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitflyerTicker.class.getResourceAsStream("/marketdata/example-orderbook.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitflyerOrderbook response = mapper.readValue(is, BitflyerOrderbook.class);

    // then
    assertThat(response.getMidPrice()).isEqualTo(new BigDecimal(33320));

    assertThat(response.getBids().get(0).getPrice()).isEqualTo(new BigDecimal(30000));
    assertThat(response.getBids().get(0).getSize()).isEqualTo(new BigDecimal("0.1"));
    assertThat(response.getBids().get(1).getPrice()).isEqualTo(new BigDecimal(25570));
    assertThat(response.getBids().get(1).getSize()).isEqualTo(new BigDecimal(3));

    assertThat(response.getAsks().get(0).getPrice()).isEqualTo(new BigDecimal(36640));
    assertThat(response.getAsks().get(0).getSize()).isEqualTo(new BigDecimal(5));
    assertThat(response.getAsks().get(1).getPrice()).isEqualTo(new BigDecimal(36700));
    assertThat(response.getAsks().get(1).getSize()).isEqualTo(new BigDecimal("1.2"));
  }
}
