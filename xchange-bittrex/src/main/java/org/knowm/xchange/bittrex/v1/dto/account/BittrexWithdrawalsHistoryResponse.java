package org.knowm.xchange.bittrex.v1.dto.account;

import java.util.List;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author npinot
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"success", "message", "result"})
public class BittrexWithdrawalsHistoryResponse {
  @JsonProperty("success")
  private Boolean success;
  @JsonProperty("message")
  private String message;
  @JsonProperty("result")
  private List<BittrexWithdrawalHistory> result;

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
  public List<BittrexWithdrawalHistory> getResult() {

    return result;
  }

  @JsonProperty("result")
  public void setResult(List<BittrexWithdrawalHistory> result) {

    this.result = result;
  }
}
