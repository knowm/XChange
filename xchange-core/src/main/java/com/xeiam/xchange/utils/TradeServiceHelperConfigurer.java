package com.xeiam.xchange.utils;

import static org.apache.commons.io.IOUtils.closeQuietly;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradeServiceHelperConfigurer {

  final public static TradeServiceHelperConfigurer CFG = new TradeServiceHelperConfigurer();

  final private static Logger log = LoggerFactory.getLogger(TradeServiceHelperConfigurer.class);

  final private static String CFG_FILE_NAME = "xchange.properties";
  private Properties properties;

  /**
   * System property containing local override path for the configuration
   */
  public static final String KEY_OVERRIDE_PATH = "xchange.config.override";
  private File override;

  /**
   * System property containing local storage path for the configuration update file
   */
  public static final String KEY_LOCAL_PATH = "xchange.config.local";
  private File local;

  /**
   * If the override file is configured, either by a system property or programmatically, the properties are loaded from the file and exposed to the
   * exchange clients. If not, but the remote update was configured, either by a system property or programmatically, remote configuration is
   * downloaded, stored locally and used to load the configuration. If not, the internal configuration file, kept in xchange-core, is used. Exchange
   * client implementation should never call this method directly. Note: downloading a file over the Internet may take significant amount of time.
   *
   * @return false if update was not configured
   */
  public boolean update() throws IOException {

    override = getFile(KEY_OVERRIDE_PATH, override, true);

    if (override != null) {
      log.debug("Override file: {}; updating disabled");
      init(override);
      return false;
    }

    local = getFile(KEY_LOCAL_PATH, local, false);
    if (local != null) {
      init(local);
      return true;
    } else {
      initInternal();
      return false;
    }
  }

  /**
   * Returns the current configuration properties. May never return null.
   *
   * @return the current configuration properties. Never a null.
   */
  public Properties getProperties() {

    if (properties == null) {
      try {
        update();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return properties;
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

  private void initInternal() throws IOException {

    InputStream input = null;
    try {
      input = getClass().getClassLoader().getResourceAsStream(CFG_FILE_NAME);
      if (input == null) {
        throw new IllegalStateException("Configuration resource not found");
      }
      init(input);
    } finally {
      closeQuietly(input);
    }
  }

  private void init(File file) throws IOException {

    InputStream input = null;
    try {
      input = new FileInputStream(file);
      init(input);
    } finally {
      closeQuietly(input);
    }
  }

  private void init(InputStream input) throws IOException {

    Properties result = new Properties();
    result.load(input);
    properties = result;
  }

  public int getIntProperty(String key) {

    return Integer.parseInt(getProperties().getProperty(key));
  }

  public boolean getBoolProperty(String key) {

    String str = getProperties().getProperty(key);
    return str != null && Boolean.parseBoolean(str);
  }

  public BigDecimal getBigDecimalProperty(String key) {

    String str = getProperties().getProperty(key);
    return str == null ? null : new BigDecimal(str);
  }

  public void setOverride(File override) {

    this.override = override;
  }

  public void setLocal(File local) {

    this.local = local;
  }

}
