package info.bitrich.xchangestream.kraken;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class KrakenStreamingServiceTest {

  @Test
  void parseOrderbookSizeReturnsDefaultOnInvalidValue() {
    assertThat(KrakenStreamingService.parseOrderBookSize(new Object[] {"22"})).isNull();
    assertThat(KrakenStreamingService.parseOrderBookSize(new Object[] {22}))
        .isEqualTo((Integer) KrakenStreamingService.ORDER_BOOK_SIZE_DEFAULT);
  }

  @Test
  void parseOrderbookSizeReturnsCorrectValue() {
    assertThat((int) KrakenStreamingService.parseOrderBookSize(new Object[] {100})).isEqualTo(100);
  }

  @Test
  void parseOrderbookSizeReturnsDefaultWhenNoArgsGiven() {
    assertThat(KrakenStreamingService.parseOrderBookSize(new Object[] {})).isNull();
  }
}
