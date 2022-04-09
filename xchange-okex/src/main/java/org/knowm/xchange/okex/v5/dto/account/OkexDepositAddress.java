package org.knowm.xchange.okex.v5.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OkexDepositAddress {
  @JsonProperty("addr")
  String address;

  @JsonProperty("tag")
  String tag;

  @JsonProperty("memo")
  String memo;

  @JsonProperty("pmtId")
  String paymentId;

  @JsonProperty("pmtId")
  String currency;

  @JsonProperty("chain")
  String chain;

  @JsonProperty("to")
  String to;

  @JsonProperty("selected")
  String selected;

  @JsonProperty("ctAddr")
  String contactAddress;
}
