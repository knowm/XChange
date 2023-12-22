package org.knowm.xchange.upbit.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.upbit.service.UpbitArrayOrMessageDeserializer;

/**
 * @author interwater
 */
@JsonDeserialize(using = UpbitBalances.UpbitBalancesDeserializer.class)
public class UpbitBalances {
  private final UpbitBalance[] balances;

  public UpbitBalance[] getBalances() {
    return balances;
  }

  public UpbitBalances(@JsonProperty() UpbitBalance[] balances) {
    this.balances = balances;
  }

  static class UpbitBalancesDeserializer
      extends UpbitArrayOrMessageDeserializer<UpbitBalance, UpbitBalances> {
    public UpbitBalancesDeserializer() {
      super(UpbitBalance.class, UpbitBalances.class);
    }
  }
}
