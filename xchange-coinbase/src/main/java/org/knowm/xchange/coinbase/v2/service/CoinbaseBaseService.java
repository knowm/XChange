package org.knowm.xchange.coinbase.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.v2.Coinbase;
import org.knowm.xchange.coinbase.v2.CoinbaseAuthenticated;
import org.knowm.xchange.coinbase.v2.CoinbaseV2Digest;
import org.knowm.xchange.coinbase.v2.dto.marketdata.CoinbaseCurrencyData.CoinbaseCurrency;
import org.knowm.xchange.coinbase.v2.dto.marketdata.CoinbaseTimeData.CoinbaseTime;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.utils.HmacDigest;
import si.mazi.rescu.RestProxyFactory;

public class CoinbaseBaseService extends BaseExchangeService implements BaseService {

  protected final CoinbaseAuthenticated coinbase;
  protected final CoinbaseV2Digest signatureCreator2;

  protected CoinbaseBaseService(Exchange exchange) {

    super(exchange);
    coinbase =
        RestProxyFactory.createProxy(
            CoinbaseAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());

    signatureCreator2 =
        CoinbaseV2Digest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  /**
   * Unauthenticated resource that returns currencies supported on Coinbase.
   *
   * @return A list of currency names and their corresponding ISO code.
   * @see <a
   *     href="https://developers.coinbase.com/api/v2#get-currencies">developers.coinbase.com/api/v2#get-currencies</a>
   */
  public List<CoinbaseCurrency> getCoinbaseCurrencies() throws IOException {

    return coinbase.getCurrencies(Coinbase.CB_VERSION_VALUE).getData();
  }

  /**
   * Unauthenticated resource that tells you the server time.
   *
   * @return The current server time.
   * @see <a
   *     href="https://developers.coinbase.com/api/v2#get-current-time">developers.coinbase.com/api/v2#get-current-time</a>
   */
  public CoinbaseTime getCoinbaseTime() throws IOException {

    return coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData();
  }

  protected String getSignature(BigDecimal timestamp, HttpMethod method, String path, String body) {
    String secretKey = exchange.getExchangeSpecification().getSecretKey();
    String message = timestamp + method.toString() + path + (body != null ? body : "");
    return new HmacDigest("HmacSHA256", secretKey).hexDigest(message);
  }

  protected void showCurl(
      HttpMethod method,
      String apiKey,
      BigDecimal timestamp,
      String signature,
      String path,
      String json) {
    String headers =
        String.format(
            "-H 'CB-VERSION: 2017-11-26' -H 'CB-ACCESS-KEY: %s' -H 'CB-ACCESS-SIGN: %s' -H 'CB-ACCESS-TIMESTAMP: %s'",
            apiKey, signature, timestamp);
    if (method == HttpMethod.GET) {
      Coinbase.LOG.debug(String.format("curl %s https://api.coinbase.com%s", headers, path));
    } else if (method == HttpMethod.POST) {
      String payload = "-d '" + json + "'";
      Coinbase.LOG.debug(
          String.format(
              "curl -X %s -H 'Content-Type: %s' %s %s https://api.coinbase.com%s",
              method, MediaType.APPLICATION_JSON, headers, payload, path));
    }
  }

  public enum HttpMethod {
    GET,
    POST
  }
}
