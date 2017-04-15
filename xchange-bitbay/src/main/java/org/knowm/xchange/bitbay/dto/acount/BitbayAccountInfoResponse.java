package org.knowm.xchange.bitbay.dto.acount;

import java.util.Map;

import org.knowm.xchange.bitbay.dto.BitbayBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Z. Dolezal
 */
public class BitbayAccountInfoResponse extends BitbayBaseResponse {

  private final Map<String, BitbayBalance> bitbayBalances;

  public BitbayAccountInfoResponse(@JsonProperty("balances") Map<String, BitbayBalance> bitbayBalances, @JsonProperty("success") boolean success,
      @JsonProperty("code") int code, @JsonProperty("message") String errorMsg) {
    super(success, code, errorMsg);

    this.bitbayBalances = bitbayBalances;
  }

  public Map<String, BitbayBalance> getBitbayBalances() {
    return bitbayBalances;
  }
}
