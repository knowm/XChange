package org.knowm.xchange.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class CurrencyMetaData implements Serializable {

  private static final long serialVersionUID = -247899067657358542L;

  @JsonProperty("scale")
  private final Integer scale;

  @JsonProperty("withdrawal_fee")
  private final BigDecimal withdrawalFee;

  @JsonProperty("min_withdrawal_amount")
  private final BigDecimal minWithdrawalAmount;

  @JsonProperty("wallet_health")
  private WalletHealth walletHealth;

  public CurrencyMetaData(Integer scale, BigDecimal withdrawalFee) {
    this(scale, withdrawalFee, null);
  }

  public CurrencyMetaData(Integer scale, BigDecimal withdrawalFee, BigDecimal minWithdrawalAmount) {
    this(scale, withdrawalFee, minWithdrawalAmount, WalletHealth.UNKNOWN);
  }
}
