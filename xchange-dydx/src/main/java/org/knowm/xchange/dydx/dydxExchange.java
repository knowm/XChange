package org.knowm.xchange.dydx;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;

/**
 * Author: Max Gao (gaamox@tutanota.com) Created: 20-02-2021
 *
 * <p>V3 Documentation: https://docs.dydx.exchange Legacy Documentation:
 * https://legacy-docs.dydx.exchange
 */
public class dydxExchange extends BaseExchange {
  public static final String V3 = "v3";
  public static final String V3_ROPSTEN = "v3_ropsten";
  public static final String V1 = "v1";

  @Override
  public void initServices() {}

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.dydx.exchange");
    exchangeSpecification.setHost("api.dydx.exchange");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("dydx");
    exchangeSpecification.setExchangeDescription("dydx Decentralized Exchange");
    exchangeSpecification.setExchangeSpecificParametersItem("version", V1);

    return exchangeSpecification;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    super.applySpecification(exchangeSpecification);
  }
}
