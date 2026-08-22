package org.knowm.xchange.cryptocom.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoComDepositAddress {

  @JsonProperty("id")
  private String id;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("network_id")
  private String networkId;

  @JsonProperty("address")
  private String address;

  @JsonProperty("create_time")
  private Long createTime;

  @JsonProperty("status")
  private String status;
}
