package org.knowm.xchange.bittrex.dto.account;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BittrexDepositHistory {

  private Long id;

  private BigDecimal quantity;

  private String currencySymbol;

  private Integer confirmations;

  private Date updatedAt;

  private String txId;

  private String cryptoAddress;

}
