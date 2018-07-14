package org.knowm.xchange.idex;

import org.knowm.xchange.ExchangeSpecification;

public class IdexExchangeSpecification extends ExchangeSpecification {

  public static final String IDEX_API =
      System.getProperty("xchange.idex.api", "https://api.idex.market/");

  public IdexExchangeSpecification() {
    super(IdexExchange.class);
  }

  @Override
  public String getExchangeClassName() {
    return IdexExchange.class.getCanonicalName();
  }

  @Override
  public String getExchangeName() {
    return "idex";
  }

  @Override
  public String getSslUri() {
    return IDEX_API;
  }
}
