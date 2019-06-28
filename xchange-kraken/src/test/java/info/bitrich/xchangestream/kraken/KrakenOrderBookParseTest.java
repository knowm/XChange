package info.bitrich.xchangestream.kraken;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.kraken.dto.KrakenOrderBook;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenOrderBookMessageType;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class KrakenOrderBookParseTest {

    @Test
    public void testOrderBookSnapshot() throws IOException {
        JsonNode jsonNode = StreamingObjectMapperHelper.getObjectMapper().readTree(
                this.getClass().getResource("/orderBookMessageSnapshot.json").openStream());
        Assert.assertNotNull(jsonNode);
        List list = StreamingObjectMapperHelper.getObjectMapper().treeToValue(jsonNode, List.class);
        KrakenOrderBook krakenOrderBook = KrakenOrderBookUtils.parse(list);
        Assert.assertNotNull(krakenOrderBook);
        Assert.assertEquals(KrakenOrderBookMessageType.SNAPSHOT, krakenOrderBook.getType());
        Assert.assertNotNull(krakenOrderBook.getAsk());
        Assert.assertEquals(25, krakenOrderBook.getAsk().length);
        Assert.assertNotNull(krakenOrderBook.getBid());
        Assert.assertEquals(25, krakenOrderBook.getBid().length);
    }

    @Test
    public void testOrderBookUpdate() throws IOException {
        JsonNode jsonNode = StreamingObjectMapperHelper.getObjectMapper().readTree(
                this.getClass().getResource("/orderBookMessageUpdate.json").openStream());
        Assert.assertNotNull(jsonNode);
        List list = StreamingObjectMapperHelper.getObjectMapper().treeToValue(jsonNode, List.class);
        KrakenOrderBook krakenOrderBook = KrakenOrderBookUtils.parse(list);
        Assert.assertNotNull(krakenOrderBook);
        Assert.assertEquals(KrakenOrderBookMessageType.UPDATE, krakenOrderBook.getType());
        Assert.assertNotNull(krakenOrderBook.getAsk());
        Assert.assertEquals(2, krakenOrderBook.getAsk().length);
        Assert.assertNotNull(krakenOrderBook.getBid());
        Assert.assertEquals(0, krakenOrderBook.getBid().length);
    }
}
