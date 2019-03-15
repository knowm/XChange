/** Copyright 2019 Mek Global Limited. */
package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse {

  private String id;

  private String symbol;

  private String opType;

  private String type;

  public String getType() {
    return this.type == null ? null : this.type.toLowerCase();
  }

  private String side;

  public String getSide() {
    return this.side == null ? null : this.side.toLowerCase();
  }

  private BigDecimal price;

  private BigDecimal size;

  private BigDecimal funds;

  private BigDecimal dealFunds;

  private BigDecimal dealSize;

  private BigDecimal fee;

  private String feeCurrency;

  private String stp;

  private String stop;

  public String getStop() {
    return this.stop == null ? null : this.stop.toLowerCase();
  }

  private Boolean stopTriggered;

  private BigDecimal stopPrice;

  private String timeInForce;

  private boolean postOnly;

  private boolean hidden;

  private boolean iceberg;

  private BigDecimal visibleSize;

  private Long cancelAfter;

  private String channel;

  private String clientOid;

  private String remark;

  private String tags;

  @JsonProperty("isActive")
  private boolean isActive;

  private boolean cancelExist;

  private Date createdAt;
}
