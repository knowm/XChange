package org.knowm.xchange.hitbtc.v2;

import org.junit.BeforeClass;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

public class BaseAuthenticatedServiceTest extends BaseServiceTest {

  private static final String API_KEY = "hitbtc_api_key";
  private static final String SECRET_KEY = "hitbtc_secret_key";

  public static String getApiKey() {
    return System.getProperty(API_KEY);
  }

  public static String getSecret() {
    return System.getProperty(SECRET_KEY);
  }

  @BeforeClass
  public static void setUpBaseClass() {

    exchangeSpecification = new ExchangeSpecification(HitbtcExchange.class);
    exchangeSpecification.setApiKey(getApiKey());
    exchangeSpecification.setSecretKey(getSecret());

    exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
  }
}
