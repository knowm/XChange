package org.knowm.xchange.okcoin.v3.dto.account;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.knowm.xchange.okcoin.v3.dto.trade.OkexResponse;

@Data
@EqualsAndHashCode(callSuper = false)
public class MarginAccountResponse extends OkexResponse {
  @JsonAnySetter
  /** map: currency:BTC-> CurrencyInfo */
  private Map<String, CurrencyInfo> currencyInfo = new HashMap<>();

  /** Instrument */
  @JsonProperty("instrument_id")
  private String instrumentId;

  /** Liquidation price */
  @JsonProperty("liquidation_price")
  private String liquidationPrice;

  /** Maintenance margin ratio */
  @JsonProperty("maint_margin_ratio")
  private String maintMarginRatio;

  /** Margin ratio */
  @JsonProperty("margin_ratio")
  private String marginRatio;

  /** product_id */
  @JsonProperty("product_id")
  private String productId;

  /** Risk rate */
  @JsonProperty("risk_rate")
  private String riskRate;

  /** Margin borrowing Position Tiers */
  @JsonProperty("tiers")
  private String tiers;

  @Data
  public static class CurrencyInfo {

    /** Available Amount */
    @JsonProperty("available")
    private String available;

    /** Remaining balance */
    @JsonProperty("balance")
    private String balance;

    /** Borrowed tokens (unpaid) */
    @JsonProperty("borrowed")
    private String borrowed;

    /** Available transfer amount */
    @JsonProperty("can_withdraw")
    private String canWithdraw;

    @JsonProperty("frozen")
    private String frozen;

    /** Amount on hold (not available) */
    @JsonProperty("hold")
    private String hold;

    @JsonProperty("holds")
    private String holds;

    /** Interest (unpaid) */
    @JsonProperty("lending_fee")
    private String lendingFee;
  }
}
