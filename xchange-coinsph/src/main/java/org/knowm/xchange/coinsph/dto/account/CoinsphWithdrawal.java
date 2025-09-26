package org.knowm.xchange.coinsph.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.coinsph.dto.CoinsphResponse;

/**
 * Represents a withdrawal response from Coins.ph API Based on POST
 * /openapi/wallet/v1/withdraw/apply endpoint
 */
@Getter
@ToString
public class CoinsphWithdrawal extends CoinsphResponse {

  private final String id;

  public CoinsphWithdrawal(@JsonProperty("id") String id) {
    this.id = id;
  }
}
