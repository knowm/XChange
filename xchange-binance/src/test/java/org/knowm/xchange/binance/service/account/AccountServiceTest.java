package org.knowm.xchange.binance.service.account;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.account.BinanceFundingHistoryParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.service.account.AccountService;

public class AccountServiceTest {

  @RegisterExtension
  public final WireMockExtension wireMockRule =
      WireMockExtension.newInstance()
          .options(wireMockConfig().dynamicPort())
          .configureStaticDsl(true)
          .build();

  @Test
  @Timeout(
      value = 2000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  void withdrawSuccess() throws Exception {
    String response = withdraw("withdraw-200.json", 200);
    assertThat(response).isEqualTo("9c7662xxxxxxxxxc8bd");
  }

  @Test
  @Timeout(
      value = 2000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  void withdrawFailure() {
    Throwable exception = catchThrowable(() -> withdraw("withdraw-400.json", 400));
    assertThat(exception)
        .isInstanceOf(ExchangeSecurityException.class)
        .hasMessage("error message (HTTP status code: 400)");
  }

  @Test
  @Timeout(
      value = 2000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  void fiatDepositHistory() throws Exception {
    BinanceExchange exchange = createExchange();
    AccountService service = exchange.getAccountService();
    stubFiatOrders("0", "fiat-orders-deposit.json", 200);
    stubEmptyCryptoDepositHistory();
    stubEmptyCryptoWithdrawHistory();
    stubEmptyAssetDividend();

    BinanceFundingHistoryParams params = new BinanceFundingHistoryParams();
    params.setType(Type.DEPOSIT);
    List<FundingRecord> fundingHistory = service.getFundingHistory(params);

    assertThat(fundingHistory).isNotNull();
    assertThat(fundingHistory.size()).isGreaterThanOrEqualTo(2);

    // Check first deposit record
    FundingRecord firstRecord =
        fundingHistory.stream()
            .filter(
                r ->
                    r.getType() == Type.DEPOSIT
                        && r.getInternalId() != null
                        && r.getInternalId().equals("7d76d611-0568-4f43-afb6-24cac7767365"))
            .findFirst()
            .orElse(null);

    assertThat(firstRecord).isNotNull();
    assertThat(firstRecord.getCurrency()).isEqualTo(Currency.EUR);
    assertThat(firstRecord.getAmount()).isEqualByComparingTo(new BigDecimal("100.00"));
    assertThat(firstRecord.getFee()).isEqualByComparingTo(new BigDecimal("0.00"));
    assertThat(firstRecord.getStatus()).isEqualTo(Status.COMPLETE);
    assertThat(firstRecord.getDescription()).isEqualTo("BankAccount");
    assertThat(firstRecord.getType()).isEqualTo(Type.DEPOSIT);

    // Check second deposit record (Processing status)
    FundingRecord secondRecord =
        fundingHistory.stream()
            .filter(
                r ->
                    r.getType() == Type.DEPOSIT
                        && r.getInternalId() != null
                        && r.getInternalId().equals("8e87e722-1679-5g54-bgc7-35dbd8878476"))
            .findFirst()
            .orElse(null);

    assertThat(secondRecord).isNotNull();
    assertThat(secondRecord.getCurrency()).isEqualTo(Currency.USD);
    assertThat(secondRecord.getAmount()).isEqualByComparingTo(new BigDecimal("50.00"));
    assertThat(secondRecord.getFee()).isEqualByComparingTo(new BigDecimal("1.00"));
    assertThat(secondRecord.getStatus()).isEqualTo(Status.PROCESSING);
  }

  @Test
  @Timeout(
      value = 2000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  void fiatWithdrawalHistory() throws Exception {
    BinanceExchange exchange = createExchange();
    AccountService service = exchange.getAccountService();
    stubFiatOrders("1", "fiat-orders-withdraw.json", 200);
    stubEmptyCryptoDepositHistory();
    stubEmptyCryptoWithdrawHistory();
    stubEmptyAssetDividend();

    BinanceFundingHistoryParams params = new BinanceFundingHistoryParams();
    params.setType(Type.WITHDRAWAL);
    List<FundingRecord> fundingHistory = service.getFundingHistory(params);

    assertThat(fundingHistory).isNotNull();
    assertThat(fundingHistory.size()).isGreaterThanOrEqualTo(2);

    // Check first withdrawal record (Finished status)
    FundingRecord firstRecord =
        fundingHistory.stream()
            .filter(
                r ->
                    r.getType() == Type.WITHDRAWAL
                        && r.getInternalId() != null
                        && r.getInternalId().equals("9f98f833-2780-6h65-chd8-46ece9989587"))
            .findFirst()
            .orElse(null);

    assertThat(firstRecord).isNotNull();
    assertThat(firstRecord.getCurrency()).isEqualTo(Currency.GBP);
    assertThat(firstRecord.getAmount()).isEqualByComparingTo(new BigDecimal("75.00"));
    assertThat(firstRecord.getFee()).isEqualByComparingTo(new BigDecimal("2.50"));
    assertThat(firstRecord.getStatus()).isEqualTo(Status.COMPLETE);
    assertThat(firstRecord.getDescription()).isEqualTo("BankAccount");
    assertThat(firstRecord.getType()).isEqualTo(Type.WITHDRAWAL);

    // Check second withdrawal record (Failed status)
    FundingRecord secondRecord =
        fundingHistory.stream()
            .filter(
                r ->
                    r.getType() == Type.WITHDRAWAL
                        && r.getInternalId() != null
                        && r.getInternalId().equals("0a09a944-3891-7i76-die9-57fdf0090698"))
            .findFirst()
            .orElse(null);

    assertThat(secondRecord).isNotNull();
    assertThat(secondRecord.getCurrency()).isEqualTo(Currency.EUR);
    assertThat(secondRecord.getAmount()).isEqualByComparingTo(new BigDecimal("195.00"));
    assertThat(secondRecord.getFee()).isEqualByComparingTo(new BigDecimal("5.00"));
    assertThat(secondRecord.getStatus()).isEqualTo(Status.FAILED);
  }

  private String withdraw(String responseFileName, int statusCode) throws IOException {
    BinanceExchange exchange = createExchange();
    AccountService service = exchange.getAccountService();
    stubWithdraw(responseFileName, statusCode);

    return service.withdrawFunds(
        Currency.BTC, BigDecimal.TEN, "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa");
  }

  private void stubWithdraw(String fileName, int statusCode) {
    stubFor(
        post(urlPathEqualTo("/sapi/v1/capital/withdraw/apply"))
            .willReturn(
                aResponse()
                    .withStatus(statusCode)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile(fileName)));
  }

  private void stubFiatOrders(String transactionType, String fileName, int statusCode) {
    stubFor(
        get(urlPathEqualTo("/sapi/v1/fiat/orders"))
            .withQueryParam("transactionType", equalTo(transactionType))
            .willReturn(
                aResponse()
                    .withStatus(statusCode)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile(fileName)));
  }

  private void stubEmptyCryptoDepositHistory() {
    stubFor(
        get(urlPathEqualTo("/sapi/v1/capital/deposit/hisrec"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("[]")));
  }

  private void stubEmptyCryptoWithdrawHistory() {
    stubFor(
        get(urlPathEqualTo("/sapi/v1/capital/withdraw/history"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("[]")));
  }

  private void stubEmptyAssetDividend() {
    stubFor(
        get(urlPathEqualTo("/sapi/v1/asset/assetDividend"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\"rows\":[],\"total\":0}")));
  }

  private BinanceExchange createExchange() {
    BinanceExchange exchange =
        ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(BinanceExchange.class);
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();
    specification.setHost("localhost");
    specification.setSslUri("http://localhost:" + wireMockRule.getPort() + "/");
    specification.setPort(wireMockRule.getPort());
    specification.setShouldLoadRemoteMetaData(false);
    specification.setHttpReadTimeout(1000);
    exchange.applySpecification(specification);
    return exchange;
  }
}
