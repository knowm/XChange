package org.knowm.xchange.bittrex.dto.account;

import java.util.List;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"success", "message", "result"})
public class BittrexBalancesResponse {

  @JsonProperty("success")
  private Boolean success;
  @JsonProperty("message")
  private String message;
  @JsonProperty("result")
  private List<BittrexBalance> result;

  @JsonProperty("success")
  public Boolean getSuccess() {

    return success;
  }

  @JsonProperty("success")
  public void setSuccess(Boolean success) {

    this.success = success;
  }

  @JsonProperty("message")
  public String getMessage() {

    return message;
  }

  @JsonProperty("message")
  public void setMessage(String message) {

    this.message = message;
  }

  @JsonProperty("result")
  public List<BittrexBalance> getResult() {

    return result;
  }

  @JsonProperty("result")
  public void setResult(List<BittrexBalance> result) {

    this.result = result;
  }

  @Override
  public String toString() {

    return "BittrexBalancesResponse [message=" + message + ", result=" + result + ", success=" + success + "]";
  }

}