package org.knowm.xchange.cryptocom.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.cryptocom.dto.CryptoComResponse;

public class CryptoComOrderBookDataTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testUnmarshalOrderBook() throws IOException {
    // given
    String resource = "/org/knowm/xchange/cryptocom/dto/marketdata/get-book.json";

    // when
    CryptoComResponse response =
        CryptoComTestSupport.readResponse(CryptoComOrderBookDataTest.class, resource, objectMapper);
    List<CryptoComOrderBookData> books =
        CryptoComTestSupport.readDataList(response, objectMapper, CryptoComOrderBookData.class);

    // then
    assertThat(response.getResult().get("instrument_name").asText()).isEqualTo("BTC_USDT");
    assertThat(books).hasSize(1);

    CryptoComOrderBookData book = books.get(0);
    assertThat(book.getTimestamp()).isEqualTo(1785085694867L);

    List<List<String>> bids = book.getBids();
    assertThat(bids).hasSize(5);
    assertThat(new BigDecimal(bids.get(0).get(0))).isEqualByComparingTo(new BigDecimal("64688.98"));
    assertThat(new BigDecimal(bids.get(0).get(1))).isEqualByComparingTo(new BigDecimal("0.04222"));
    assertThat(bids.get(0).get(2)).isEqualTo("3");

    List<List<String>> asks = book.getAsks();
    assertThat(asks).hasSize(5);
    assertThat(new BigDecimal(asks.get(0).get(0))).isEqualByComparingTo(new BigDecimal("64688.99"));
    assertThat(new BigDecimal(asks.get(0).get(1))).isEqualByComparingTo(new BigDecimal("0.17723"));
    assertThat(asks.get(0).get(2)).isEqualTo("7");
  }
}
