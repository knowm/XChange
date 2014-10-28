package com.xeiam.xchange.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class ConfigurationManager {
  final public static ConfigurationManager CFG_MGR = new ConfigurationManager();

  final private static Logger log = LoggerFactory.getLogger(ConfigurationManager.class);

  final private static URL DEFAULT_REMOTE;

  static {
    try {
      DEFAULT_REMOTE = new URL("https://github.com/timmolter/XChange/raw/develop/xchange-core/src/main/resources/configuration.properties");
    } catch (MalformedURLException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  private File override;
  private File local;

  private URL remote;

  private Properties properties;

  public void init() {
    override = getFile("xchange.configuration.override", override, true);

    if (override != null) {
      log.debug("Override file: {}; updating disabled");
      init(override);
      return;
    }

    local = getFile("xchange.configuration.local", local, false);
    if (local != null) {
      remote = getRemote("xchange.configuration.remote", remote);
      update();
      init(local);
    } else {
      initInternal();
    }
  }

  private static URL getRemote(String propertyKey, URL remote) {
    String userRemote = System.getProperty(propertyKey);
    if (userRemote != null) {
      try {
        return new URL(userRemote);
      } catch (MalformedURLException e) {
        throw new IllegalStateException(e);
      }
    } else {
      return remote != null ? remote : DEFAULT_REMOTE;
    }
  }

  private static File getFile(String propertyKey, File defaultFile, boolean requireReadable) {
    String propertyValue = System.getProperty(propertyKey);
    if (propertyValue == null) {
      return defaultFile;
    } else {
      File result = new File(propertyValue);
      if (requireReadable && !result.canRead()) {
        throw new IllegalStateException("Override file not readable");
      }
      return result;
    }
  }

  public void update() {
  }

  private void initInternal() throws IOException {

    InputStream input = getClass().getClassLoader().getResourceAsStream("configuration.properties");
    if (input == null)
      throw new IllegalStateException("Configuration resource not found");

    try {
      init(input);
    } finally {
      input.close();
    }
  }

  private void init(File file) throws IOException {
    InputStream input = new FileInputStream(file);
    try {
      init(input);
    } finally {
      input.close();
    }
  }

  private void init(InputStream input) throws IOException {
    Properties result = new Properties();
    result.load(input);
    properties = result;
  }

  public void setOverride(File override) {
    this.override = override;
  }

  public void setLocal(File local) {
    this.local = local;
  }

  public void setRemote(URL remote) {
    this.remote = remote;
  }

  /**
   * Returns the current configuration properties. May never return null.
   *
   * @return the current configuration properties. Never a null.
   */
  public Properties getProperties() {
    if (properties == null) {
      init();
    }
    return properties;
  }
}
