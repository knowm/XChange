package org.knowm.xchange.okex.v5.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OkexDepositHistory {

  @JsonProperty("ccy")
  private String currency;

  @JsonProperty("chain")
  private String chain;

  @JsonProperty("amt")
  private String withdrawalAmount;

  @JsonProperty("from")
  private String from;

  @JsonProperty("to")
  private String to;

  @JsonProperty("txId")
  private String txId;

  @JsonProperty("ts")
  private String ts;

  @JsonProperty("state")
  private String state;

  @JsonProperty("depId")
  private String depId;

  @JsonProperty("actualDepBlkConfirm")
  private String actualDepBlkConfirm;
}
