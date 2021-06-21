package org.knowm.xchange.binance.dto.account;

import java.math.BigDecimal;
import lombok.Data;

@Data
public final class BinanceDeposit {

  private long insertTime;
  private BigDecimal amount;
  private String asset;
  private String txId;
  private String address;
  private String addressTag;
  /** (0:pending,1:success) */
  private int status;
}
