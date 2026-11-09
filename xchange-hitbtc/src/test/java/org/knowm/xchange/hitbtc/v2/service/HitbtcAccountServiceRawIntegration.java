package org.knowm.xchange.hitbtc.v2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.hitbtc.v2.BaseAuthenticatedServiceTest;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcBalance;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSort;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTransaction;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTransferType;
import si.mazi.rescu.HttpStatusIOException;

/**
 * Test ignored in default build because it requires production authentication credentials. See
 * {@link BaseAuthenticatedServiceTest}.
 */
@Disabled
class HitbtcAccountServiceRawIntegration extends BaseAuthenticatedServiceTest {

  private HitbtcAccountServiceRaw service = (HitbtcAccountServiceRaw) exchange.getAccountService();

  @Test
  void getMainBalance() throws Exception {

    List<HitbtcBalance> balance = service.getMainBalance();

    Map<Currency, HitbtcBalance> balanceMap = new HashMap<>();
    for (HitbtcBalance hitbtcBalance : balance) {
      balanceMap.put(Currency.getInstance(hitbtcBalance.getCurrency()), hitbtcBalance);
    }

    assertThat(balance).isNotNull();
    BigDecimal expected = new BigDecimal("0.00000000");
    assertThat(balanceMap.get(Currency.BTC).getAvailable()).isEqualTo(expected);
  }

  @Test
  void getTradingBalance() throws Exception {

    List<HitbtcBalance> balance = service.getTradingBalance();

    Map<Currency, HitbtcBalance> balanceMap = new HashMap<>();
    for (HitbtcBalance hitbtcBalance : balance) {
      balanceMap.put(Currency.getInstance(hitbtcBalance.getCurrency()), hitbtcBalance);
    }

    assertThat(balance).isNotNull();
    BigDecimal expected = new BigDecimal("0.040000000");
    assertThat(balanceMap.get(Currency.BTC).getAvailable()).isEqualTo(expected);
  }

  @Test
  void getPaymentBalance() throws Exception {

    List<HitbtcBalance> response = service.getMainBalance();

    assertThat(response.isEmpty()).isFalse();
  }

  @Test
  void getDepositAddress() throws Exception {

    String response = service.getDepositAddress(Currency.BTC).getAddress();

    assertThat(StringUtils.isNotEmpty(response)).isTrue();
  }

  @Test
  void getTransactions() throws Exception {
    List<HitbtcTransaction> transactions;

    transactions =
        service.getTransactions(
            null, HitbtcSort.SORT_ASCENDING, new Date(1520949577579L), new Date(), 100, null);
    assertThat(transactions.isEmpty()).isFalse();
    assertThat(StringUtils.isNotEmpty(transactions.get(0).getId())).isTrue();

    transactions =
        service.getTransactions(
            Currency.LTC.getCurrencyCode(),
            HitbtcSort.SORT_DESCENDING,
            new Date(0),
            new Date(),
            100,
            null);
    assertThat(transactions.isEmpty()).isFalse();
    assertThat(StringUtils.isNotEmpty(transactions.get(0).getId())).isTrue();

    transactions =
        service.getTransactions(
            Currency.LTC.getCurrencyCode(),
            HitbtcSort.SORT_DESCENDING,
            new Date(0),
            new Date(),
            100,
            null);
    assertThat(transactions.isEmpty()).isFalse();
    assertThat(StringUtils.isNotEmpty(transactions.get(0).getId())).isTrue();

    transactions = service.getTransactions(null, null, null);
    assertThat(transactions.isEmpty()).isFalse();
    assertThat(StringUtils.isNotEmpty(transactions.get(0).getId())).isTrue();

    transactions =
        service.getTransactions(
            Currency.LTC.getCurrencyCode(), null, new Date(0), new Date(), null, null);
    assertThat(transactions.isEmpty()).isFalse();
    assertThat(StringUtils.isNotEmpty(transactions.get(0).getId())).isTrue();

    transactions =
        service.getTransactions(
            Currency.LTC.getCurrencyCode(), null, 0L, Long.MAX_VALUE, null, null);
    assertThat(transactions.isEmpty()).isFalse();
    assertThat(StringUtils.isNotEmpty(transactions.get(0).getId())).isTrue();
  }

  // Should return {"error":{"code":20001,"message":"Insufficient funds","description":"Check that
  // the funds are sufficient, given commissions"}} --I'm poor
  @Test
  void transferFunds() {

    Throwable exception =
        assertThatExceptionOfType(HttpStatusIOException.class)
            .isThrownBy(
                () ->
                    service.transferFunds(
                        Currency.USD, new BigDecimal("0.01"), HitbtcTransferType.BANK_TO_EXCHANGE))
            .actual();
    assertThat(exception.getMessage()).contains("HTTP status code was not OK: 400");
  }
}
