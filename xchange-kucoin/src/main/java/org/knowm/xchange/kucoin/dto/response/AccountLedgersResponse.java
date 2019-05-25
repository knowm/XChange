package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountLedgersResponse {
  /** The currency of the account */
  private String currency;
  /**
   * The total amount of assets (fees included) involved in assets changes such as transaction,
   * withdrawal and bonus distribution.
   */
  private BigDecimal amount;
  /** Fees generated in transaction, withdrawal, etc. */
  private BigDecimal fee;
  /** Remaining funds after the transaction. */
  private BigDecimal balance;
  /**
   * Business type leading to the changes in funds, such as exchange, withdrawal, deposit,
   * KUCOIN_BONUS, REFERRAL_BONUS etc.
   */
  private String bizType;
  /** out or in */
  private String direction;
  /** Time of the event */
  private Long createdAt;
  /** Business related information such as order ID, serial No., etc. */
  private Object context;
}
