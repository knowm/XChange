package org.knowm.xchange.ripple;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

public class RippleServerTrustTest {

  /**
   * Make sure it is possible to create a default public query only Ripple connection without a
   * secret key.
   */
  @Test
  public void noSecretKeyTest() {
    final Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(RippleExchange.class.getName());
    assertThat(exchange).isInstanceOf(RippleExchange.class);
    assertThat(exchange.getExchangeSpecification().getSecretKey()).isNull();
    assertThat(exchange.getExchangeSpecification().getSslUri())
        .isEqualTo(RippleExchange.REST_API_RIPPLE_LABS);
  }

  /**
   * It should not be possible to initialise a RippleExchange with URL https://api.ripple.com/ using
   * a secret key if trust has not been explicitly enabled - an IllegalStateException should be
   * thrown when attempting to create the exchange.
   */
  @Test(expected = IllegalStateException.class)
  public void safetyNetTest() {
    final ExchangeSpecification specification =
        new ExchangeSpecification(RippleExchange.class.getName());
    specification.setSslUri(RippleExchange.REST_API_RIPPLE_LABS);
    specification.setSecretKey("s****************************");
    ExchangeFactory.INSTANCE.createExchange(specification);
  }

  /**
   * The recommended method for sending private transactions with a secret key is to use a trusted
   * REST API server, e.g. one running locally.
   */
  @Test
  public void localServerTest() {
    final ExchangeSpecification specification =
        new ExchangeSpecification(RippleExchange.class.getName());
    specification.setSslUri(""); // remove the default api.ripple.com connection
    specification.setPlainTextUri(RippleExchange.REST_API_LOCALHOST_PLAIN_TEXT);
    specification.setSecretKey("s****************************");

    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(specification);

    assertThat(exchange).isInstanceOf(RippleExchange.class);
    assertThat(exchange.getExchangeSpecification().getSecretKey())
        .isEqualTo("s****************************");
    assertThat(exchange.getExchangeSpecification().getSslUri()).isEqualTo("");
    assertThat(exchange.getExchangeSpecification().getPlainTextUri())
        .isEqualTo(RippleExchange.REST_API_LOCALHOST_PLAIN_TEXT);
  }

  /**
   * If someone insists on using https://api.ripple.com/ for private transactions make sure the
   * break glass works.
   */
  @Test
  public void breakGlassTest() {
    final ExchangeSpecification specification =
        new ExchangeSpecification(RippleExchange.class.getName());
    specification.setSslUri(RippleExchange.REST_API_RIPPLE_LABS);
    specification.setSecretKey("s****************************");
    specification.setExchangeSpecificParametersItem(
        RippleExchange.PARAMETER_TRUST_API_RIPPLE_COM, true);

    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(specification);

    assertThat(exchange).isInstanceOf(RippleExchange.class);
    assertThat(exchange.getExchangeSpecification().getSecretKey())
        .isEqualTo("s****************************");
    assertThat(exchange.getExchangeSpecification().getSslUri())
        .isEqualTo(RippleExchange.REST_API_RIPPLE_LABS);
  }
}
