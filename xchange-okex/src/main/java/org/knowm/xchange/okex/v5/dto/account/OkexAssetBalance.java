package org.knowm.xchange.okex.v5.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Author: Ali Gokalp Peker (aligokalppeker@yahoo.com) Created: 23-10-2021 */

/** https://www.okex.com/docs-v5/en/#rest-api-funding-get-balance * */
@Getter
@NoArgsConstructor
public class OkexAssetBalance {

  @JsonProperty("ccy")
  private String currency;

  @JsonProperty("bal")
  private String balance;

  @JsonProperty("availBal")
  private String availableBalance;

  @JsonProperty("frozenBal")
  private String frozenBalance;
}
