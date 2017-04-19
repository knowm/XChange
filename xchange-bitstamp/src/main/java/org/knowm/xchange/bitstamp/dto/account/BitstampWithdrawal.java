package org.knowm.xchange.bitstamp.dto.account;

import org.knowm.xchange.bitstamp.dto.BitstampBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

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
