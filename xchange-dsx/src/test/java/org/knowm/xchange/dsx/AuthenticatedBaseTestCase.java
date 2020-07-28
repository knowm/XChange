package org.knowm.xchange.dsx;

import java.io.IOException;
import org.junit.BeforeClass;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;

/**
 * Base class for making test calls which require authentication to Dsx services. Since Dsx has no
 * test system, these credentials should be private to the person running them. Thus the tests will
 * be ignored for default suite runs. example -Ddsx_api_key=XXXXXXXXXX -Ddsx_secret_key=YYYYYYYYY
 */
public class AuthenticatedBaseTestCase {

  private static final String API_KEY_LOOKUP = "dsx_api_key";
  private static final String SECRET_KEY_LOOKUP = "dsx_secret_key";
  protected static Exchange EXCHANGE = null;

  @BeforeClass
  public static void setUpClass() throws IOException {

    String apiKey = System.getProperty(API_KEY_LOOKUP);
    String secretValue = System.getProperty(SECRET_KEY_LOOKUP);

    EXCHANGE =
        ExchangeFactory.INSTANCE.createExchange(DsxExchange.class.getName(), apiKey, secretValue);
    EXCHANGE.remoteInit();
  }
}
