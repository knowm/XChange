package info.bitrich.xchangestream.cexio;

import java.io.*;
import java.util.Properties;

public class CexioProperties {

  private static final String fileName = "cexio-secret.keys";

  private String apiKey;
  private String secretKey;

  public CexioProperties() throws IOException {
    Properties properties = new Properties();
    InputStream input = new FileInputStream(fileName);
    properties.load(input);
    apiKey = properties.getProperty("api-key");
    secretKey = properties.getProperty("secret-key");
    input.close();
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getSecretKey() {
    return secretKey;
  }
}
