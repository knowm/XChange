package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesOrder extends CryptoFacilitiesResult {

  private final String orderId;

  public CryptoFacilitiesOrder(@JsonProperty("result") String result, @JsonProperty("error") String error, @JsonProperty("orderId") String orderId) {

    super(result, error);

    this.orderId = orderId;
  }

  public String getOrderId() {
    return orderId;
  }

  @Override
  public String toString() {

    if (isSuccess())
      return "CryptoFacilitiesOrder [orderId=" + orderId + "]";
    else
      return super.toString();
  }

}
