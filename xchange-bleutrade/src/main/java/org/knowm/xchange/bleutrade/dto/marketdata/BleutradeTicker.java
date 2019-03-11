package org.knowm.xchange.bleutrade.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
  "MarketName",
  "PrevDay",
  "High",
  "Low",
  "Last",
  "Average",
  "Volume",
  "BaseVolume",
  "TimeStamp",
  "Bid",
  "Ask",
  "IsActive"
})
public class BleutradeTicker {

  @JsonProperty("MarketName")
  private String MarketName;

  @JsonProperty("PrevDay")
  private BigDecimal PrevDay;

  @JsonProperty("High")
  private BigDecimal High;

  @JsonProperty("Low")
  private BigDecimal Low;

  @JsonProperty("Last")
  private BigDecimal Last;

  @JsonProperty("Average")
  private BigDecimal Average;

  @JsonProperty("Volume")
  private BigDecimal Volume;

  @JsonProperty("BaseVolume")
  private BigDecimal BaseVolume;

  @JsonProperty("TimeStamp")
  private String TimeStamp;

  @JsonProperty("Bid")
  private BigDecimal Bid;

  @JsonProperty("Ask")
  private BigDecimal Ask;

  @JsonProperty("IsActive")
  private Boolean IsActive;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** @return The MarketName */
  @JsonProperty("MarketName")
  public String getMarketName() {

    return MarketName;
  }

  /** @param MarketName The MarketName */
  @JsonProperty("MarketName")
  public void setMarketName(String MarketName) {

    this.MarketName = MarketName;
  }

  /** @return The PrevDay */
  @JsonProperty("PrevDay")
  public BigDecimal getPrevDay() {

    return PrevDay;
  }

  /** @param PrevDay The PrevDay */
  @JsonProperty("PrevDay")
  public void setPrevDay(BigDecimal PrevDay) {

    this.PrevDay = PrevDay;
  }

  /** @return The High */
  @JsonProperty("High")
  public BigDecimal getHigh() {

    return High;
  }

  /** @param High The High */
  @JsonProperty("High")
  public void setHigh(BigDecimal High) {

    this.High = High;
  }

  /** @return The Low */
  @JsonProperty("Low")
  public BigDecimal getLow() {

    return Low;
  }

  /** @param Low The Low */
  @JsonProperty("Low")
  public void setLow(BigDecimal Low) {

    this.Low = Low;
  }

  /** @return The Last */
  @JsonProperty("Last")
  public BigDecimal getLast() {

    return Last;
  }

  /** @param Last The Last */
  @JsonProperty("Last")
  public void setLast(BigDecimal Last) {

    this.Last = Last;
  }

  /** @return The Average */
  @JsonProperty("Average")
  public BigDecimal getAverage() {

    return Average;
  }

  /** @param Average The Average */
  @JsonProperty("Average")
  public void setAverage(BigDecimal Average) {

    this.Average = Average;
  }

  /** @return The Volume */
  @JsonProperty("Volume")
  public BigDecimal getVolume() {

    return Volume;
  }

  /** @param Volume The Volume */
  @JsonProperty("Volume")
  public void setVolume(BigDecimal Volume) {

    this.Volume = Volume;
  }

  /** @return The BaseVolume */
  @JsonProperty("BaseVolume")
  public BigDecimal getBaseVolume() {

    return BaseVolume;
  }

  /** @param BaseVolume The BaseVolume */
  @JsonProperty("BaseVolume")
  public void setBaseVolume(BigDecimal BaseVolume) {

    this.BaseVolume = BaseVolume;
  }

  /** @return The TimeStamp */
  @JsonProperty("TimeStamp")
  public String getTimeStamp() {

    return TimeStamp;
  }

  /** @param TimeStamp The TimeStamp */
  @JsonProperty("TimeStamp")
  public void setTimeStamp(String TimeStamp) {

    this.TimeStamp = TimeStamp;
  }

  /** @return The Bid */
  @JsonProperty("Bid")
  public BigDecimal getBid() {

    return Bid;
  }

  /** @param Bid The Bid */
  @JsonProperty("Bid")
  public void setBid(BigDecimal Bid) {

    this.Bid = Bid;
  }

  /** @return The Ask */
  @JsonProperty("Ask")
  public BigDecimal getAsk() {

    return Ask;
  }

  /** @param Ask The Ask */
  @JsonProperty("Ask")
  public void setAsk(BigDecimal Ask) {

    this.Ask = Ask;
  }

  /** @return The IsActive */
  @JsonProperty("IsActive")
  public Boolean getIsActive() {

    return IsActive;
  }

  /** @param IsActive The IsActive */
  @JsonProperty("IsActive")
  public void setIsActive(Boolean IsActive) {

    this.IsActive = IsActive;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {

    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {

    this.additionalProperties.put(name, value);
  }

  @Override
  public String toString() {

    return "BleutradeTicker [MarketName="
        + MarketName
        + ", PrevDay="
        + PrevDay
        + ", High="
        + High
        + ", Low="
        + Low
        + ", Last="
        + Last
        + ", Average="
        + Average
        + ", Volume="
        + Volume
        + ", BaseVolume="
        + BaseVolume
        + ", TimeStamp="
        + TimeStamp
        + ", Bid="
        + Bid
        + ", Ask="
        + Ask
        + ", IsActive="
        + IsActive
        + ", additionalProperties="
        + additionalProperties
        + "]";
  }
}
