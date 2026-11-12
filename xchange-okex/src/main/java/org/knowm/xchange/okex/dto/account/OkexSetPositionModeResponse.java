package org.knowm.xchange.okex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/** https://www.okx.com/docs-v5/en/#rest-api-account-set-position-mode */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OkexSetPositionModeResponse {

  @JsonProperty("posMode")
  private String posMode;
}
