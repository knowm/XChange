package org.knowm.xchange.bitmarket.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bitmarket.dto.BitMarketAPILimit;
import org.knowm.xchange.bitmarket.dto.BitMarketBaseResponse;

/** @author kfonal */
public class BitMarketTradeResponse extends BitMarketBaseResponse<BitMarketTrade> {

  /**
   * Constructor
   *
   * @param success
   * @param data
   * @param limit
   * @param error
   * @param errorMsg
   */
  public BitMarketTradeResponse(
      @JsonProperty("success") boolean success,
      @JsonProperty("data") BitMarketTrade data,
      @JsonProperty("limit") BitMarketAPILimit limit,
      @JsonProperty("error") int error,
      @JsonProperty("errorMsg") String errorMsg) {

    super(success, data, limit, error, errorMsg);
  }
}
