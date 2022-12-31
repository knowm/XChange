package org.knowm.xchange.krakenfutures.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.krakenfutures.dto.trade.KrakenFuturesOrderSide;

/** @author Panchen */
public class KrakenFuturesFillsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenFuturesFillsJSONTest.class.getResourceAsStream(
                "/org/knowm/xchange/krakenfutures/dto/marketdata/example-fills-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenFuturesFills cryptoFacilitiesFills = mapper.readValue(is, KrakenFuturesFills.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesFills.isSuccess()).isTrue();

    List<KrakenFuturesFill> fills = cryptoFacilitiesFills.getFills();

    assertThat(fills.size()).isEqualTo(2);

    Iterator<KrakenFuturesFill> it = fills.iterator();
    KrakenFuturesFill fill = it.next();

    assertThat(fill.getOrderId()).isEqualTo("c18f0c17-9971-40e6-8e5b-10df05d422f0");
    assertThat(fill.getFillId()).isEqualTo("522d4e08-96e7-4b44-9694-bfaea8fe215e");
    assertThat(fill.getCliOrdId()).isEqualTo("d427f920-ec55-4c18-ba95-5fe241513b30");
    assertThat(fill.getSymbol()).isEqualTo("fi_xbtusd_180615");
    assertThat(fill.getSide()).isEqualTo(KrakenFuturesOrderSide.buy);
    assertThat(fill.getSize()).isEqualTo(new BigDecimal("2000"));
    assertThat(fill.getPrice()).isEqualTo(new BigDecimal("4255"));
    assertThat(fill.getFillType()).isEqualTo("maker");
  }
}
