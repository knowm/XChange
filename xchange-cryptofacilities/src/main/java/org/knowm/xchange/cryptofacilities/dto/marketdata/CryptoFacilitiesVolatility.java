package org.knowm.xchange.cryptofacilities.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/**
 * @author Jean-Christophe Laruelle
 */

@Deprecated
public class CryptoFacilitiesVolatility extends CryptoFacilitiesResult {

  private final BigDecimal volatility;

  public CryptoFacilitiesVolatility(@JsonProperty("result") String result, @JsonProperty("error") String error,
      @JsonProperty("volatility") BigDecimal volatility) {

    super(result, error);

    this.volatility = volatility;
  }

  public BigDecimal getVolatility() {
    return volatility;
  }

  @Override
  public String toString() {

    if (isSuccess())
      return "CryptoFacilitiesVolatility [volatility=" + volatility + "]";
    else
      return super.toString();
  }

}
