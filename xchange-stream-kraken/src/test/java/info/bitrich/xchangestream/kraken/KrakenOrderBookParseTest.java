package info.bitrich.xchangestream.kraken;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.kraken.dto.KrakenOrderBook;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenOrderBookMessageType;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicOrder;

public class KrakenOrderBookParseTest {

  @Test
  public void testOrderBookSnapshot() throws IOException {
    JsonNode jsonNode =
        StreamingObjectMapperHelper.getObjectMapper()
            .readTree(this.getClass().getResource("/orderBookMessageSnapshot.json").openStream());
    Assert.assertNotNull(jsonNode);
    List list = StreamingObjectMapperHelper.getObjectMapper().treeToValue(jsonNode, List.class);
    KrakenOrderBook krakenOrderBook = KrakenOrderBookUtils.parse(list);
    Assert.assertNotNull(krakenOrderBook);
    Assert.assertEquals(KrakenOrderBookMessageType.SNAPSHOT, krakenOrderBook.getType());
    Assert.assertNotNull(krakenOrderBook.getAsk());
    Assert.assertEquals(25, krakenOrderBook.getAsk().length);
    Assert.assertNotNull(krakenOrderBook.getBid());
    Assert.assertEquals(25, krakenOrderBook.getBid().length);
    KrakenPublicOrder firstAsk = krakenOrderBook.getAsk()[0];
    Assert.assertEquals(0, new BigDecimal("8692").compareTo(firstAsk.getPrice()));
    Assert.assertEquals(0, new BigDecimal("2.01122372").compareTo(firstAsk.getVolume()));
    Assert.assertEquals(1561120269939L, firstAsk.getTimestamp());
    KrakenPublicOrder firstBid = krakenOrderBook.getBid()[0];
    Assert.assertEquals(0, new BigDecimal("8691.9").compareTo(firstBid.getPrice()));
    Assert.assertEquals(0, new BigDecimal("1.45612927").compareTo(firstBid.getVolume()));
    Assert.assertEquals(1561120266647L, firstBid.getTimestamp());
  }

  @Test
  public void testOrderBookUpdate() throws IOException {
    JsonNode jsonNode =
        StreamingObjectMapperHelper.getObjectMapper()
            .readTree(this.getClass().getResource("/orderBookMessageUpdate.json").openStream());
    Assert.assertNotNull(jsonNode);
    List list = StreamingObjectMapperHelper.getObjectMapper().treeToValue(jsonNode, List.class);
    KrakenOrderBook krakenOrderBook = KrakenOrderBookUtils.parse(list);
    Assert.assertNotNull(krakenOrderBook);
    Assert.assertEquals(KrakenOrderBookMessageType.UPDATE, krakenOrderBook.getType());
    Assert.assertNotNull(krakenOrderBook.getAsk());
    Assert.assertEquals(2, krakenOrderBook.getAsk().length);
    Assert.assertNotNull(krakenOrderBook.getBid());
    Assert.assertEquals(0, krakenOrderBook.getBid().length);
    KrakenPublicOrder firstAsk = krakenOrderBook.getAsk()[0];
    Assert.assertEquals(0, new BigDecimal("9618.6").compareTo(firstAsk.getPrice()));
    Assert.assertEquals(0, BigDecimal.ZERO.compareTo(firstAsk.getVolume()));
    Assert.assertEquals(1561372908562L, firstAsk.getTimestamp());
  }
}
