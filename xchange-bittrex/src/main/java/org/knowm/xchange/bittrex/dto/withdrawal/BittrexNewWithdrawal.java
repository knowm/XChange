package org.knowm.xchange.bittrex.dto.withdrawal;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BittrexNewWithdrawal {
  private String currencySymbol;
  private BigDecimal quantity;
  private String cryptoAddress;
  private String cryptoAddressTag;
  private String fundsTransferMethodId;
  private String clientWithdrawalId;
}
