package com.xeiam.xchange.service;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.utils.Assert;

/**
 * <p>
 * Abstract base class to provide the following to exchange services:
 * </p>
 * <ul>
 * <li>Provision of standard specification parsing</li>
 * </ul>
 */
public abstract class BaseExchangeService {

  protected static final String SUF_DEFAULT = "default";
  protected static final String IN_ORDER_SIZE_MIN = ".order.size.min.";
  protected static final String SUF_ORDER_SIZE_MIN_DEFAULT = IN_ORDER_SIZE_MIN + SUF_DEFAULT;
  protected static final String SUF_ORDER_SIZE_SCALE_DEFAULT = ".order.size.scale." + SUF_DEFAULT;
  protected static final String SUF_ORDER_PRICE_SCALE_DEFAULT = ".order.price.scale." + SUF_DEFAULT;
  protected static final String ORDER_FEE_LISTING = ".order.fee.listing.";
  protected static final String XCHANGE_ORDER_FEE_LISTING_DEFAULT = "xchange" + ORDER_FEE_LISTING + SUF_DEFAULT;

  /**
   * The exchange specification containing session-specific information
   */
  protected final ExchangeSpecification exchangeSpecification;

  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  protected BaseExchangeService(ExchangeSpecification exchangeSpecification) {

    Assert.notNull(exchangeSpecification, "exchangeSpecification cannot be null");

    this.exchangeSpecification = exchangeSpecification;
  }

}
