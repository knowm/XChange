package info.bitrich.xchangestream.dydx.dto;

import com.google.common.io.CharStreams;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


public class dydxWebSocketTransactionTest {
    @Test
    public void testWebSocketTransactionDeserialization_initialOrderBook() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("InitialOrderBook.json");
        String body = null;
        try (final Reader reader = new InputStreamReader(is)) {
            body = CharStreams.toString(reader);
        }

        dydxWebSocketTransaction message = StreamingObjectMapperHelper.getObjectMapper().readValue(body, dydxWebSocketTransaction.class);

        //Message Metadata
        Assert.assertEquals("subscribed", message.getType());
        Assert.assertEquals("a8187bac-4781-423b-880c-9a54ae622a41", message.getConnectionId());
        Assert.assertEquals("2", message.getMessageId());
        Assert.assertEquals("orderbook", message.getChannel());
        Assert.assertEquals("WETH-USDC", message.getId());

        //Bids
        Assert.assertEquals(58, message.getContents().getBids().length);
        Assert.assertEquals("0x2745c76240f009bf46a61059d3f7f41dafc9453e9bada85ebca1127ee490e593", message.getContents().getBids()[0].getId());
        Assert.assertEquals("fb4c8b5c-df0d-43e6-a6ec-2c4f2f280f3d", message.getContents().getBids()[0].getUuid());
        Assert.assertEquals("41491800000000000000", message.getContents().getBids()[0].getAmount());
        Assert.assertEquals("0.00000000192729", message.getContents().getBids()[0].getPrice());

        //Asks
        Assert.assertEquals(25, message.getContents().getAsks().length);
        Assert.assertEquals("0x877f1162d50cc279c8b3f6f91b3e12937fe3d9eae82f1ca0b6745db679b73678", message.getContents().getAsks()[0].getId());
        Assert.assertEquals("c92f63e3-db01-441f-b10c-dead927d5f45", message.getContents().getAsks()[0].getUuid());
        Assert.assertEquals("41481300000000000000", message.getContents().getAsks()[0].getAmount());
        Assert.assertEquals("0.00000000192961", message.getContents().getAsks()[0].getPrice());
    }

    @Test
    public void testWebSocketTransactionDeserialization_NewOrder() throws Exception {

    }

    @Test
    public void testWebSocketTransactionDeserialization_RemovedOrder() throws Exception {

    }

    @Test
    public void testWebSocketTransactionDeserialization_UpdatedOrder() throws Exception {

    }
}