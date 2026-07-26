package org.knowm.xchange.cryptocom.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoComBalance {

  @JsonProperty("instrument_name")
  private String instrumentName;

  @JsonProperty("total_available_balance")
  private String totalAvailableBalance;

  @JsonProperty("total_margin_balance")
  private String totalMarginBalance;

  @JsonProperty("total_cash_balance")
  private String totalCashBalance;

  @JsonProperty("position_balances")
  private List<PositionBalance> positionBalances;

  @Data
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class PositionBalance {

    @JsonProperty("instrument_name")
    private String instrumentName;

    @JsonProperty("quantity")
    private String quantity;

    @JsonProperty("market_value")
    private String marketValue;

    @JsonProperty("reserved_qty")
    private String reservedQty;
  }
}
