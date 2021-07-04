package info.bitrich.xchangestream.kraken;

import org.junit.Assert;
import org.junit.Test;

public class KrakenStreamingServiceTest {

    @Test
    public void testParseOrderbookSizeReturnsDefaultOnInvalidValue() {
        Assert.assertEquals(KrakenStreamingService.ORDER_BOOK_SIZE_DEFAULT, KrakenStreamingService.parseOrderBookSize(new Object[]{"22"}));
        Assert.assertEquals(KrakenStreamingService.ORDER_BOOK_SIZE_DEFAULT, KrakenStreamingService.parseOrderBookSize(new Object[]{22}));
    }

    @Test
    public void testParseOrderbookSizeReturnsCorrectValue() {
        Assert.assertEquals(100, KrakenStreamingService.parseOrderBookSize(new Object[]{100}));
    }

    @Test
    public void testParseOrderbookSizeReturnsDefaultWhenNoArgsGiven() {
        Assert.assertEquals(KrakenStreamingService.ORDER_BOOK_SIZE_DEFAULT, KrakenStreamingService.parseOrderBookSize(new Object[]{}));
    }

}