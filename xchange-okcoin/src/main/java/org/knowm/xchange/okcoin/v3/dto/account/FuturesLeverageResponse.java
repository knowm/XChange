package org.knowm.xchange.okcoin.v3.dto.account;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.knowm.xchange.okcoin.v3.dto.MarginMode;
import org.knowm.xchange.okcoin.v3.dto.trade.OkexResponse;

@Data
@EqualsAndHashCode(callSuper = false)
public class FuturesLeverageResponse extends OkexResponse {

  /** Account Type: fixed or crossed */
  @JsonProperty("margin_mode")
  private MarginMode marginMode;

  /** ******************************** crossed margin mode ********************************* */

  /** Leverage , 1-100x, eg 10 */
  private BigDecimal leverage;

  /** Token, e.g. BTC */
  private String currency;

  /** ******************************** fixed margin mode ********************************* */
  @JsonAnySetter
  /** map: contract id -> FixedLeverage Contract ID, e.g.“BTC-USD-180213” */
  private Map<String, FixedLeverage> fixedLeverages = new HashMap<>();

  @Data
  public static class FixedLeverage {
    /** Leverage for long positions , 1-100x */
    @JsonProperty("long_leverage")
    private BigDecimal longLeverage;

    /** Leverage for short positions , 1-100x */
    @JsonProperty("short_leverage")
    private BigDecimal shortLeverage;
  }
}
