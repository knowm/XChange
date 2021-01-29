package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FtxSubAccountBalancesDto {

  private final Map<String, FtxSubAccountBalanceDto> balances = new HashMap<>();

  @JsonCreator
  public FtxSubAccountBalancesDto(List<FtxSubAccountBalanceDto> listOfBalances) {
    listOfBalances.forEach(
        ftxSubAccountBalanceDto -> {
          balances.put(ftxSubAccountBalanceDto.getCoin(), ftxSubAccountBalanceDto);
        });
  }

  public Map<String, FtxSubAccountBalanceDto> getBalances() {
    return balances;
  }

  @Override
  public String toString() {
    return "FtxSubAccountBalancesDto{" + "mapBalances=" + balances + '}';
  }
}
