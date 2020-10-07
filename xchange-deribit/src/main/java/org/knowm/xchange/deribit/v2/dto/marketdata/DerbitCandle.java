package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DerbitCandle {

  @JsonProperty("volumne")
  private List<BigDecimal> volumne;

  @JsonProperty("ticks")
  private List<BigDecimal> ticks;

  @JsonProperty("open")
  private List<BigDecimal> open;

  @JsonProperty("low")
  private List<BigDecimal> low;

  @JsonProperty("high")
  private List<BigDecimal> high;

  @JsonProperty("cost")
  private List<BigDecimal> cost;

  @JsonProperty("close")
  private List<BigDecimal> close;

  @JsonProperty("status")
  private String status;

  public List<BigDecimal> getVolumne() {
    return volumne;
  }

  public void setVolumne(List<BigDecimal> volumne) {
    this.volumne = volumne;
  }

  public List<BigDecimal> getTicks() {
    return ticks;
  }

  public void setTicks(List<BigDecimal> ticks) {
    this.ticks = ticks;
  }

  public List<BigDecimal> getOpen() {
    return open;
  }

  public void setOpen(List<BigDecimal> open) {
    this.open = open;
  }

  public List<BigDecimal> getLow() {
    return low;
  }

  public void setLow(List<BigDecimal> low) {
    this.low = low;
  }

  public List<BigDecimal> getHigh() {
    return high;
  }

  public void setHigh(List<BigDecimal> high) {
    this.high = high;
  }

  public List<BigDecimal> getCost() {
    return cost;
  }

  public void setCost(List<BigDecimal> cost) {
    this.cost = cost;
  }

  public List<BigDecimal> getClose() {
    return close;
  }

  public void setClose(List<BigDecimal> close) {
    this.close = close;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "DerbitCandle [volumne="
        + volumne
        + ", ticks="
        + ticks
        + ", open="
        + open
        + ", low="
        + low
        + ", high="
        + high
        + ", cost="
        + cost
        + ", close="
        + close
        + ", status="
        + status
        + "]";
  }
}
