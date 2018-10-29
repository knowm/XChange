package org.knowm.xchange.bl3p.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bl3p.dto.Bl3pResult;

public class Bl3pWithdrawFunds extends Bl3pResult<Bl3pWithdrawFunds.Bl3pWithdrawFundsData> {

  public static class Bl3pWithdrawFundsData {
    @JsonProperty("id")
    public int id;
  }
}
