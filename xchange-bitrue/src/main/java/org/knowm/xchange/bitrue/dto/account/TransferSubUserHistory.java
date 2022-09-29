package org.knowm.xchange.bitrue.dto.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public final class TransferSubUserHistory {

  private String counterParty;
  private String email;
  private Integer type;
  private String asset;
  private BigDecimal qty;
  private long time;
}
