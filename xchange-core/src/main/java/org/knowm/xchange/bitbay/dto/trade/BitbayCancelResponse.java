package org.knowm.xchange.bitbay.dto.trade;

import org.knowm.xchange.bitbay.dto.BitbayBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Z. Dolezal
 */
public class BitbayCancelResponse extends BitbayBaseResponse {

  public BitbayCancelResponse(@JsonProperty("success") boolean success, @JsonProperty("code") int code, @JsonProperty("message") String errorMsg) {
    super(success, code, errorMsg);
  }
}
