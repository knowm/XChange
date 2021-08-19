package org.knowm.xchange.bittrex.dto.account;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class BittrexWithdrawalHistory {

  private String id;

  private String currencySymbol;

  private BigDecimal quantity;

  private String cryptoAddress;

  private BigDecimal txCost;

  private String txId;

  private String status;

  private Date createdAt;

  private Date completedAt;
}
