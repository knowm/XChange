package info.bitrich.xchangestream.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author Foat Akhmadeev 08/06/2018 */
public class PropsLoader {
  private static final Logger LOG = LoggerFactory.getLogger(PropsLoader.class);

  public static LocalExchangeConfig loadKeys(String fileName, String originName, String prefix)
      throws IOException {
    String apiKeyName = prefix + ".api.key";
    String secretKeyName = prefix + ".secret.key";

    String apiKey = System.getProperty(apiKeyName);
    String secretKey = System.getProperty(secretKeyName);

    if (apiKey != null && secretKey != null) {
      return new LocalExchangeConfig(apiKey, secretKey);
    } else {
      LOG.info("Not found keys in system props, loading from a file {}...", fileName);
    }

    try (FileInputStream input = new FileInputStream(fileName)) {
      Properties properties = new Properties();
      properties.load(input);
      return new LocalExchangeConfig(
          properties.getProperty(apiKeyName), properties.getProperty(secretKeyName));
    } catch (FileNotFoundException e) {
      LOG.error("Please create {} file from {}", fileName, originName);
      throw e;
    }
  }

  // e.g. "-Dproxy.exec.line=/Applications/Charles.app/Contents/MacOS/Charles -headless"
  public static String proxyExecLine() {
    return System.getProperty("proxy.exec.line");
  }
}
