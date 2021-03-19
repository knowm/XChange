package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public final class BinanceMarginAccountInformation {

  public final BigDecimal marginLevel;
  public final BigDecimal totalAssetOfBtc;
  public final BigDecimal totalLiabilityOfBtc;
  public final BigDecimal totalNetAssetOfBtc;
  public final boolean borrowEnabled;
  public final boolean tradeEnabled;
  public final boolean transferEnabled;
  public List<BinanceMarginBalance> userAssets;

  public BinanceMarginAccountInformation(
          @JsonProperty("marginLevel") BigDecimal marginLevel,
          @JsonProperty("totalAssetOfBtc") BigDecimal totalAssetOfBtc,
          @JsonProperty("totalLiabilityOfBtc") BigDecimal totalLiabilityOfBtc,
          @JsonProperty("totalNetAssetOfBtc") BigDecimal totalNetAssetOfBtc,
          @JsonProperty("borrowEnabled") boolean borrowEnabled,
          @JsonProperty("tradeEnabled") boolean tradeEnabled,
          @JsonProperty("transferEnabled") boolean transferEnabled,
          @JsonProperty("userAssets") List<BinanceMarginBalance> assets) {
    this.marginLevel = marginLevel;
    this.totalAssetOfBtc = totalAssetOfBtc;
    this.totalLiabilityOfBtc = totalLiabilityOfBtc;
    this.totalNetAssetOfBtc = totalNetAssetOfBtc;
    this.borrowEnabled = borrowEnabled;
    this.tradeEnabled = tradeEnabled;
    this.transferEnabled = transferEnabled;
    this.userAssets = assets;
  }
}
