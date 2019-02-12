package org.knowm.xchange.bitfinex.v1.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BitfinexProperties {

  private final String FILENAME = "bitfinex-secret.keys";
  private final String API = "api-key";
  private final String SECRET = "secret-key";

  private String apiKey;
  private String secretKey;
  private boolean valid;

  public BitfinexProperties() throws IOException {

    Properties properties = new Properties();
    try (InputStream input = new FileInputStream(FILENAME)) {
      properties.load(input);
      apiKey = properties.getProperty(API);
      secretKey = properties.getProperty(SECRET);

      if (apiKey == null || apiKey.isEmpty()) {
        throw new IllegalArgumentException("API key is missing");
      }
      if (secretKey == null || secretKey.isEmpty()) {
        throw new IllegalArgumentException("Secret key is missing");
      }
      valid = true;
    } catch (FileNotFoundException ignored) {
      valid = false;
    }
  }

  public boolean isValid() {
    return valid;
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getSecretKey() {
    return secretKey;
  }
}
