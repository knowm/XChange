package org.knowm.xchange.cryptofacilities.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/**
 * @author Jean-Christophe Laruelle
 */

@Deprecated
public class CryptoFacilitiesContracts extends CryptoFacilitiesResult {

  private final List<CryptoFacilitiesContract> contracts;

  public CryptoFacilitiesContracts(@JsonProperty("result") String result, @JsonProperty("error") String error,
      @JsonProperty("contracts") List<CryptoFacilitiesContract> contracts) {

    super(result, error);

    this.contracts = contracts;
  }

  public List<CryptoFacilitiesContract> getContracts() {
    return contracts;
  }

  @Override
  public String toString() {

    String res = "CryptoFacilitiesContracts [contracts=";
    for (CryptoFacilitiesContract ct : contracts)
      res = res + ct.toString() + ", ";
    res = res + " ]";

    return res;
  }

}
