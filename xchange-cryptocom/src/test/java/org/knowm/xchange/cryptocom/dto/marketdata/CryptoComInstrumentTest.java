package org.knowm.xchange.cryptocom.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.cryptocom.dto.CryptoComResponse;

public class CryptoComInstrumentTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testUnmarshalInstruments() throws IOException {
    // given
    String resource = "/org/knowm/xchange/cryptocom/dto/marketdata/get-instruments.json";

    // when
    CryptoComResponse response =
        CryptoComTestSupport.readResponse(CryptoComInstrumentTest.class, resource, objectMapper);
    List<CryptoComInstrument> instruments =
        CryptoComTestSupport.readDataList(response, objectMapper, CryptoComInstrument.class);

    // then
    assertThat(response.getId()).isEqualTo(1);
    assertThat(response.getMethod()).isEqualTo("public/get-instruments");
    assertThat(response.getCode()).isEqualTo(0);

    assertThat(instruments).hasSize(4);

    CryptoComInstrument btcUsdt = instruments.get(1);
    assertThat(btcUsdt.getSymbol()).isEqualTo("BTC_USDT");
    assertThat(btcUsdt.getInstType()).isEqualTo("CCY_PAIR");
    assertThat(btcUsdt.getBaseCurrency()).isEqualTo("BTC");
    assertThat(btcUsdt.getQuoteCurrency()).isEqualTo("USDT");
    assertThat(btcUsdt.getQuoteDecimals()).isEqualTo(2);
    assertThat(btcUsdt.getQuantityDecimals()).isEqualTo(5);
    assertThat(btcUsdt.getPriceTickSize()).isEqualTo("0.01");
    assertThat(btcUsdt.getQtyTickSize()).isEqualTo("0.00001");
    assertThat(btcUsdt.getTradable()).isTrue();

    CryptoComInstrument perpetual = instruments.get(3);
    assertThat(perpetual.getSymbol()).isEqualTo("1INCHUSD-PERP");
    assertThat(perpetual.getInstType()).isEqualTo("PERPETUAL_SWAP");
  }
}
