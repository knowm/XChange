package org.knowm.xchange.bitrue.dto.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public final class TransferHistory {

  private String from;
  private String to;
  private String asset;
  private BigDecimal qty;
  private String status;
  private long time;
}
