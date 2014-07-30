package com.xeiam.xchange.bitstamp.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.bitstamp.dto.BitstampBaseResponse;

/**
 * @author Matija Mazi
 */
public final class BitstampWithdrawal extends BitstampBaseResponse {

  private final Integer id;

  /**
   * Constructor
   * 
   * @param id
   */
  public BitstampWithdrawal(@JsonProperty("id") Integer id, @JsonProperty("error") String error) {

    super(error);
    this.id = id;
  }

  public Integer getId() {

    return id;
  }

  @Override
  public String toString() {

    return String.format("Withdrawal{id=%s}", id);
  }
}
