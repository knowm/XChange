package org.knowm.xchange.dsx;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.dto.DsxBalance;
import org.knowm.xchange.dto.account.AccountInfo;

public class DsxAdaptersTest {

  // @Rule
  // public final ExpectedException exception = ExpectedException.none();

  @Test
  public void testAdaptWallet_getBalance() {
    try {
      List<DsxBalance> main = new ArrayList<>();
      main.add(new DsxBalance("BTC", new BigDecimal("0.1234"), new BigDecimal("32.23")));
      List<DsxBalance> trading = new ArrayList<>();
      trading.add(new DsxBalance("BTC", new BigDecimal("0.111"), BigDecimal.ZERO));

      AccountInfo accountInfo =
          new AccountInfo(
              DsxAdapters.adaptWallet("Main", main), DsxAdapters.adaptWallet("Trading", trading));

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
        new CurrencyPair(Currency.LTC, Currency.USDT), DsxAdapters.guessSymbol(symbol1));
    Assert.assertEquals(CurrencyPair.LTC_BTC, DsxAdapters.guessSymbol(symbol2));
    Assert.assertEquals(
        new CurrencyPair(Currency.USDT, Currency.BTC), DsxAdapters.guessSymbol(symbol3));
    Assert.assertEquals(
        new CurrencyPair(Currency.STRAT, Currency.USDT), DsxAdapters.guessSymbol(symbol4));
    Assert.assertEquals(
        new CurrencyPair(Currency.STRAT, Currency.BTC), DsxAdapters.guessSymbol(symbol5));
  }
}
