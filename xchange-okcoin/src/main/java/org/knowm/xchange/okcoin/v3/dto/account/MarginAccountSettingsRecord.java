package org.knowm.xchange.okcoin.v3.dto.account;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class MarginAccountSettingsRecord {
  @JsonAnySetter
  /** map: currency:BTC-> CurrencyInfo */
  private Map<String, CurrencyInfo> currencyInfo = new HashMap<>();

  /** trading pair */
  @JsonProperty("instrument_id")
  private String instrument_id;

  @JsonProperty("product_id")
  private String product_id;

  @Data
  public static class CurrencyInfo {

    /** maximum loan amount */
    @JsonProperty("available")
    private String available;

    /** maximum leverage */
    @JsonProperty("leverage")
    private String leverage;

    @JsonProperty("leverage_ratio")
    private String leverageRatio;

    /** interest rate */
    @JsonProperty("rate")
    private String rate;
  }
}
