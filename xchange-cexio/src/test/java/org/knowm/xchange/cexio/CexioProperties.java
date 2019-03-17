package org.knowm.xchange.cexio;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CexioProperties {

  private final String FILENAME = "cexio.keys";
  private final String USER_NAME = "user-name";
  private final String API = "api-key";
  private final String SECRET = "secret-key";

  private String userName;
  private String apiKey;
  private String secretKey;
  private boolean valid;

  public CexioProperties() throws IOException {

    Properties properties = new Properties();
    try (InputStream input = getClass().getResourceAsStream(FILENAME)) {
      properties.load(input);
      userName = properties.getProperty(USER_NAME);
      apiKey = properties.getProperty(API);
      secretKey = properties.getProperty(SECRET);

      if (userName == null || userName.isEmpty()) {
        throw new IllegalArgumentException("USER_NAME key is missing");
      }
      if (apiKey == null || apiKey.isEmpty()) {
        throw new IllegalArgumentException("API key is missing");
      }
      if (secretKey == null || secretKey.isEmpty()) {
        throw new IllegalArgumentException("Secret key is missing");
      }
      valid = true;
    } catch (Exception ignored) {
      valid = false;
    }
  }

  public boolean isValid() {
    return valid;
  }

  public String getUserName() {
    return userName;
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getSecretKey() {
    return secretKey;
  }
}
