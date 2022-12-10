package org.knowm.xchange.okex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class OkexBillDetails {
  @JsonProperty("instType")
  String instType;

  @JsonProperty("billId")
  String billId;

  @JsonProperty("type")
  String billType;

  @JsonProperty("subType")
  String billSubType;

  @JsonProperty("ts")
  String timestamp;

  @JsonProperty("balChg")
  String accountBalanceChange;

  @JsonProperty("posBalChg")
  String positionBalanceChange;

  @JsonProperty("bal")
  String accountBalance;

  @JsonProperty("posBal")
  String positionBalance;

  @JsonProperty("sz")
  String quantity;

  @JsonProperty("ccy")
  String currency;

  @JsonProperty("pnl")
  String pnl;

  @JsonProperty("fee")
  String fee;

  @JsonProperty("mgnMode")
  String marginMode;

  @JsonProperty("instId")
  String instId;

  @JsonProperty("ordId")
  String orderId;

  @JsonProperty("execType")
  String execType;

  @JsonProperty("from")
  String fromAccount;

  @JsonProperty("to")
  String toAccount;

  @JsonProperty("notes")
  String notes;
}
