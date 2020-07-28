package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeError {

  String message;
  Integer code;
  String field;

  @JsonCreator
  public BitcoindeError(
      @JsonProperty("message") String message,
      @JsonProperty("code") Integer code,
      @JsonProperty("field") String field) {
    this.message = message;
    this.code = code;
    this.field = field;
  }

  /** Customized {@code toString()} methode for better usage in {@link BitcoindeException} */
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Error ").append(code).append(": ").append(message);

    if (StringUtils.isNotBlank(this.field)) {
      sb.append(" (Field: ").append(this.field).append(")");
    }
    return sb.toString();
  }
}
