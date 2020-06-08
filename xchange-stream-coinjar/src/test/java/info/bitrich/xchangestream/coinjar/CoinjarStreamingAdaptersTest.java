package info.bitrich.xchangestream.coinjar;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;

public class CoinjarStreamingAdaptersTest {

  @Test
  public void testAdaptTopicToCurrencyPair() {
    String topic = "book:BTCAUD";
    assertThat(CoinjarStreamingAdapters.adaptTopicToCurrencyPair(topic))
        .isEqualTo(CurrencyPair.BTC_AUD);
  }
}
