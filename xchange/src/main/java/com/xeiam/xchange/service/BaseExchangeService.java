package com.xeiam.xchange.service;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.utils.Assert;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * <p>Abstract base class to provide the following to exchange services:</p>
 * <ul>
 * <li>Provision of standard specification parsing</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public abstract class BaseExchangeService {

  /**
   * Jackson JSON to Java object mapper
   */
  protected ObjectMapper mapper = new ObjectMapper();

  protected final String apiURI;
  protected final String apiSecretKey;
  protected final String apiVersion;

  protected final String username;
  protected final String password;

  /**
   * Initialise common properties from the exchange specification
   *
   * @param exchangeSpecification The exchange specification with the configuration parameters
   */
  protected BaseExchangeService(ExchangeSpecification exchangeSpecification) {

    Assert.notNull(exchangeSpecification, "exchangeSpecification cannot be null");

    // Configure the API URI
    if (exchangeSpecification.getParameter(ExchangeSpecification.API_URI) != null) {
      this.apiURI = (String) exchangeSpecification.getParameter(ExchangeSpecification.API_URI);
    } else {
      // Use the default
      this.apiURI = "https://mtgox.com";
    }

    // Configure the API secret key
    if (exchangeSpecification.getParameter(ExchangeSpecification.API_SECRET_KEY) != null) {
      this.apiSecretKey = (String) exchangeSpecification.getParameter(ExchangeSpecification.API_SECRET_KEY);
    } else {
      // Use the default
      this.apiSecretKey = null;
    }

    // Configure the API version
    if (exchangeSpecification.getParameter(ExchangeSpecification.API_VERSION) != null) {
      this.apiVersion = "v" + exchangeSpecification.getParameter(ExchangeSpecification.API_VERSION);
    } else {
      // Use the default
      this.apiVersion = "v1";
    }

    // Configure the credentials
    if (exchangeSpecification.getParameter(ExchangeSpecification.USERNAME) != null) {
      this.username = (String) exchangeSpecification.getParameter(ExchangeSpecification.USERNAME);
    } else {
      // Use the default
      this.username = null;
    }
    if (exchangeSpecification.getParameter(ExchangeSpecification.PASSWORD) != null) {
      this.password = (String) exchangeSpecification.getParameter(ExchangeSpecification.PASSWORD);
    } else {
      // Use the default
      this.password = null;
    }

  }


}
