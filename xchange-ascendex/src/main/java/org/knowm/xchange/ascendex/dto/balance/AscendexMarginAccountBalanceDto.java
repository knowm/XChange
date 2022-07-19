package org.knowm.xchange.ascendex.dto.balance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexMarginAccountBalanceDto {

  private String asset;

  private BigDecimal totalBalance;

  private BigDecimal availableBalance;
  private BigDecimal borrowed;
  private BigDecimal interest;

}
