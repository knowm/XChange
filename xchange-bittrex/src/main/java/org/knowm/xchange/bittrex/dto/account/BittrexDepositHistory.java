package org.knowm.xchange.bittrex.dto.account;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class BittrexDepositHistory {

  private String id;

  private BigDecimal quantity;

  private String currencySymbol;

  private Integer confirmations;

  private Date updatedAt;

  private String txId;

  private String cryptoAddress;
}
