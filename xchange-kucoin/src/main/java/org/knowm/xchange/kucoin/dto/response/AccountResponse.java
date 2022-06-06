package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountResponse {

  private String currency;

  private BigDecimal balance;

  private BigDecimal holds;

  private BigDecimal available;
}
