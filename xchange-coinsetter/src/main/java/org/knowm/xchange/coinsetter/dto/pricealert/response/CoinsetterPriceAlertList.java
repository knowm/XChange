package org.knowm.xchange.coinsetter.dto.pricealert.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A list of price alerts.
 */
public class CoinsetterPriceAlertList {

  private CoinsetterPriceAlert[] priceAlerts;

  public CoinsetterPriceAlertList(@JsonProperty("PriceAlerts") CoinsetterPriceAlert[] priceAlerts) {

    this.priceAlerts = priceAlerts;
  }

  public CoinsetterPriceAlert[] getPriceAlerts() {

    return priceAlerts;
  }

  public void setPriceAlerts(CoinsetterPriceAlert[] priceAlerts) {

    this.priceAlerts = priceAlerts;
  }

}
