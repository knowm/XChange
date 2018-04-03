package org.knowm.xchange.hitbtc.v2;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
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
}
