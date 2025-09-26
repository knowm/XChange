package org.knowm.xchange.coinsph.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CoinsphSymbol {

  private final String symbol;
  private final String status;
  private final String baseAsset;
  private final int baseAssetPrecision;
  private final String quoteAsset;
  private final int quoteAssetPrecision; // Coins.ph doc uses "quotePrecision"
  private final List<String> orderTypes; // e.g. ["LIMIT", "MARKET", ...]
  private final boolean icebergAllowed;
  private final boolean ocoAllowed;
  private final boolean quoteOrderQtyMarketAllowed;
  private final boolean allowTrailingStop;
  private final boolean cancelReplaceAllowed; // Not in Coins.ph docs, from Binance
  private final boolean isSpotTradingAllowed;
  private final List<Object> filters;
  private final List<String> permissions; // e.g. ["SPOT"]

  public CoinsphSymbol(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("status") String status,
      @JsonProperty("baseAsset") String baseAsset,
      @JsonProperty("baseAssetPrecision") int baseAssetPrecision,
      @JsonProperty("quoteAsset") String quoteAsset,
      @JsonProperty("quotePrecision") int quoteAssetPrecision, // Matches Coins.ph doc
      @JsonProperty("orderTypes") List<String> orderTypes,
      @JsonProperty("icebergAllowed") boolean icebergAllowed,
      @JsonProperty("ocoAllowed") boolean ocoAllowed,
      @JsonProperty("quoteOrderQtyMarketAllowed") boolean quoteOrderQtyMarketAllowed,
      @JsonProperty("allowTrailingStop") boolean allowTrailingStop,
      @JsonProperty("cancelReplaceAllowed") boolean cancelReplaceAllowed,
      @JsonProperty("isSpotTradingAllowed") boolean isSpotTradingAllowed,
      // @JsonProperty("isMarginTradingAllowed") boolean isMarginTradingAllowed,
      @JsonProperty("filters") List<Object> filters,
      @JsonProperty("permissions") List<String> permissions) {
    this.symbol = symbol;
    this.status = status;
    this.baseAsset = baseAsset;
    this.baseAssetPrecision = baseAssetPrecision;
    this.quoteAsset = quoteAsset;
    this.quoteAssetPrecision = quoteAssetPrecision;
    this.orderTypes = orderTypes;
    this.icebergAllowed = icebergAllowed;
    this.ocoAllowed = ocoAllowed;
    this.quoteOrderQtyMarketAllowed = quoteOrderQtyMarketAllowed;
    this.allowTrailingStop = allowTrailingStop;
    this.cancelReplaceAllowed = cancelReplaceAllowed;
    this.isSpotTradingAllowed = isSpotTradingAllowed;
    // this.isMarginTradingAllowed = isMarginTradingAllowed;
    this.filters = filters;
    this.permissions = permissions;
  }
}
