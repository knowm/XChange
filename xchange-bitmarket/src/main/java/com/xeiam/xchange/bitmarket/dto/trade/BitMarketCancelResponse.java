package com.xeiam.xchange.bitmarket.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.bitmarket.dto.BitMarketAPILimit;
import com.xeiam.xchange.bitmarket.dto.BitMarketBaseResponse;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketBalance;

/**
 * @author kfonal
 */
public class BitMarketCancelResponse extends BitMarketBaseResponse<BitMarketBalance> {

  /**
   * Constructor
   *
   * @param success
   * @param data
   * @param limit
   * @param error
   * @param errorMsg
   */
  public BitMarketCancelResponse(@JsonProperty("success") boolean success, @JsonProperty("data") BitMarketBalance data,
      @JsonProperty("limit") BitMarketAPILimit limit, @JsonProperty("error") int error, @JsonProperty("errorMsg") String errorMsg) {

    super(success, data, limit, error, errorMsg);
  }
}
