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
public class KrakenFuturesHistoryJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenFuturesFillsJSONTest.class.getResourceAsStream(
                "/org/knowm/xchange/krakenfutures/dto/marketdata/example-history-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenFuturesPublicFills cryptoFacilitiesPublicFills =
        mapper.readValue(is, KrakenFuturesPublicFills.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesPublicFills.isSuccess()).isTrue();

    List<KrakenFuturesPublicFill> fills = cryptoFacilitiesPublicFills.getFills();

    assertThat(fills.size()).isEqualTo(2);

    Iterator<KrakenFuturesPublicFill> it = fills.iterator();
    KrakenFuturesPublicFill fill = it.next();

    assertThat(fill.getTradeId()).isEqualTo("865");
    assertThat(fill.getPrice()).isEqualTo(new BigDecimal("4322"));
    assertThat(fill.getSize()).isEqualTo(new BigDecimal("5000"));
    assertThat(fill.getSide()).isEqualTo(KrakenFuturesOrderSide.buy);
    assertThat(fill.getType()).isEqualTo("fill");
  }
}
