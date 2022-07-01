package org.knowm.xchange.okex.v5.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
/** https://www.okex.com/docs-v5/en/#rest-api-funding-get-currencies * */
@Getter
@NoArgsConstructor
public class OkexCurrency {
  @JsonProperty("ccy")
  private String currency;

  @JsonProperty("name")
  private String name;

  @JsonProperty("chain")
  private String chain;

  @JsonProperty("canDep")
  private boolean canDep;

  @JsonProperty("canWd")
  private boolean canWd;

  @JsonProperty("canInternal")
  private boolean canInternal;

  @JsonProperty("minWd")
  private String minWd;

  @JsonProperty("minFee")
  private String minFee;

  @JsonProperty("maxFee")
  private String maxFee;
}
