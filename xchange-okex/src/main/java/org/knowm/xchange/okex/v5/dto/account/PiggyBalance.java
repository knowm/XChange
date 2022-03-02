package org.knowm.xchange.okex.v5.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PiggyBalance {
  @JsonProperty private String earnings;
  @JsonProperty private String ccy;
  @JsonProperty private String amt;
}
