package org.knowm.xchange.bitcointoyou.dto.account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Danilo Guimaraes
 * @author Jonathas Carrijo
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"success", "oReturn", "error", "date", "timestamp"})
public class BitcointoyouBalance {

  @JsonProperty("success")
  private String success;
  @JsonProperty("oReturn")
  private List<Map<String, BigDecimal>> oReturn;
  @JsonProperty("error")
  private String error;
  @JsonProperty("date")
  private String date;
  @JsonProperty("timestamp")
  private String timestamp;

  @JsonProperty("success")
  public String getSuccess() {

    return success;
  }

  @JsonProperty("success")
  public void setAvailable(String success) {

    this.success = success;
  }

  @JsonProperty("oReturn")
  public List<Map<String, BigDecimal>> getoReturn() {

    return oReturn;
  }

  @JsonProperty("oReturn")
  public void setoReturn(List<Map<String, BigDecimal>> oReturn) {

    this.oReturn = oReturn;
  }

  @JsonProperty("error")
  public String getError() {

    return error;
  }

  @JsonProperty("error")
  public void setError(String error) {

    this.error = error;
  }

  @JsonProperty("date")
  public String getDate() {

    return date;
  }

  @JsonProperty("date")
  public void setDate(String date) {

    this.date = date;
  }

  @JsonProperty("timestamp")
  public String getTimestamp() {

    return timestamp;
  }

  @JsonProperty("timestamp")
  public void setTimestamp(String timestamp) {

    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "BitcointoyouBalance[" +
        "success=" + success +
        ", oReturn=" + oReturn +
        ", date=" + date +
        ", timestamp=" + timestamp +
        ']';
  }
}
