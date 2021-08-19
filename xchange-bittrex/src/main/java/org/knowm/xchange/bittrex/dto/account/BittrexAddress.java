package org.knowm.xchange.bittrex.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.currency.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BittrexAddress {
  private Currency currencySymbol;
  private String status;
  private String cryptoAddress;
  private String cryptoAddressTag;
}
