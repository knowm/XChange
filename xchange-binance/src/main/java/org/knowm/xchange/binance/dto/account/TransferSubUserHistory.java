package org.knowm.xchange.binance.dto.account;

import java.math.BigDecimal;
import lombok.Data;

@Data
public final class TransferSubUserHistory {

  private String counterParty;
  private String email;
  private Integer type;
  private String asset;
  private BigDecimal qty;
  private long time;
}
