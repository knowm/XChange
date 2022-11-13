package org.knowm.xchange.binance.dto.meta.exchangeinfo;

import lombok.Getter;

public class FutureSymbol {

  @Getter private String symbol;
  @Getter private String pair;
  @Getter private String contractType;
  @Getter private String status;
  @Getter private String baseAsset;
  @Getter private String quoteAsset;
  @Getter private String marginAsset;
  @Getter private String pricePrecision;
  @Getter private String quantityPrecision;
  @Getter private String baseAssetPrecision;
  @Getter private String quotePrecision;
  @Getter private String underlyingType;
  @Getter private String[] underlyingSubType;
  @Getter private String settlePlan;
  @Getter private String triggerProtect;
  @Getter private String[] orderTypes;
  @Getter private String[] timeInForce;
  @Getter private String liquidationFee;
  @Getter private String marketTakeBound;
  @Getter private FutureFilter[] filters;

  @Override
  public String toString() {
    return "ClassPojo [quoteAsset = "
        + quoteAsset
        + ", baseAsset = "
        + baseAsset
        + ", symbol = "
        + symbol
        + ", contractType = "
        + contractType
        + ", status = "
        + status
        + ", quotePrecision = "
        + quotePrecision
        + ", baseAssetPrecision = "
        + baseAssetPrecision
        + "]";
  }
}
