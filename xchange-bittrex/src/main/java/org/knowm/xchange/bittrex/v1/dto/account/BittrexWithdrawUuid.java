package org.knowm.xchange.bittrex.v1.dto.account;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"uuid"})
public class BittrexWithdrawUuid {

  @JsonProperty("uuid")
  private String uuid;

  @JsonProperty("uuid")
  public String getUuid() {

    return uuid;
  }

  @JsonProperty("uuid")
  public void setUuid(String uuid) {

    this.uuid = uuid;
  }
}
