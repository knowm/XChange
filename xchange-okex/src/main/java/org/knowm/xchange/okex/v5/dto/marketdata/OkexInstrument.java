package org.knowm.xchange.okex.v5.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
/** https://www.okex.com/docs-v5/en/#rest-api-public-data-get-instruments * */
@Getter
@NoArgsConstructor
public class OkexInstrument {
  @JsonProperty("instType")
  private String instrumentType;

  @JsonProperty("instId")
  private String instrumentId;

  @JsonProperty("uly")
  private String underlying;

  @JsonProperty("category")
  private String category;

  @JsonProperty("baseCcy")
  private String baseCurrency;

  @JsonProperty("quoteCcy")
  private String quoteCurrency;

  @JsonProperty("settleCcy")
  private String settleCurrency;

  @JsonProperty("ctVal")
  private String contractValue;

  @JsonProperty("ctMult")
  private String contractMultiplier;

  @JsonProperty("optType")
  private String optionType;

  @JsonProperty("stk")
  private String strikePrice;

  @JsonProperty("listTime")
  private String listTime;

  @JsonProperty("expTime")
  private String expiryTime;

  @JsonProperty("lever")
  private String leverage;

  @JsonProperty("tickSz")
  private String tickSize;

  @JsonProperty("lotSz")
  private String lotSize;

  @JsonProperty("minSz")
  private String minSize;

  @JsonProperty("ctType")
  private String contractType;

  @JsonProperty("alias")
  private String alias;

  @JsonProperty("state")
  private String state;
}
