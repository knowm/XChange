package org.knowm.xchange.coinsuper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigInteger;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({"code", "data", "msg"})
public class CoinsuperResponse<R> {

  @JsonProperty("code")
  private int code;

  @JsonProperty("data")
  private Data<R> data;

  @JsonProperty("msg")
  private String msg;

  /** No args constructor for use in serialization */
  public CoinsuperResponse() {}

  /**
   * @param data
   * @param code
   * @param msg
   */
  public CoinsuperResponse(int code, Data<R> data, String msg) {
    super();
    this.code = code;
    this.data = data;
    this.msg = msg;
  }

  @JsonProperty("code")
  public int getCode() {
    return code;
  }

  @JsonProperty("code")
  public void setCode(int code) {
    this.code = code;
  }

  @JsonProperty("data")
  public Data<R> getData() {
    return data;
  }

  @JsonProperty("data")
  public void setData(Data<R> data) {
    this.data = data;
  }

  @JsonProperty("msg")
  public String getMsg() {
    return msg;
  }

  @JsonProperty("msg")
  public void setMsg(String msg) {
    this.msg = msg;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("code", code)
        .append("data", data)
        .append("msg", msg)
        .toString();
  }

  /// --------------------
  public class Data<R> {

    @JsonProperty("result")
    private R result = null;

    @JsonProperty("timestamp")
    private BigInteger timestamp;

    /** No args constructor for use in serialization */
    public Data() {}

    /**
     * @param timestamp
     * @param result
     */
    public Data(R result, BigInteger timestamp) {
      super();
      this.result = result;
      this.timestamp = timestamp;
    }

    @JsonProperty("result")
    public R getResult() {
      return result;
    }

    @JsonProperty("result")
    public void setResult(R result) {
      this.result = result;
    }

    @JsonProperty("timestamp")
    public BigInteger getTimestamp() {
      return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(BigInteger timestamp) {
      this.timestamp = timestamp;
    }

    @Override
    public String toString() {
      return new ToStringBuilder(this)
          .append("result", result)
          .append("timestamp", timestamp)
          .toString();
    }
  }
}
