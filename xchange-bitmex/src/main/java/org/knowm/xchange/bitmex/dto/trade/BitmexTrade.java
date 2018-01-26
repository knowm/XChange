package org.knowm.xchange.bitmex.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "timestamp", "symbol", "side", "size", "price", "tickDirection", "trdMatchID", "grossValue", "homeNotional", "foreignNotional" })

public final class BitmexTrade {

  @JsonProperty("timestamp")
  public Date timestamp;
  @JsonProperty("symbol")
  public String symbol;
  @JsonProperty("side")
  public BitmexSide side;
  @JsonProperty("size")
  public BigDecimal size;
  @JsonProperty("price")
  public BigDecimal price;
  @JsonProperty("tickDirection")
  public BitmexTickDirection tickDirection;
  @JsonProperty("trdMatchID")
  public String trdMatchID;
  @JsonProperty("grossValue")
  public BigDecimal grossValue;
  @JsonProperty("homeNotional")
  public BigDecimal homeNotional;
  @JsonProperty("foreignNotional")
  public BigDecimal foreignNotional;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<>();

  public String getSymbol() {

    return symbol;
  }

  public BitmexSide getSide() {

    return side;
  }

  public BigDecimal getSize() {

    return size;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BitmexTickDirection getTickDirection() {

    return tickDirection;
  }

  public String getTrdMatchID() {

    return trdMatchID;
  }

  public BigDecimal getGrossValue() {

    return grossValue;
  }

  public BigDecimal getHomeNotional() {

    return homeNotional;
  }

  public BigDecimal getForeignNotional() {

    return foreignNotional;
  }

  public Map<String, Object> getAdditionalProperties() {

    return additionalProperties;
  }

  @Override
  public String toString() {

    return "BitmexTrade{" + "symbol='" + symbol + '\'' + ", side='" + side + '\'' + ", size=" + size + ", price=" + price + ", tickDirection='" + tickDirection + '\'' + ", trdMatchID='" + trdMatchID
        + '\'' + ", grossValue=" + grossValue + ", homeNotional=" + homeNotional + ", foreignNotional=" + foreignNotional + '}';
  }

}