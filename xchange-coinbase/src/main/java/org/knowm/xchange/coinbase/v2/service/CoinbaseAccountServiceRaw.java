package org.knowm.xchange.coinbase.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.v2.Coinbase;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseAccountData.CoinbaseAccount;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbasePaymentMethodsData.CoinbasePaymentMethod;
import org.knowm.xchange.currency.Currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

class CoinbaseAccountServiceRaw extends CoinbaseBaseService {

  public CoinbaseAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  /**
   * Authenticated resource that shows the current user accounts.
   *
   * @see <a href="https://developers.coinbase.com/api/v2#list-accounts">developers.coinbase.com/api/v2#list-accounts</a>
   */
  public List<CoinbaseAccount> getCoinbaseAccounts() throws IOException {

    String path = "/v2/accounts";
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();
    String signature = getSignature(timestamp, path);
    showCurl(apiKey, timestamp, signature, path);
    
    return coinbase.getAccounts(Coinbase.CB_VERSION_VALUE, apiKey, signature, timestamp).getData();
  }

  /**
   * Authenticated resource that shows the current user account for the give currency.
   *
   * @see <a href="https://developers.coinbase.com/api/v2#show-an-account">developers.coinbase.com/api/v2#show-an-account</a>
   */
  public CoinbaseAccount getCoinbaseAccount(Currency currency) throws IOException {

    String path = "/v2/accounts/" + currency.getCurrencyCode();
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();
    String signature = getSignature(timestamp, path);
    showCurl(apiKey, timestamp, signature, path);
    
    return coinbase.getAccount(Coinbase.CB_VERSION_VALUE, apiKey, signature, timestamp, currency.getCurrencyCode()).getData();
  }

  /**
   * Authenticated resource that creates a new BTC account for the current user.
   *
   * @see <a href="https://developers.coinbase.com/api/v2#create-account">developers.coinbase.com/api/v2#create-account</a>
   */
  public CoinbaseAccount createCoinbaseAccount(String name) throws IOException {

    class Payload {
      @JsonProperty
      String name;
      Payload(String name) {
        this.name = name;
      }
    }
    
    Payload payload = new Payload(name);

    String path = "/v2/accounts";
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();
    String body = new ObjectMapper().writeValueAsString(payload);
    String signature = getSignature(timestamp, HttpMethod.POST, path, body);
    showCurl(HttpMethod.POST, apiKey, timestamp, signature, path, body);
    
    return coinbase.createAccount(MediaType.APPLICATION_JSON, Coinbase.CB_VERSION_VALUE, apiKey, signature, timestamp, payload).getData();
  }

  /**
   * Authenticated resource that shows the current user payment methods.
   *
   * @see <a href="https://developers.coinbase.com/api/v2#list-payment-methods">developers.coinbase.com/api/v2?shell#list-payment-methods</a>
   */
  public List<CoinbasePaymentMethod> getCoinbasePaymentMethods() throws IOException {

    String path = "/v2/payment-methods";
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();
    String signature = getSignature(timestamp, path);
    showCurl(apiKey, timestamp, signature, path);

    return coinbase.getPaymentMethods(Coinbase.CB_VERSION_VALUE, apiKey, signature, timestamp).getData();
  }

}
