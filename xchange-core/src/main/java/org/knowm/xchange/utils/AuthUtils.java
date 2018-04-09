package org.knowm.xchange.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.knowm.xchange.ExchangeSpecification;

public class AuthUtils {

  /**
   * Generates a BASE64 Basic Authentication String
   *
   * @return BASE64 Basic Authentication String
   */
  public static String getBasicAuth(String user, final String pass) {

    return "Basic " + java.util.Base64.getEncoder().encodeToString((user + ":" + pass).getBytes());
  }

  /**
   * Read the API & Secret key from a resource called {@code secret.keys}. NOTE: This file MUST
   * NEVER be commited to source control. It is therefore added to .gitignore.
   */
  public static void setApiAndSecretKey(ExchangeSpecification exchangeSpec) {

    setApiAndSecretKey(exchangeSpec, null);
  }

  /**
   * Read the API & Secret key from a resource called {@code prefix}-{@code secret.keys}. NOTE: This
   * file MUST NEVER be commited to source control. It is therefore added to .gitignore.
   */
  public static void setApiAndSecretKey(ExchangeSpecification exchangeSpec, String prefix) {

    Properties props = getSecretProperties(prefix);

    if (props != null) {
      exchangeSpec.setApiKey(props.getProperty("apiKey"));
      exchangeSpec.setSecretKey(props.getProperty("secretKey"));
    }
  }

  /**
   * Read the secret properties from a resource called {@code prefix}-{@code secret.keys}. NOTE:
   * This file MUST NEVER be commited to source control. It is therefore added to .gitignore.
   *
   * @return The properties or null
   */
  public static Properties getSecretProperties(String prefix) {

    String resource = prefix != null ? prefix + "-secret.keys" : "secret.keys";

    // First try to find the keys in the classpath
    InputStream inStream = AuthUtils.class.getResourceAsStream("/" + resource);

    // Next try to find the keys in the user's home/.ssh dir
    File keyfile = new File(System.getProperty("user.home") + "/" + ".ssh", resource);
    if (inStream == null && keyfile.isFile()) {
      try {
        inStream = new FileInputStream(keyfile);
      } catch (IOException e) {
        // do nothing
      }
    }

    Properties props = null;
    if (inStream != null) {
      try {
        props = new Properties();
        props.load(inStream);
        return props;
      } catch (IOException e) {
        // do nothing
      }
    }
    return props;
  }
}
