package com.xeiam.xchange.bitmarket.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.bitmarket.dto.BitMarketAPILimit;
import com.xeiam.xchange.bitmarket.dto.BitMarketBaseResponse;

/**
 * @author kfonal
 */
public class BitMarketDepositResponse extends BitMarketBaseResponse<String> {

  /**
   * Constructor
   *
   * @param success
   * @param data
   * @param limit
   * @param error
   * @param errorMsg
   */
  public BitMarketDepositResponse(@JsonProperty("success") boolean success, @JsonProperty("data") String data,
      @JsonProperty("limit") BitMarketAPILimit limit, @JsonProperty("error") int error, @JsonProperty("errorMsg") String errorMsg) {

    super(success, data, limit, error, errorMsg);
  }
}
