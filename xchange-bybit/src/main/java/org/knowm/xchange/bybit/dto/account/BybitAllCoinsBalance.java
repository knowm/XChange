package org.knowm.xchange.bybit.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
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

  @JsonProperty("bizType")
  Integer bizType;

  @JsonProperty("accountId")
  String accountId;

  @JsonProperty("memberId")
  String memberId;

  @JsonProperty("balance")
  List<BybitCoinBalance> balance;

  @Builder
  @Jacksonized
  @Value
  public static class BybitCoinBalance {

    @JsonProperty("coin")
    String coin;

    @JsonProperty("walletBalance")
    BigDecimal walletBalance;

    @JsonProperty("transferBalance")
    BigDecimal transferBalance;

    @JsonProperty("bonus")
    BigDecimal bonus;

    @JsonProperty("transferSafeAmount")
    BigDecimal transferSafeAmount;

    @JsonProperty("ltvTransferSafeAmount")
    BigDecimal ltvTransferSafeAmount;
  }
}
