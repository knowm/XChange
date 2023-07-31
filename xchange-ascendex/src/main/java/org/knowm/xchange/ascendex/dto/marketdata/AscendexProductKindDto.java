package org.knowm.xchange.ascendex.dto.marketdata;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.knowm.xchange.ascendex.dto.enums.AscendexAssetStatus;

import java.math.BigDecimal;
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexProductKindDto extends AscendexProductBaseDto {


  private  String	displayName;
  private  String	domain;
  private  Long	tradingStartTime;
  private  String	collapseDecimals;
  private  BigDecimal	minQty;
  private  BigDecimal	maxQty;
  private AscendexAssetStatus statusCode;
  private  String	statusMessage;
  private  boolean	useTick;
  private  boolean	useLot;
  private  BigDecimal	qtyScale;
  private  BigDecimal	priceScale;
  private  BigDecimal	notionalScale;

}
