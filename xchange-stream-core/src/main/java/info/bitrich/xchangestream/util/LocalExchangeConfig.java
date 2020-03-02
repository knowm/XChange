package info.bitrich.xchangestream.util;

/** @author Foat Akhmadeev 08/06/2018 */
public class LocalExchangeConfig {
  private String apiKey;
  private String secretKey;
  private String proxyHost;
  private String proxyPort;

  public LocalExchangeConfig(String apiKey, String secretKey) {
    this.apiKey = apiKey;
    this.secretKey = secretKey;
  }

  public LocalExchangeConfig(String apiKey, String secretKey, String proxyHost, String proxyPort) {
    this.apiKey = apiKey;
    this.secretKey = secretKey;
    this.proxyHost = proxyHost;
    this.proxyPort = proxyPort;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getProxyHost() {
    return proxyHost;
  }

  public void setProxyHost(String proxyHost) {
    this.proxyHost = proxyHost;
  }

  public String getProxyPort() {
    return proxyPort;
  }

  public void setProxyPort(String proxyPort) {
    this.proxyPort = proxyPort;
  }
}
