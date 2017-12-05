package org.knowm.xchange.wex.v3.dto.account;

import org.knowm.xchange.wex.v3.dto.WexReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Ondřej Novotný
 */
public class WexWithDrawInfoReturn extends WexReturn<WexWithdrawInfo> {

  /**
   * Constructor
   *
   * @param success True if successful
   * @param value The BTC-e account info
   * @param error Any error
   */
  public WexWithDrawInfoReturn(@JsonProperty("success") boolean success, @JsonProperty("return") WexWithdrawInfo value,
      @JsonProperty("error") String error) {
    super(success, value, error);
  }
}
