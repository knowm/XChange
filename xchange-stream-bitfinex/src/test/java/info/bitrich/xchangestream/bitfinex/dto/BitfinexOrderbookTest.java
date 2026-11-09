package info.bitrich.xchangestream.bitfinex.dto;

import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.currency.CurrencyPair.BTC_USD;

import java.util.Date;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitfinex.service.BitfinexAdapters;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexDepth;
import org.knowm.xchange.dto.marketdata.OrderBook;

class BitfinexOrderbookTest {

  @Test
  void timestampShouldBeInSeconds() {
    BitfinexDepth depth =
        new BitfinexOrderbook(
                new BitfinexOrderbookLevel[] {
                  new BitfinexOrderbookLevel(ONE, ONE, ONE),
                  new BitfinexOrderbookLevel(ONE, ONE, ONE)
                })
            .toBitfinexDepth();

    OrderBook orderBook = BitfinexAdapters.adaptOrderBook(depth, BTC_USD);

    // What is the time now... after order books created?
    assertThat(!orderBook.getTimeStamp().after(new Date()))
        .as("The timestamp should be a value less than now, but was: " + orderBook.getTimeStamp())
        .isTrue();
  }
}
