package org.knowm.xchange.gemini.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import org.knowm.xchange.utils.DateUtils;
import si.mazi.rescu.SynchronizedValueFactory;

public class GeminiTransfersRequest {

  @JsonProperty("request")
  public final String request = "/v1/transfers";

  @JsonProperty("nonce")
  public final String nonce;

  @JsonProperty("limit_transfers")
  public final Integer limit;

  @JsonProperty("timestamp")
  public final Long timestamp;

  public GeminiTransfersRequest(String nonce, Integer limit, Long timestamp) {
    this.nonce = nonce;
    this.limit = limit;
    this.timestamp = timestamp;
  }

  public static GeminiTransfersRequest create(
      Date from, Integer limit, SynchronizedValueFactory<Long> nonceFactory) {
    Long timestamp = DateUtils.toMillisNullSafe(from);
    return new GeminiTransfersRequest(String.valueOf(nonceFactory.createValue()), limit, timestamp);
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public String getRequest() {
    return request;
  }

  public String getNonce() {
    return nonce;
  }

  public Integer getLimit() {
    return limit;
  }
}
