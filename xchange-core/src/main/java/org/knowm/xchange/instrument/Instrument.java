package org.knowm.xchange.instrument;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import org.knowm.xchange.currency.CurrencyPair;

/**
 * Base object for financial instruments supported in xchange such as CurrencyPair, Future or Option
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@Type(value = CurrencyPair.class, name = "currencyPair")})
public abstract class Instrument implements Serializable {

  private static final long serialVersionUID = 414711266389792746L;
}
