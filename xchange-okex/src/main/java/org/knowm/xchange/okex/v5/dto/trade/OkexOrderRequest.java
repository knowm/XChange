package org.knowm.xchange.okex.v5.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/** Author: Max Gao (gaamox@tutanota.com) Created: 09-06-2021 */
/** https://www.okex.com/docs-v5/en/#rest-api-trade-place-order * */
@Builder
public class OkexOrderRequest {
  @JsonProperty("instId")
  private String instrumentId;

  @JsonProperty("tdMode")
  private String tradeMode;

  @JsonProperty("ccy")
  private String marginCurrency;

  @JsonProperty("clOrderId")
  private String clientOrderId;

  @JsonProperty("tag")
  private String tag;

  @JsonProperty("side")
  private String side;

  @JsonProperty("posSide")
  private String posSide;

  @JsonProperty("ordType")
  private String orderType;

  @JsonProperty("sz")
  private String amount;

  @JsonProperty("px")
  private String price;

  @JsonProperty("reduceOnly")
  private boolean reducePosition;
}
