package org.knowm.xchange.dase;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.dase.dto.account.DaseBalanceItem;
import org.knowm.xchange.dase.dto.account.DaseBalancesResponse;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;

public class DaseAdaptersAccountInfoTest {

  @Test
  public void adaptAccountInfo_maps_balances() {
    DaseBalanceItem b =
        new DaseBalanceItem(
            "acc-1",
            "USDC",
            new BigDecimal("100.00"),
            new BigDecimal("70.00"),
            new BigDecimal("30.00"));
    DaseBalancesResponse resp = new DaseBalancesResponse(Arrays.asList(b));

    AccountInfo info = DaseAdapters.adaptAccountInfo("portfolio-1", resp);
    assertThat(info.getUsername()).isEqualTo("portfolio-1");
    Wallet wallet = info.getWallet();
    Balance usdc = wallet.getBalance(org.knowm.xchange.currency.Currency.USDC);
    assertThat(usdc.getTotal()).isEqualTo(new BigDecimal("100.00"));
    assertThat(usdc.getAvailable()).isEqualTo(new BigDecimal("70.00"));
    assertThat(usdc.getFrozen()).isEqualTo(new BigDecimal("30.00"));
  }
}
