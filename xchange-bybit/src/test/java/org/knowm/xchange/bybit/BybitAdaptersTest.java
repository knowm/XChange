package org.knowm.xchange.bybit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.bybit.BybitAdapters.guessSymbol;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.derivative.OptionsContract;

public class BybitAdaptersTest {

  @Test
  public void testGuessSymbol() {
    assertThat(guessSymbol("BTCUSDT")).isEqualTo(new CurrencyPair("BTC", "USDT"));
    assertThat(guessSymbol("BTCUSDC")).isEqualTo(new CurrencyPair("BTC", "USDC"));
    assertThat(guessSymbol("LTCBTC")).isEqualTo(new CurrencyPair("LTC", "BTC"));
    assertThat(guessSymbol("BTCDAI")).isEqualTo(new CurrencyPair("BTC", "DAI"));
    assertThat(guessSymbol("ABCDEFG")).isEqualTo(new CurrencyPair("ABCD", "EFG"));
  }

  @Test
  public void testInstrumentOptionSymbol() {
    assertThat(BybitAdapters.convertToBybitSymbol(new CurrencyPair("BTC/USDC")))
        .isEqualTo("BTCUSDC");
    assertThat(BybitAdapters.convertToBybitSymbol(new FuturesContract("BTC/USDC/PERP")))
        .isEqualTo("BTCUSDC");
    assertThat(BybitAdapters.convertToBybitSymbol(new OptionsContract("BTC/USDC/240110/45500/P")))
        .isEqualTo("BTC-10JAN24-45500-P");
  }
}
