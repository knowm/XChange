package org.knowm.xchange.blockchain.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockchainAccountInformation {

  private final Currency currency;

  @JsonProperty("balance")
  private final BigDecimal total;

  private final BigDecimal available;
  private final BigDecimal balance_local;
  private final BigDecimal available_local;
  private final BigDecimal rate;
}
