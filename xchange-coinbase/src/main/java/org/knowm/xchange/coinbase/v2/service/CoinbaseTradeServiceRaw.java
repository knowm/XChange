package org.knowm.xchange.coinbase.v2.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.v2.Coinbase;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseBuyData.CoinbaseBuy;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseSellData.CoinbaseSell;
import org.knowm.xchange.currency.Currency;

class CoinbaseTradeServiceRaw extends CoinbaseBaseService {

  protected CoinbaseTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  /**
   * Authenticated resource that lets you purchase Bitcoin using the primary bank account that is
   * linked to your account. (You must link and verify your bank account through the website before
   * this API call will work). The underlying optional parameter agree_btc_amount_varies is set to
   * false.
   *
   * @see <a
   *     href="https://developers.coinbase.com/api/v2#place-buy-order">developers.coinbase.com/api/v2#place-buy-order</a>
   */
  public CoinbaseBuy buy(String accountId, BigDecimal total, Currency currency, boolean commit)
      throws IOException {

    String path = "/v2/accounts/" + accountId + "/buys";
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();
    BuyPayload payload = new BuyPayload(total, currency.getCurrencyCode(), commit, false);
    String body = new ObjectMapper().writeValueAsString(payload);
    String signature = getSignature(timestamp, HttpMethod.POST, path, body);

    showCurl(HttpMethod.POST, apiKey, timestamp, signature, path, body);

    return coinbase
        .buy(
            MediaType.APPLICATION_JSON,
            Coinbase.CB_VERSION_VALUE,
            apiKey,
            signature,
            timestamp,
            accountId,
            payload)
        .getData();
  }

  /**
   * Authenticated resource that lets you convert Bitcoin crediting your primary bank account on
   * Coinbase. (You must link and verify your bank account through the website before this API call
   * will work).
   *
   * @see <a
   *     href="https://developers.coinbase.com/api/v2#place-sell-order">developers.coinbase.com/api/v2#place-sell-order</a>
   */
  public CoinbaseSell sell(String accountId, BigDecimal total, Currency currency, boolean commit)
      throws IOException {

    return sellInternal(
        accountId, new SellPayload(total, currency.getCurrencyCode(), commit, false));
  }

  /**
   * Authenticated resource that lets you convert Bitcoin crediting your primary bank account on
   * Coinbase. (You must link and verify your bank account through the website before this API call
   * will work).
   *
   * @see <a
   *     href="https://developers.coinbase.com/api/v2#place-sell-order">developers.coinbase.com/api/v2#place-sell-order</a>
   */
  public CoinbaseSell quote(String accountId, BigDecimal total, Currency currency)
      throws IOException {

    return sellInternal(accountId, new SellPayload(total, currency.getCurrencyCode(), false, true));
  }

  private CoinbaseSell sellInternal(String accountId, SellPayload payload) throws IOException {

    String path = "/v2/accounts/" + accountId + "/sells";
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();
    String body = new ObjectMapper().writeValueAsString(payload);
    String signature = getSignature(timestamp, HttpMethod.POST, path, body);

    showCurl(HttpMethod.POST, apiKey, timestamp, signature, path, body);

    return coinbase
        .sell(
            MediaType.APPLICATION_JSON,
            Coinbase.CB_VERSION_VALUE,
            apiKey,
            signature,
            timestamp,
            accountId,
            payload)
        .getData();
  }

  class BuyPayload extends AbstractPayload {
    @JsonProperty String total;

    BuyPayload(BigDecimal total, String currency, boolean commit, boolean quote) {
      super(currency, commit, quote);
      this.total = total.toString();
    }
  }

  class SellPayload extends AbstractPayload {
    @JsonProperty String amount;

    SellPayload(BigDecimal amount, String currency, boolean commit, boolean quote) {
      super(currency, commit, quote);
      this.amount = amount.toString();
    }
  }

  abstract class AbstractPayload {
    @JsonProperty String currency;
    @JsonProperty boolean commit;
    @JsonProperty boolean quote;

    AbstractPayload(String currency, boolean commit, boolean quote) {
      this.currency = currency;
      this.commit = commit;
      this.quote = quote;
    }
  }
}
