package org.knowm.xchange.okcoin.v3.dto.account;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OkexFundingAccountRecord {
  private BigDecimal balance;
  private BigDecimal available;
  private String currency;
  private BigDecimal hold;
}
