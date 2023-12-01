package org.knowm.xchange.bybit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.bybit.BybitAdapters.guessSymbol;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;

public class BybitAdaptersTest {

  @Test
  public void testGuessSymbol() {
    assertThat(guessSymbol("BTCUSDT")).isEqualTo(new CurrencyPair("BTC", "USDT"));
    assertThat(guessSymbol("BTCUSDC")).isEqualTo(new CurrencyPair("BTC", "USDC"));
    assertThat(guessSymbol("LTCBTC")).isEqualTo(new CurrencyPair("LTC", "BTC"));
    assertThat(guessSymbol("BTCDAI")).isEqualTo(new CurrencyPair("BTC", "DAI"));
    assertThat(guessSymbol("ABCDEFG")).isEqualTo(new CurrencyPair("ABCD", "EFG"));
  }
}
