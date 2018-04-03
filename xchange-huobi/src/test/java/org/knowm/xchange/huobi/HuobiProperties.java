package org.knowm.xchange.huobi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class HuobiProperties {

  private String apiKey;
  private String secretKey;

  HuobiProperties() throws IOException {
    Properties properties = new Properties();
    InputStream input = new FileInputStream("huobi-secret.keys");
    properties.load(input);
    apiKey = properties.getProperty("api-key");
    secretKey = properties.getProperty("secret-key");
  }

  String getApiKey() {
    return apiKey;
  }

  String getSecretKey() {
    return secretKey;
  }
}
