package org.knowm.xchange.bitmarket.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bitmarket.dto.BitMarketAPILimit;
import org.knowm.xchange.bitmarket.dto.BitMarketBaseResponse;

/** @author kfonal */
public class BitMarketAccountInfoResponse extends BitMarketBaseResponse<BitMarketAccountInfo> {

  public BitMarketAccountInfoResponse(
      @JsonProperty("success") boolean success,
      @JsonProperty("data") BitMarketAccountInfo accountInfo,
      @JsonProperty("limit") BitMarketAPILimit limit,
      @JsonProperty("error") int error,
      @JsonProperty("errorMsg") String errorMsg) {

    super(success, accountInfo, limit, error, errorMsg);
  }
}
