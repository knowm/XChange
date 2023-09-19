package org.knowm.xchange.bybit.dto.account.allcoins;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;

@Builder
@Jacksonized
@Value
public class BybitAllCoinsBalance {

  @JsonProperty("accountType")
  BybitAccountType accountType;

  @JsonProperty("memberId")
  String memberId;

  @JsonProperty("balance")
  List<BybitAllCoinBalance> balance;
}
