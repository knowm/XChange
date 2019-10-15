package org.knowm.xchange.blockchainpit.dto.orderbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.blockchainpit.dto.PitChannel;
import org.knowm.xchange.blockchainpit.dto.PitEventType;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/** Test PitOrderbookL3 JSON parsing */
public class OrderbookL2JSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = OrderbookL2JSONTest.class.getResourceAsStream(
            "/org.knowm.xchange.blockchainpit.dto.orderbook/orderbook_snapshot_l2.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    PitOrderbookL2 lob = mapper.readValue(is, PitOrderbookL2.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(lob.getSeqnum()).isEqualTo(2);
    assertThat(lob.getEvent()).isEqualTo(PitEventType.SNAPSHOT);
    assertThat(lob.getChannel()).isEqualTo(PitChannel.ORDERBOOK_L2);
    ArrayList<PitDepthItemL2> asks = lob.getAsks();
    ArrayList<PitDepthItemL2> bids = lob.getBids();
    assertThat(bids.size()).isEqualTo(2);
    assertThat(asks.size()).isEqualTo(2);

    assertThat(bids.get(0).getNum()).isEqualTo(2);
    assertThat(bids.get(0).getPrice()).isEqualTo(new BigDecimal("8723.45"));
    assertThat(bids.get(0).getQty()).isEqualTo(new BigDecimal("1.45"));
    assertThat(bids.get(1).getNum()).isEqualTo(1);
    assertThat(bids.get(1).getPrice()).isEqualTo(new BigDecimal("8124.45"));
    assertThat(bids.get(1).getQty()).isEqualTo(new BigDecimal("123.45"));

    assertThat(asks.get(0).getNum()).isEqualTo(2);
    assertThat(asks.get(0).getPrice()).isEqualTo(new BigDecimal("8730.0"));
    assertThat(asks.get(0).getQty()).isEqualTo(new BigDecimal("1.55"));
    assertThat(asks.get(1).getNum()).isEqualTo(2);
    assertThat(asks.get(1).getPrice()).isEqualTo(new BigDecimal("8904.45"));
    assertThat(asks.get(1).getQty()).isEqualTo(new BigDecimal("13.66"));

  }
}
