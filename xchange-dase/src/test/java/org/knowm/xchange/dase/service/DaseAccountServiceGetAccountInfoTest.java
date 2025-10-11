package org.knowm.xchange.dase.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dase.DaseExchange;
import org.knowm.xchange.dase.dto.account.DaseBalanceItem;
import org.knowm.xchange.dase.dto.account.DaseBalancesResponse;
import org.knowm.xchange.dase.dto.user.DaseUserProfile;
import org.knowm.xchange.dto.account.AccountInfo;
import org.mockito.Mockito;

public class DaseAccountServiceGetAccountInfoTest {

  @Test
  public void combinesProfileAndBalances() throws Exception {
    Exchange exchange = Mockito.mock(Exchange.class);
    ExchangeSpecification spec = new ExchangeSpecification(DaseExchange.class);
    Mockito.when(exchange.getExchangeSpecification()).thenReturn(spec);

    DaseAccountService svc = spy(new DaseAccountService(exchange));

    doReturn(new DaseUserProfile("portfolio-1")).when(svc).getUserProfile();
    DaseBalanceItem usdc =
        new DaseBalanceItem(
            "acc-1",
            "USDC",
            new BigDecimal("100.00"),
            new BigDecimal("70.00"),
            new BigDecimal("30.00"));
    doReturn(new DaseBalancesResponse(Arrays.asList(usdc))).when(svc).getDaseBalances();

    AccountInfo info = svc.getAccountInfo();
    assertThat(info.getUsername()).isEqualTo("portfolio-1");
    assertThat(info.getWallet().getBalance(org.knowm.xchange.currency.Currency.USDC).getTotal())
        .isEqualByComparingTo("100.00");
    assertThat(info.getWallet().getBalance(org.knowm.xchange.currency.Currency.USDC).getAvailable())
        .isEqualByComparingTo("70.00");
    assertThat(info.getWallet().getBalance(org.knowm.xchange.currency.Currency.USDC).getFrozen())
        .isEqualByComparingTo("30.00");
  }
}
