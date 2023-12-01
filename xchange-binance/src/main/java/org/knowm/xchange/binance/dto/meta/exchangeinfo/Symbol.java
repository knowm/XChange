package org.knowm.xchange.binance.dto.meta.exchangeinfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Symbol {

  private String quoteAsset;

  private String icebergAllowed;

  private String ocoAllowed;

  private String isMarginTradingAllowed;

  private String isSpotTradingAllowed;

  private String baseAsset;

  private String symbol;

  private String status;

  private String quotePrecision;

  private String quoteAssetPrecision;

  private String baseAssetPrecision;

  private String[] orderTypes;

  private Filter[] filters;

  private String[] permissions;
}
