package org.knowm.xchange.binance.dto.account;

import java.math.BigDecimal;
import lombok.Data;

@Data
public final class TransferHistory {

  private String from;
  private String to;
  private String asset;
  private BigDecimal qty;
  private String status;
  private long time;
}
