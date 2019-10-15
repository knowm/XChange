package org.knowm.xchange.blockchainpit.dto.orderbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.blockchainpit.dto.PitChannel;
import org.knowm.xchange.blockchainpit.dto.PitEventType;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/** Test PitOrderbookL3 JSON parsing */
public class OrderbookL3JSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = OrderbookL3JSONTest.class.getResourceAsStream(
            "/org.knowm.xchange.blockchainpit.dto.orderbook/orderbook_snapshot_l3.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    PitOrderbookL3 lob = mapper.readValue(is, PitOrderbookL3.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(lob.getSeqnum()).isEqualTo(2);
    assertThat(lob.getEvent()).isEqualTo(PitEventType.SNAPSHOT);
    assertThat(lob.getChannel()).isEqualTo(PitChannel.ORDERBOOK_L3);
    ArrayList<PitDepthItemL3> asks = lob.getAsks();
    ArrayList<PitDepthItemL3> bids = lob.getBids();
    assertThat(bids.size()).isEqualTo(2);
    assertThat(asks.size()).isEqualTo(2);

    assertThat(bids.get(0).getId()).isEqualTo("2");
    assertThat(bids.get(0).getPrice()).isEqualTo(new BigDecimal("8723.45"));
    assertThat(bids.get(0).getQty()).isEqualTo(new BigDecimal("1.45"));
    assertThat(bids.get(1).getId()).isEqualTo("1");
    assertThat(bids.get(1).getPrice()).isEqualTo(new BigDecimal("8124.45"));
    assertThat(bids.get(1).getQty()).isEqualTo(new BigDecimal("123.45"));

    assertThat(asks.get(0).getId()).isEqualTo("555");
    assertThat(asks.get(0).getPrice()).isEqualTo(new BigDecimal("8730.0"));
    assertThat(asks.get(0).getQty()).isEqualTo(new BigDecimal("1.55"));
    assertThat(asks.get(1).getId()).isEqualTo("uyttr");
    assertThat(asks.get(1).getPrice()).isEqualTo(new BigDecimal("8904.45"));
    assertThat(asks.get(1).getQty()).isEqualTo(new BigDecimal("13.66"));

  }
}
