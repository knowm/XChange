package org.knowm.xchange.therock.dto.account;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class TheRockBalances {

  private List<TheRockBalance> balances;

  public List<TheRockBalance> getBalances() {
    return balances;
  }

  @Override
  public String toString() {
    return String.format("TheRockBalances{balances=%s}", balances);
  }
}
