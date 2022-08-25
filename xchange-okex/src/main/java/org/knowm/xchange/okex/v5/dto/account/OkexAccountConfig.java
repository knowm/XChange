package org.knowm.xchange.okex.v5.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */

/** https://www.okex.com/docs-v5/en/#rest-api-account-get-account-configuration * */
@Getter
@NoArgsConstructor
public class OkexAccountConfig {
  @JsonProperty("uid")
  private String uid;

  @JsonProperty("acctLv")
  private String accountLevel;

  @JsonProperty("posMode")
  private String positionMode;

  @JsonProperty("autoLoan")
  private Boolean autoLoan;

  @JsonProperty("greeksType")
  private String greeksType;

  @JsonProperty("level")
  private String level;

  @JsonProperty("levelTmp")
  private String levelTmp;
}
