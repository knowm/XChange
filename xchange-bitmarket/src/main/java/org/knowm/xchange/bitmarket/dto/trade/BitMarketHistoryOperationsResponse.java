package org.knowm.xchange.bitmarket.dto.trade;

import org.knowm.xchange.bitmarket.dto.BitMarketAPILimit;
import org.knowm.xchange.bitmarket.dto.BitMarketBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kfonal
 */
public class BitMarketHistoryOperationsResponse extends BitMarketBaseResponse<BitMarketHistoryOperations> {

  /**
   * Constructor
   *
   * @param success
   * @param data
   * @param limit
   * @param error
   * @param errorMsg
   */
  public BitMarketHistoryOperationsResponse(@JsonProperty("success") boolean success, @JsonProperty("data") BitMarketHistoryOperations data,
      @JsonProperty("limit") BitMarketAPILimit limit, @JsonProperty("error") int error, @JsonProperty("errorMsg") String errorMsg) {

    super(success, data, limit, error, errorMsg);
  }
}
