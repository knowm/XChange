package org.knowm.xchange.coinbase.v4.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.v2.service.CoinbaseTradeHistoryParams;
import org.knowm.xchange.coinbase.v2.Coinbase;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseAccountData.CoinbaseAccount;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseExpandTransactionsResponse;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbasePaymentMethodsData.CoinbasePaymentMethod;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseTransactionsResponse;
import org.knowm.xchange.currency.Currency;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CoinbaseAccountServiceRawCDP extends CoinbaseBaseServiceCDP {

  public CoinbaseAccountServiceRawCDP(Exchange exchange) {
    super(exchange);
  }

  public CoinbaseTransactionsResponse getTransactions(String accountId) throws IOException {

    return coinbase.getTransactions(
        signatureCreator2, accountId);
  }

  public CoinbaseExpandTransactionsResponse getExpandTransactions(String accountId, CoinbaseTradeHistoryParams params, String orderType)
      throws IOException {

    return coinbase.getExpandedTransactions(
              signatureCreator2,
              accountId,
              params.getLimit(),
              orderType,
              params.getStartId());
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

    List<CoinbaseAccount> returnList = new ArrayList<>();
    List<CoinbaseAccount> tmpList = null;

    String lastAccount = null;
    do {
      try {
        tmpList =
            coinbase
                .getAccounts(
                        signatureCreator2, 100, lastAccount)
                .getData();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }

      lastAccount = null;
      if (tmpList != null && tmpList.size() > 0) {
        returnList.addAll(tmpList);
        lastAccount = tmpList.get(tmpList.size() - 1).getId();
      }

    } while (lastAccount != null && isValidUUID(lastAccount));

    return returnList;
  }

  private boolean isValidUUID(String uuid) {
    try {
      UUID.fromString(uuid);
      return true;
    } catch (IllegalArgumentException exception) {
      return false;
    }
  }

  /**
   * Authenticated resource that shows the current user account for the give currency.
   *
   * @see <a
   *     href="https://developers.coinbase.com/api/v2#show-an-account">developers.coinbase.com/api/v2#show-an-account</a>
   */
  public CoinbaseAccount getCoinbaseAccount(Currency currency) throws IOException {

    return coinbase
        .getAccount(
            signatureCreator2,
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

    return coinbase
        .getPaymentMethods(signatureCreator2)
        .getData();
  }

  public static class CreateCoinbaseAccountPayload {
    @JsonProperty String name;

    CreateCoinbaseAccountPayload(String name) {
      this.name = name;
    }
  }
}
