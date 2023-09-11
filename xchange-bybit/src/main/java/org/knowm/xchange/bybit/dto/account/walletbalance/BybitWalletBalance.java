package org.knowm.xchange.bybit.dto.account.walletbalance;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BybitWalletBalance {

  @JsonProperty("list")
  List<BybitAccountBalance> list;
}
