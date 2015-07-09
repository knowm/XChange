package com.xeiam.xchange.ripple;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.ripple.service.polling.RippleAccountService;
import com.xeiam.xchange.ripple.service.polling.RippleMarketDataService;
import com.xeiam.xchange.ripple.service.polling.RippleTradeService;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;

public class RippleExchange extends BaseExchange implements Exchange {

  public static final String TRUST_API_RIPPLE_COM = "trust.api.ripple.com";

  public static final String REST_API_RIPPLE_LABS = "https://api.ripple.com/";

  public static final String REST_API_LOCALHOST_PLAIN_TEXT = "http://localhost:5990/";

  private static final String README = "https://github.com/timmolter/XChange/tree/develop/xchange-ripple";

  public static final String ROUNDING_SCALE = "rounding.scale";

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  public void applySpecification(final ExchangeSpecification specification) {
    super.applySpecification(specification);

    if (specification.getSecretKey() != null && specification.getSecretKey().length() > 0 && specification.getSslUri().equals(REST_API_RIPPLE_LABS)
        && Boolean.parseBoolean(specification.getParameter(TRUST_API_RIPPLE_COM).toString()) == false) {
      throw new IllegalStateException(String.format("server %s has not been trusted - see %s for details", REST_API_RIPPLE_LABS, README));
    }

    this.pollingMarketDataService = new RippleMarketDataService(this);
    this.pollingTradeService = new RippleTradeService(this);
    this.pollingAccountService = new RippleAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    final ExchangeSpecification specification = new ExchangeSpecification(this.getClass().getCanonicalName());
    specification.setSslUri(REST_API_RIPPLE_LABS);
    specification.setExchangeName("Ripple");
    specification.setExchangeDescription("Ripple is a payment system, currency exchange and remittance network");

    // By default only use https://api.ripple.com/ for queries that do not require authentication, i.e. do not send secret key to Ripple labs servers. 
    specification.setExchangeSpecificParametersItem(TRUST_API_RIPPLE_COM, false);

    // Round to 10 decimal places on BigDecimal division
    specification.setExchangeSpecificParametersItem(ROUNDING_SCALE, 10);

    return specification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  /**
   * Converts a datetime string as returned from the Ripple REST API into a java date object. The string is the UTC time in format
   * yyyy-MM-dd'T'hh:mm:ss.SSS'Z' e.g. 2015-06-13T11:45:20.102Z
   * 
   * @throws ParseException
   */
  public static Date ToDate(final String datetime) throws ParseException {
    final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
    format.setTimeZone(TimeZone.getTimeZone("UTC"));
    return format.parse(datetime);
  }
}
