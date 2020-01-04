package org.knowm.xchange.okcoin.v3.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class OkexWithdrawalRequest {

  /** token */
  private String currency;
  /** withdrawal amount */
  private BigDecimal amount;
  /** withdrawal address(2:OKCoin International 3:OKEx 4:others) */
  private String destination;
  /**
   * verified digital asset address, email or mobile String, some digital asset address format is
   * address+tag , eg: "ARDOR-7JF3-8F2E-QUWZ-CAN7F：123456"
   */
  @JsonProperty("to_address")
  private String toAddress;
  /** fund password */
  @JsonProperty("trade_pwd")
  private String tradePwd;
  /**
   * Network transaction fee≥0. Withdrawals to OKCoin or OKEx are fee-free, please set as 0.
   * Withdrawal to external digital asset address requires network transaction fee.
   */
  private BigDecimal fee;
}
