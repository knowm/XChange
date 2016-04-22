package org.knowm.xchange.clevercoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.clevercoin.dto.CleverCoinBaseResponse;

/**
 * @author Karsten Nilsen
 */
public final class CleverCoinWithdrawal extends CleverCoinBaseResponse {

  private final Integer id;

  /**
   * Constructor
   * 
   * @param id
   */
  public CleverCoinWithdrawal(@JsonProperty("withdrawalID") Integer id, @JsonProperty("error") String error) {

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
