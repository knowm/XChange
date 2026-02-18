package org.knowm.xchange.okex.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OkexResponse<V> {
  private final String id;
  private final String code;
  private final String msg;
  private final V data;

  public OkexResponse(
      @JsonProperty("id") String id,
      @JsonProperty("code") String code,
      @JsonProperty("msg") String msg,
      @JsonProperty("data") V data) {
    this.id = id;
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public boolean isSuccess() {
    return "0".equals(code);
  }

  @Override
  public String toString() {
    return "OkexResponse{" + "code=" + code + ", msg=" + msg + '}';
  }
}
