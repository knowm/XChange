package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map.Entry;

import static org.junit.Assert.assertEquals;

public class DepthBinanceWebSocketTransactionTest {
    private static ObjectMapper mapper;

    @BeforeClass
    public static void setupClass() {
        JsonFactory jf = new JsonFactory();
        jf.enable(JsonParser.Feature.ALLOW_COMMENTS);
        mapper = new ObjectMapper(jf);
    }

    @Test
    public void testMapping() throws Exception {
        InputStream stream = this.getClass().getResourceAsStream("testDepthEvent.json");
        DepthBinanceWebSocketTransaction transaction = mapper.readValue(stream, DepthBinanceWebSocketTransaction.class);
        assertEquals(BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.DEPTH_UPDATE, transaction.getEventType());

        BinanceOrderbook orderBook = transaction.getOrderBook();

        Iterator<Entry<BigDecimal, BigDecimal>> bidIterator = orderBook.bids.entrySet().iterator();
        assertOrderBookEntry(bidIterator, 0.10376590, 59.15767010);

        Iterator<Entry<BigDecimal, BigDecimal>> askIterator = orderBook.asks.entrySet().iterator();
        assertOrderBookEntry(askIterator, 0.10376586, 159.15767010);
        assertOrderBookEntry(askIterator, 0.10383109, 345.86845230);
        assertOrderBookEntry(askIterator, 0.10490700, 0.00000000);
    }

    private void assertOrderBookEntry(Iterator<Entry<BigDecimal, BigDecimal>> entryIterator, double price, double volume) {
        Entry<BigDecimal, BigDecimal> firstAskEntry = entryIterator.next();
        assertEquals(price, firstAskEntry.getKey().doubleValue(), 0.0);
        assertEquals(volume, firstAskEntry.getValue().doubleValue(), 0.0);
    }
}
