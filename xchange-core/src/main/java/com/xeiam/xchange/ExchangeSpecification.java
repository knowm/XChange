/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * Specification to provide the following to {@link ExchangeFactory}:
 * </p>
 * <ul>
 * <li>Provision of required exchangeSpecificParameters for creating an {@link Exchange}</li>
 * <li>Provision of optional exchangeSpecificParameters for additional configuration</li>
 * </ul>
 * <p>
 * Due to the JSON annotations, you can externalise your exchange configuration as follows:
 * </p>
 * <code>config.yaml:</code>
 * 
 * <pre>
 * # Mt Gox configuration
 * mtgox:
 *   tradeFeePercent: 0.6
 *   apiKey: exampleApiKey
 *   secretKey: exampleSecretKey
 *   exchangeClassName: com.xeiam.xchange.mtgox.v1.MtGoxExchange
 * 
 * # BTC-E configuration
 * btce:
 *   tradeFeePercent: 0.2
 *   apiKey: exampleApiKey
 *   secretKey: exampleSecretKey
 *   exchangeClassName: com.xeiam.xchange.btce.BTCEExchange
 * 
 * # Bitstamp configuration
 * bitstamp:
 *   tradeFeePercent: 0.5
 *   userName: exampleUser
 *   password: examplePassword
 *   exchangeClassName: com.xeiam.xchange.bitstamp.BitstampExchange
 * 
 * </pre>
 * <p>
 * Which is used to populate a <code>Configuration</code> object:
 * </p>
 * 
 * <pre>
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * public class Configuration {
 * 
 *   private ExchangeSpecification mtgox;
 *   private ExchangeSpecification btce;
 *   private ExchangeSpecification bitstamp;
 * 
 *   public ExchangeSpecification getMtgox() {
 * 
 *     return mtgox;
 *   }
 * 
 *   public ExchangeSpecification getBtce() {
 * 
 *     return btce;
 *   }
 * 
 *   public ExchangeSpecification getBitstamp() {
 * 
 *     return bitstamp;
 *   }
 * 
 * }
 * </pre>
 * <p>
 * And read it in at application startup:
 * </p>
 * 
 * <pre>
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
 * InputStream fis = new FileInputStream(&quot;config.yaml&quot;);
 * Configuration configuration = mapper.readValue(fis, Configuration.class);
 * </pre>
 * <p>
 * The <code>YAMLFactory</code> requires an additional Maven dependency in your application:
 * </p>
 * 
 * <pre>
 * {@code
 * <dependency>
 *   <groupId>com.fasterxml.jackson.dataformat</groupId>
 *   <artifactId>jackson-dataformat-yaml</artifactId>
 *   <version>${jackson.version}</version>
 * </dependency>
 * }
 * </pre>
 */
public class ExchangeSpecification {

  @JsonProperty
  private String exchangeName;

  @JsonProperty
  private String exchangeDescription;

  @JsonProperty
  private Double tradeFeePercent = Double.valueOf(0.0);

  @JsonProperty
  private String minTradeFee;

  @JsonProperty
  private String userName;

  @JsonProperty
  private String password;

  @JsonProperty
  private String secretKey;

  @JsonProperty
  private String apiKey;

  @JsonProperty
  private String sslUri;

  @JsonProperty
  private String plainTextUri;

  @JsonProperty
  private String sslUriStreaming;

  @JsonProperty
  private String plainTextUriStreaming;

  @JsonProperty
  private String host;

  @JsonProperty
  private int port = 80;

  private final String exchangeClassName;

  @JsonProperty
  private Map<String, Object> exchangeSpecificParameters = new HashMap<String, Object>();

  /**
   * Dynamic binding
   * 
   * @param exchangeClassName The exchange class name (e.g. "com.xeiam.xchange.mtgox.v1.MtGoxExchange")
   */
  @JsonCreator
  public ExchangeSpecification(@JsonProperty("exchangeClassName") String exchangeClassName) {

    this.exchangeClassName = exchangeClassName;
  }

  /**
   * Static binding
   * 
   * @param exchangeClass The exchange class
   */
  public ExchangeSpecification(Class exchangeClass) {

    this.exchangeClassName = exchangeClass.getCanonicalName();
  }

  /**
   * @return The exchange class name for loading at runtime
   */
  public String getExchangeClassName() {

    return exchangeClassName;
  }

  /**
   * @param key The key into the parameter map (recommend using the provided standard static entries)
   * @return Any additional exchangeSpecificParameters that the {@link Exchange} may consume to configure services
   */
  public Object getParameter(String key) {

    return exchangeSpecificParameters.get(key);
  }

  /**
   * Get the host name of the server providing data (e.g. "mtgox.com").
   * 
   * @return the host name
   */
  public String getHost() {

    return host;
  }

  /**
   * Set the host name of the server providing data.
   * 
   * @param host the host name
   */
  public void setHost(String host) {

    this.host = host;
  }

  /**
   * Get the API key. For MtGox this would be the "Rest-Key" field.
   * 
   * @return the API key
   */
  public String getApiKey() {

    return apiKey;
  }

  /**
   * Set the API key. For MtGox this would be the "Rest-Key" field.
   * 
   * @param apiKey the API key
   */
  public void setApiKey(String apiKey) {

    this.apiKey = apiKey;
  }

  /**
   * Get the port number of the server providing direct socket data (e.g. "1337").
   * 
   * @return the port number
   */
  public int getPort() {

    return port;
  }

  /**
   * Set the port number of the server providing direct socket data (e.g. "1337").
   * 
   * @param port the port number
   */
  public void setPort(int port) {

    this.port = port;
  }

  /**
   * Get the API secret key typically used in HMAC signing of requests. For MtGox this would be the "Rest-Sign" field.
   * 
   * @return the secret key
   */
  public String getSecretKey() {

    return secretKey;
  }

  /**
   * Set the API secret key typically used in HMAC signing of requests. For MtGox this would be the "Rest-Sign" field.
   * 
   * @param secretKey the secret key
   */
  public void setSecretKey(String secretKey) {

    this.secretKey = secretKey;
  }

  /**
   * Get the URI to reach the <b>root</b> of the exchange API for SSL queries
   * (e.g. use "https://example.com:8443/exchange", not "https://example.com:8443/exchange/api/v3/trades").
   * 
   * @return the SSL URI
   */
  public String getSslUri() {

    return sslUri;
  }

  /**
   * Set the URI to reach the <b>root</b> of the exchange API for SSL queries
   * (e.g. use "https://example.com:8443/exchange", not "https://example.com:8443/exchange/api/v3/trades").
   * 
   * @param uri the SSL URI
   */
  public void setSslUri(String uri) {

    this.sslUri = uri;
  }

  /**
   * Get the URI to reach the <b>root</b> of the exchange API for plaintext (non-SSL) queries
   * (e.g. use "http://example.com:8080/exchange", not "http://example.com:8080/exchange/api/v3/trades")
   * 
   * @return the plain text URI
   */
  public String getPlainTextUri() {

    return plainTextUri;
  }

  /**
   * Set the URI to reach the <b>root</b> of the exchange API for plaintext (non-SSL) queries
   * (e.g. use "http://example.com:8080/exchange", not "http://example.com:8080/exchange/api/v3/trades")
   * 
   * @param plainTextUri the plain text URI
   */
  public void setPlainTextUri(String plainTextUri) {

    this.plainTextUri = plainTextUri;
  }

  /**
   * Set the URI for plain text streaming.
   * 
   * @return the plaintext streaming URI
   */
  public String getPlainTextUriStreaming() {

    return plainTextUriStreaming;
  }

  /**
   * Set the URI for plain text streaming.
   * 
   * @param plainTextUriStreaming the plaintext streaming URI
   */
  public void setPlainTextUriStreaming(String plainTextUriStreaming) {

    this.plainTextUriStreaming = plainTextUriStreaming;
  }

  /**
   * Get the URI for SSL streaming.
   * 
   * @return the URI for ssl streaming
   */
  public String getSslUriStreaming() {

    return sslUriStreaming;
  }

  /**
   * Set the URI for SSL streaming.
   * 
   * @param sslUriStreaming the URI for ssl streaming
   */
  public void setSslUriStreaming(String sslUriStreaming) {

    this.sslUriStreaming = sslUriStreaming;
  }

  /**
   * Get the arbitrary exchange-specific parameters to be passed to the exchange implementation.
   * 
   * @return a Map of named exchange-specific parameter values
   */
  public Map<String, Object> getExchangeSpecificParameters() {

    return exchangeSpecificParameters;
  }

  /**
   * Set the arbitrary exchange-specific parameters to be passed to the exchange implementation.
   * 
   * @param exchangeSpecificParameters a Map of named exchange-specific parameter values
   */
  public void setExchangeSpecificParameters(Map<String, Object> exchangeSpecificParameters) {

    this.exchangeSpecificParameters = exchangeSpecificParameters;
  }

  /**
   * Get the password for authentication.
   * 
   * @return the password
   */
  public String getPassword() {

    return password;
  }

  /**
   * Set the password for authentication.
   * 
   * @param password the password
   */
  public void setPassword(String password) {

    this.password = password;
  }

  /**
   * Get the username for authentication.
   * 
   * @return the username
   */
  public String getUserName() {

    return userName;
  }

  /**
   * Set the username for authentication.
   * 
   * @param userName the username
   */
  public void setUserName(String userName) {

    this.userName = userName;
  }

  /**
   * Get the exchange name.
   * 
   * @return the exchange name (e.g. "Mt Gox")
   */
  public String getExchangeName() {

    return exchangeName;
  }

  /**
   * Set the exchange name (e.g. "Mt Gox").
   * 
   * @param exchangeName the exchange name
   */
  public void setExchangeName(String exchangeName) {

    this.exchangeName = exchangeName;
  }

  /**
   * Get the exchange description (e.g. "Major exchange specialising in USD, EUR, GBP").
   * 
   * @return the exchange description
   */
  public String getExchangeDescription() {

    return exchangeDescription;
  }

  /**
   * Set the exchange description (e.g. "Major exchange specialising in USD, EUR, GBP").
   * 
   * @param exchangeDescription the exchange description
   */
  public void setExchangeDescription(String exchangeDescription) {

    this.exchangeDescription = exchangeDescription;
  }

  /**
   * Get the per-trade fee, expressed as a percentage of the trade amount (e.g. 0.6 is 0.6%).
   * <p>
   * Some exchanges offer a sliding scale that is earned based on trade history so this is normally set externally.
   * </p>
   * 
   * @return per-trade fee
   */
  public Double getTradeFeePercent() {

    return tradeFeePercent;
  }

  /**
   * Set the per-trade fee, expressed as a percentage of the trade amount (e.g. 0.6 is 0.6%).
   * <p>
   * Some exchanges offer a sliding scale that is earned based on trade history so this is normally set externally.
   * </p>
   * 
   * @param tradeFeePercent per-trade fee
   */
  public void setTradeFeePercent(Double tradeFeePercent) {

    this.tradeFeePercent = tradeFeePercent;
  }

  /**
   * Get the minimum per-trade fee per trade expressed in the exchange's local currency (e.g. "USD 0.25").
   * <p>
   * Some exchanges offer a sliding scale that is earned based on trade history so this is normally set externally.
   * </p>
   * 
   * @return the minimum fee per trade
   */
  public String getMinTradeFee() {

    return minTradeFee;
  }

  /**
   * Set the minimum per-trade fee per trade expressed in the exchange's local currency (e.g. "USD 0.25").
   * <p>
   * Some exchanges offer a sliding scale that is earned based on trade history so this is normally set externally.
   * </p>
   * 
   * @param minTradeFee the minimum fee per trade
   */
  public void setMinTradeFee(String minTradeFee) {

    this.minTradeFee = minTradeFee;
  }
}
