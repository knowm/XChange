package org.knowm.xchange.coinbase.v2.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.v2.Coinbase;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseAccountData.CoinbaseAccount;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbasePaymentMethodsData.CoinbasePaymentMethod;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseTransactionsResponse;
import org.knowm.xchange.currency.Currency;

public class CoinbaseAccountServiceRaw extends CoinbaseBaseService {

  public CoinbaseAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public CoinbaseTransactionsResponse getTransactions(String accountId) throws IOException {
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();

    return coinbase.getTransactions(
        Coinbase.CB_VERSION_VALUE, apiKey, signatureCreator2, timestamp, accountId);
  }

  public Map getDeposits(String accountId) throws IOException {
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();

    return coinbase.getDeposits(
        Coinbase.CB_VERSION_VALUE, apiKey, signatureCreator2, timestamp, accountId);
  }

  public Map getWithdrawals(String accountId) throws IOException {
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();

    return coinbase.getWithdrawals(
        Coinbase.CB_VERSION_VALUE, apiKey, signatureCreator2, timestamp, accountId);
  }

  /**
   * Authenticated resource that shows the current user accounts.
   *
   * @see <a
   *     href="https://developers.coinbase.com/api/v2#list-accounts">developers.coinbase.com/api/v2#list-accounts</a>
   */
  public List<CoinbaseAccount> getCoinbaseAccounts() throws IOException {
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();

    return coinbase
        .getAccounts(Coinbase.CB_VERSION_VALUE, apiKey, signatureCreator2, timestamp)
        .getData();
  }

  /**
   * Authenticated resource that shows the current user account for the give currency.
   *
   * @see <a
   *     href="https://developers.coinbase.com/api/v2#show-an-account">developers.coinbase.com/api/v2#show-an-account</a>
   */
  public CoinbaseAccount getCoinbaseAccount(Currency currency) throws IOException {
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();

    return coinbase
        .getAccount(
            Coinbase.CB_VERSION_VALUE,
            apiKey,
            signatureCreator2,
            timestamp,
            currency.getCurrencyCode())
        .getData();
  }

  /**
   * Authenticated resource that creates a new BTC account for the current user.
   *
   * @see <a
   *     href="https://developers.coinbase.com/api/v2#create-account">developers.coinbase.com/api/v2#create-account</a>
   */
  public CoinbaseAccount createCoinbaseAccount(String name) throws IOException {

    CreateCoinbaseAccountPayload payload = new CreateCoinbaseAccountPayload(name);

    String path = "/v2/accounts";
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();
    String body = new ObjectMapper().writeValueAsString(payload);
    String signature = getSignature(timestamp, HttpMethod.POST, path, body);
    showCurl(HttpMethod.POST, apiKey, timestamp, signature, path, body);

    return coinbase
        .createAccount(
            MediaType.APPLICATION_JSON,
            Coinbase.CB_VERSION_VALUE,
            apiKey,
            signature,
            timestamp,
            payload)
        .getData();
  }

  /**
   * Authenticated resource that shows the current user payment methods.
   *
   * @see <a
   *     href="https://developers.coinbase.com/api/v2#list-payment-methods">developers.coinbase.com/api/v2?shell#list-payment-methods</a>
   */
  public List<CoinbasePaymentMethod> getCoinbasePaymentMethods() throws IOException {
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();

    return coinbase
        .getPaymentMethods(Coinbase.CB_VERSION_VALUE, apiKey, signatureCreator2, timestamp)
        .getData();
  }

  public static class CreateCoinbaseAccountPayload {
    @JsonProperty String name;

    CreateCoinbaseAccountPayload(String name) {
      this.name = name;
    }
  }
}
