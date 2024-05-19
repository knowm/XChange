package org.knowm.xchange.bybit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.bybit.BybitAdapters.guessSymbol;

import org.junit.Test;
import org.knowm.xchange.bybit.dto.BybitCategory;
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

    assertThat(guessSymbol("BTCUSDT", BybitCategory.LINEAR)).isEqualTo(new FuturesContract("BTC/USDT/PERP"));
    assertThat(guessSymbol("ETHPERP", BybitCategory.LINEAR)).isEqualTo(new FuturesContract("ETH/USDC/PERP"));
  }

  @Test
  public void testConvertToByBitSymbol() {
    assertThat(BybitAdapters.convertToBybitSymbol(new CurrencyPair("BTC/USDC")))
        .isEqualTo("BTCUSDC");

    assertThat(BybitAdapters.convertToBybitSymbol(new FuturesContract("ETH/USDT/PERP")))
        .isEqualTo("ETHUSDT");
    assertThat(BybitAdapters.convertToBybitSymbol(new FuturesContract("ETH/USDC/PERP")))
        .isEqualTo("ETHPERP");
    assertThat(BybitAdapters.convertToBybitSymbol(new FuturesContract("ETH/USDC/02FEB24")))
        .isEqualTo("ETH-02FEB24");
    assertThat(BybitAdapters.convertToBybitSymbol(new FuturesContract("ETH/USD/H24")))
        .isEqualTo("ETHUSDH24");

    assertThat(BybitAdapters.convertToBybitSymbol(new OptionsContract("BTC/USDC/240110/45500/P")))
        .isEqualTo("BTC-10JAN24-45500-P");
  }

  @Test
  public void testConvertToInstrument() {
    assertThat(BybitAdapters.convertBybitSymbolToInstrument("BTCUSDC", BybitCategory.SPOT))
        .isEqualTo(new CurrencyPair("BTC/USDC"));

    assertThat(BybitAdapters.convertBybitSymbolToInstrument("ETHUSDT", BybitCategory.LINEAR))
        .isEqualTo(new FuturesContract("ETH/USDT/PERP"));
    assertThat(BybitAdapters.convertBybitSymbolToInstrument("ETHPERP", BybitCategory.LINEAR))
        .isEqualTo(new FuturesContract("ETH/USDC/PERP"));
    assertThat(BybitAdapters.convertBybitSymbolToInstrument("ETH-02FEB24", BybitCategory.LINEAR))
        .isEqualTo(new FuturesContract("ETH/USDC/02FEB24"));

    assertThat(BybitAdapters.convertBybitSymbolToInstrument("ETHUSDH24", BybitCategory.INVERSE))
        .isEqualTo(new FuturesContract("ETH/USD/H24"));

  }
}
