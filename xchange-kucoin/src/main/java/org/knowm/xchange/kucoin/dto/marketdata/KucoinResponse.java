package org.knowm.xchange.kucoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author Jan Akerman */
public class KucoinResponse<T> {

  private boolean success;
  private String code;
  private String message;
  private Long timestamp;
  private T data;

  public KucoinResponse(
      @JsonProperty("success") boolean success,
      @JsonProperty("code") String code,
      @JsonProperty("msg") String message,
      @JsonProperty("timestamp") Long timestamp,
      @JsonProperty("data") T data) {
    this.success = success;
    this.code = code;
    this.message = message;
    this.timestamp = timestamp;
    this.data = data;
  }

  public boolean isSuccess() {
    return success;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public T getData() {
    return data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    KucoinResponse<?> that = (KucoinResponse<?>) o;

    if (success != that.success) return false;
    if (code != null ? !code.equals(that.code) : that.code != null) return false;
    if (message != null ? !message.equals(that.message) : that.message != null) return false;
    if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null)
      return false;
    return data != null ? data.equals(that.data) : that.data == null;
  }

  @Override
  public int hashCode() {
    int result = (success ? 1 : 0);
    result = 31 * result + (code != null ? code.hashCode() : 0);
    result = 31 * result + (message != null ? message.hashCode() : 0);
    result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
    result = 31 * result + (data != null ? data.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "KucoinResponse{"
        + "success="
        + success
        + ", code='"
        + code
        + '\''
        + ", message='"
        + message
        + '\''
        + ", timestamp="
        + timestamp
        + ", data="
        + data
        + '}';
  }
}
