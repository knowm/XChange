package com.xeiam.xchange.bitmarket.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.bitmarket.dto.BitMarketAPILimit;
import com.xeiam.xchange.bitmarket.dto.BitMarketBaseResponse;

/**
 * @author kfonal
 */
public class BitMarketHistoryTradesResponse extends BitMarketBaseResponse<BitMarketHistoryTrades> {

  /**
   * Constructor
   *
   * @param success
   * @param data
   * @param limit
   * @param error
   * @param errorMsg
   */
  public BitMarketHistoryTradesResponse(@JsonProperty("success") boolean success, @JsonProperty("data") BitMarketHistoryTrades data,
      @JsonProperty("limit") BitMarketAPILimit limit, @JsonProperty("error") int error, @JsonProperty("errorMsg") String errorMsg) {

    super(success, data, limit, error, errorMsg);
  }
}
