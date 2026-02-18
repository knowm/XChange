package org.knowm.xchange.kraken.service;

import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.AddressWithTag;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.exceptions.DepositAddressAmbiguousException;
import org.knowm.xchange.kraken.KrakenExchangeWiremock;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.account.params.DefaultRequestDepositAddressParams;

public class KrakenAccountServiceTest extends KrakenExchangeWiremock {

  AccountService accountService = exchange.getAccountService();

  @Test
  void funding_history() throws IOException {
    var actual = accountService.getFundingHistory(null);

    assertThat(actual).hasSize(2);

    var expected =
        FundingRecord.builder()
            .type(Type.DEPOSIT)
            .status(Status.COMPLETE)
            .currency(Currency.USDT)
            .balance(new BigDecimal("100"))
            .amount(new BigDecimal("100"))
            .fee(BigDecimal.ZERO)
            .internalId("FTJ4ZXN-YRWqyo1No6Wqt3vzBVgNMf")
            .date(Date.from(Instant.parse("2025-09-02T15:11:18.456Z")))
            .build();

    assertThat(actual.get(1))
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @Test
  void valid_balances() throws IOException {
    AccountInfo accountInfo = accountService.getAccountInfo();

    var expectedBTC =
        new Balance.Builder()
            .currency(Currency.BTC)
            .total(new BigDecimal("0.0001339400"))
            .frozen(new BigDecimal("0.00005"))
            .build();

    var actualBTC = accountInfo.getWallet("spot").getBalance(Currency.BTC);

    assertThat(actualBTC.getTotal()).isEqualTo(expectedBTC.getTotal());
    assertThat(actualBTC.getAvailable()).isEqualTo(expectedBTC.getAvailable());
    assertThat(actualBTC.getCurrency()).isEqualTo(expectedBTC.getCurrency());

    assertThat(actualBTC)
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expectedBTC);
  }

  @Test
  public void testRequestDepositAddress() throws IOException {
    DefaultRequestDepositAddressParams params =
        DefaultRequestDepositAddressParams.builder().currency(Currency.TRX).build();

    String address = accountService.requestDepositAddress(params);

    assertThat(address).isEqualTo("TYAnp8VW1aq5Jbtxgoai7BDo3jKSRe6VNR");
  }

  @Test
  public void testRequestDepositAddressUnknownCurrencyMultipleMethods() {
    var params = DefaultRequestDepositAddressParams.builder().currency(Currency.USDT).build();

    assertThatExceptionOfType(DepositAddressAmbiguousException.class)
        .isThrownBy(() -> accountService.requestDepositAddress(params));
  }

  @Test
  public void testRequestDepositAddressCurrencyWithNetwork() throws IOException {
    DefaultRequestDepositAddressParams params =
        DefaultRequestDepositAddressParams.builder().currency(Currency.XRP).build();

    AddressWithTag address = accountService.requestDepositAddressData(params);

    assertThat(address.getAddress()).isEqualTo("testXrpAddress");
    assertThat(address.getAddressTag()).isEqualTo("123");
  }

  @Test
  public void testRequestDepositMethodCaching() throws IOException {
    // cache enabled
    exchange
        .getExchangeSpecification()
        .setExchangeSpecificParametersItem("cacheDepositMethods", true);

    DefaultRequestDepositAddressParams params =
        DefaultRequestDepositAddressParams.builder().currency(Currency.TRX).build();

    wireMockServer.resetRequests();
    accountService.requestDepositAddress(params);
    accountService.requestDepositAddress(params);

    wireMockServer.verify(1, postRequestedFor(urlEqualTo("/0/private/DepositMethods")));

    // cache disabled
    exchange
        .getExchangeSpecification()
        .setExchangeSpecificParametersItem("cacheDepositMethods", false);

    wireMockServer.resetRequests();
    accountService.requestDepositAddress(params);
    accountService.requestDepositAddress(params);

    wireMockServer.verify(2, postRequestedFor(urlEqualTo("/0/private/DepositMethods")));
  }
}
