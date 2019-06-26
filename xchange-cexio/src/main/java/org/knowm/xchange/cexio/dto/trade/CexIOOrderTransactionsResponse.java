package org.knowm.xchange.cexio.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cexio.dto.CexIOApiResponse;

public class CexIOOrderTransactionsResponse extends CexIOApiResponse<CexIOOrderWithTransactions> {
  public CexIOOrderTransactionsResponse(
      @JsonProperty("e") final String e,
      @JsonProperty("data") final CexIOOrderWithTransactions data,
      @JsonProperty("ok") final String ok,
      @JsonProperty("error") final String error) {
    super(e, data, ok, error);
  }
}
