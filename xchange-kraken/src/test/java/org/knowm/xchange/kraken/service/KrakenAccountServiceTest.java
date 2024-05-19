package org.knowm.xchange.kraken.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AddressWithTag;
import org.knowm.xchange.exceptions.DepositAddressAmbiguousException;
import org.knowm.xchange.service.account.params.DefaultRequestDepositAddressParams;

@Slf4j
public class KrakenAccountServiceTest extends BaseWiremockTest {

  private KrakenAccountService classUnderTest;
  private Exchange exchange;

  @Before
  public void setup() {
    exchange = createExchange();
    classUnderTest = (KrakenAccountService) exchange.getAccountService();
  }

  @Test
  @SneakyThrows
  public void testRequestDepositAddressUnknownCurrency() {
    stubFor(
        post(urlPathEqualTo("/0/private/DepositMethods"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        loadFile(
                            "/org/knowm/xchange/kraken/dto/account/example-deposit-methods-trx.json"))));

    stubFor(
        post(urlPathEqualTo("/0/private/DepositAddresses"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        loadFile(
                            "/org/knowm/xchange/kraken/dto/account/example-deposit-addresses-trx.json"))));

    DefaultRequestDepositAddressParams params =
        DefaultRequestDepositAddressParams.builder().currency(Currency.TRX).build();

    String address = classUnderTest.requestDepositAddress(params);

    assertThat(address).isEqualTo("TYAnp8VW1aq5Jbtxgoai7BDo3jKSRe6VNR");
  }

  @Test
  @SneakyThrows
  public void testRequestDepositAddressKnownCurrency() {
    stubFor(
        post(urlPathEqualTo("/0/private/DepositAddresses"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        loadFile(
                            "/org/knowm/xchange/kraken/dto/account/example-deposit-addresses.json"))));

    DefaultRequestDepositAddressParams params =
        DefaultRequestDepositAddressParams.builder().currency(Currency.BTC).build();

    String address = classUnderTest.requestDepositAddress(params);

    assertThat(address).isEqualTo("testBtcAddress");
  }

  @Test
  @SneakyThrows
  public void testRequestDepositAddressUnknownCurrencyMultipleMethods() {
    stubFor(
        post(urlPathEqualTo("/0/private/DepositMethods"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        loadFile(
                            "/org/knowm/xchange/kraken/dto/account/example-deposit-methods-usdt.json"))));

    assertThatThrownBy(
            () -> {
              DefaultRequestDepositAddressParams params =
                  DefaultRequestDepositAddressParams.builder().currency(Currency.USDT).build();

              classUnderTest.requestDepositAddress(params);
            })
        .isInstanceOf(DepositAddressAmbiguousException.class);
  }

  @Test
  @SneakyThrows
  public void testRequestDepositAddressUnknownCurrencyMultipleMethodsWithNetwork() {
    stubFor(
        post(urlPathEqualTo("/0/private/DepositMethods"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        loadFile(
                            "/org/knowm/xchange/kraken/dto/account/example-deposit-methods-xrp.json"))));

    stubFor(
        post(urlPathEqualTo("/0/private/DepositAddresses"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        loadFile(
                            "/org/knowm/xchange/kraken/dto/account/example-deposit-addresses-xrp.json"))));

    DefaultRequestDepositAddressParams params =
        DefaultRequestDepositAddressParams.builder().currency(Currency.XRP).build();

    AddressWithTag address = classUnderTest.requestDepositAddressData(params);

    assertThat(address.getAddress()).isEqualTo("testXrpAddress");
    assertThat(address.getAddressTag()).isEqualTo("123");
  }

  @Test
  @SneakyThrows
  public void testRequestDepositMethodCaching() {
    stubFor(
        post(urlPathEqualTo("/0/private/DepositMethods"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        loadFile(
                            "/org/knowm/xchange/kraken/dto/account/example-deposit-methods-trx.json"))));

    stubFor(
        post(urlPathEqualTo("/0/private/DepositAddresses"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        loadFile(
                            "/org/knowm/xchange/kraken/dto/account/example-deposit-addresses-trx.json"))));

    exchange
        .getExchangeSpecification()
        .setExchangeSpecificParametersItem("cacheDepositMethods", true);

    DefaultRequestDepositAddressParams params =
        DefaultRequestDepositAddressParams.builder().currency(Currency.TRX).build();

    classUnderTest.requestDepositAddress(params);
    classUnderTest.requestDepositAddress(params);

    verify(1, postRequestedFor(urlEqualTo("/0/private/DepositMethods")));
  }

  @Test
  @SneakyThrows
  public void testRequestDepositMethodNoCache() {
    stubFor(
        post(urlPathEqualTo("/0/private/DepositMethods"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        loadFile(
                            "/org/knowm/xchange/kraken/dto/account/example-deposit-methods-trx.json"))));

    stubFor(
        post(urlPathEqualTo("/0/private/DepositAddresses"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        loadFile(
                            "/org/knowm/xchange/kraken/dto/account/example-deposit-addresses-trx.json"))));

    DefaultRequestDepositAddressParams params =
        DefaultRequestDepositAddressParams.builder().currency(Currency.TRX).build();

    exchange
        .getExchangeSpecification()
        .setExchangeSpecificParametersItem("cacheDepositMethods", false);

    classUnderTest.requestDepositAddress(params);
    classUnderTest.requestDepositAddress(params);

    verify(2, postRequestedFor(urlEqualTo("/0/private/DepositMethods")));
  }
}
