package org.knowm.xchange.bitmarket.dto.trade;

import java.util.List;
import java.util.Map;

import org.knowm.xchange.bitmarket.dto.BitMarketAPILimit;
import org.knowm.xchange.bitmarket.dto.BitMarketBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kfonal
 */
public class BitMarketOrdersResponse extends BitMarketBaseResponse<Map<String, Map<String, List<BitMarketOrder>>>> {

  /**
   * Constructor
   *
   * @param success
   * @param data
   * @param limit
   * @param error
   * @param errorMsg
   */
  public BitMarketOrdersResponse(@JsonProperty("success") boolean success, @JsonProperty("data") Map<String, Map<String, List<BitMarketOrder>>> data,
      @JsonProperty("limit") BitMarketAPILimit limit, @JsonProperty("error") int error, @JsonProperty("errorMsg") String errorMsg) {

    super(success, data, limit, error, errorMsg);
  }
}
