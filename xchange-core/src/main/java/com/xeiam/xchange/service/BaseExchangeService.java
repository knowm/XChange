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

  protected static final String KEY_ORDER_SIZE_MIN_DEFAULT = "order.size.min.default";
  protected static final String KEY_ORDER_SIZE_SCALE_DEFAULT = "order.size.scale.default";
  protected static final String PREKEY_ORDER_SIZE_MIN = "order.size.min.";
  protected static final String KEY_ORDER_PRICE_SCALE_DEFAULT = "order.price.scale.default";
  protected static final String KEY_ORDER_FEE_POLICY_MAKER = "order.fee-policy.maker";

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
