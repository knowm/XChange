package org.knowm.xchange.bittrex.dto.marketdata;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.currency.Currency;

@Data
@NoArgsConstructor
public class BittrexCurrency {
  private Currency symbol;
  private String name;
  private String coinType;
  private String status;
  private Integer minConfirmations;
  private String notice;
  private Double txFee;
  private String logoUrl;
  private String[] prohibitedIn;
  private String baseAddress;
  private String[] associatedTermsOfService;
  private String[] tags;
}
