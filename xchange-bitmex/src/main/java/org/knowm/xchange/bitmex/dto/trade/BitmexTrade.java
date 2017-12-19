package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
                       "timestamp",
                       "symbol",
                       "side",
                       "size",
                       "price",
                       "tickDirection",
                       "trdMatchID",
                       "grossValue",
                       "homeNotional",
                       "foreignNotional"
                   })

public final class BitmexTrade {

  @JsonProperty("timestamp")
  public String timestamp;
  @JsonProperty("symbol")
  public String symbol;
  @JsonProperty("side")
  public String side;
  @JsonProperty("size")
  public BigDecimal size;
  @JsonProperty("price")
  public BigDecimal price;
  @JsonProperty("tickDirection")
  public String tickDirection;
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

  @Getter
  public enum TimeFrame {
    DAILY("daily"),
    WEEKLY("weekly"),
    MONTHLY("monthly"),
    QUARTERLY("quarterly"),
    BI_QUARTERLY("biquarterly");

    private String name;

    TimeFrame(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return name;
    }
  }
}