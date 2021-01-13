package org.knowm.xchange.bx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.knowm.xchange.exceptions.ExchangeException;

class BxProperties {

  private String apiKey;
  private String secretKey;
  private final String fileName = "bx-secret.keys";
  private final String API = "api-key";
  private final String SECRET = "secret-key";

  public BxProperties() throws IOException {
    File file = new File(fileName);
    if (!file.exists()) {
      throw new ExchangeException(String.format("Properties file %s doesn't exists", fileName));
    }
    Properties properties = new Properties();
    InputStream input = new FileInputStream(fileName);
    properties.load(input);
    apiKey = properties.getProperty(API);
    secretKey = properties.getProperty(SECRET);
    input.close();
  }

  private ExchangeException createException(String source) {
    return new ExchangeException(
        String.format("Please specify %s in the file %s", source, fileName));
  }

  public String getApiKey() {
    if (apiKey.isEmpty()) {
      throw createException(API);
    }
    return apiKey;
  }

  public String getSecretKey() {
    if (secretKey.isEmpty()) {
      throw createException(SECRET);
    }
    return secretKey;
  }
}
