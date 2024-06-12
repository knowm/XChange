package org.knowm.xchange.blockchain.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockchainWithdrawal {

  private final String withdrawalId;
  private final BigDecimal amount;
  private final BigDecimal fee;
  private final Currency currency;
  private final String beneficiary;
  private final String state;
  private final Date timestamp;
}
