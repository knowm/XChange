package org.knowm.xchange.cryptocom.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoComWithdrawalRecord {

  @JsonProperty("id")
  private String id;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("network")
  private String network;

  @JsonProperty("client_wid")
  private String clientWid;

  @JsonProperty("amount")
  private String amount;

  @JsonProperty("fee")
  private String fee;

  @JsonProperty("address")
  private String address;

  @JsonProperty("status")
  private String status;

  @JsonProperty("create_time")
  private Long createTime;

  @JsonProperty("update_time")
  private Long updateTime;

  @JsonProperty("txid")
  private String txid;
}
