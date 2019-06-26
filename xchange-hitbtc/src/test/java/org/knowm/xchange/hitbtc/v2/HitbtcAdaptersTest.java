package org.knowm.xchange.hitbtc.v2;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcBalance;

public class HitbtcAdaptersTest {

  // @Rule
  // public final ExpectedException exception = ExpectedException.none();

  @Test
  public void testAdaptWallet_getBalance() {
    try {
      List<HitbtcBalance> main = new ArrayList<>();
      main.add(new HitbtcBalance("BTC", new BigDecimal("0.1234"), new BigDecimal("32.23")));
      List<HitbtcBalance> trading = new ArrayList<>();
      trading.add(new HitbtcBalance("BTC", new BigDecimal("0.111"), BigDecimal.ZERO));

      AccountInfo accountInfo =
          new AccountInfo(
              HitbtcAdapters.adaptWallet("Main", main),
              HitbtcAdapters.adaptWallet("Trading", trading));

      assertThat(accountInfo).isNotNull();

      // Getting balance with two wallets not supported
      accountInfo.getWallet().getBalance(Currency.BTC);
    } catch (Exception e) {
      assertThat(e instanceof UnsupportedOperationException);
      assertThat(e.getMessage().equals("2 wallets in account"));
    }
  }

  @Test
  public void testGuessSymbol() {
    String symbol1 = "LTCUSDT";
    String symbol2 = "LTCBTC";
    String symbol3 = "USDTBTC";
    String symbol4 = "STRATUSDT";
    String symbol5 = "STRATBTC";

    Assert.assertEquals(
        new CurrencyPair(Currency.LTC, Currency.USDT), HitbtcAdapters.guessSymbol(symbol1));
    Assert.assertEquals(CurrencyPair.LTC_BTC, HitbtcAdapters.guessSymbol(symbol2));
    Assert.assertEquals(
        new CurrencyPair(Currency.USDT, Currency.BTC), HitbtcAdapters.guessSymbol(symbol3));
    Assert.assertEquals(
        new CurrencyPair(Currency.STRAT, Currency.USDT), HitbtcAdapters.guessSymbol(symbol4));
    Assert.assertEquals(
        new CurrencyPair(Currency.STRAT, Currency.BTC), HitbtcAdapters.guessSymbol(symbol5));
  }
}
