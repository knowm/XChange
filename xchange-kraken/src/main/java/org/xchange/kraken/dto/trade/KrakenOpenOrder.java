package org.xchange.kraken.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KrakenOpenOrder {

  
  /**
   * @param opentm
   * @param description
   * @param volume
   * @param volumeExecuted
   */
  public KrakenOpenOrder(@JsonProperty ("opentm")Double opentm, @JsonProperty ("descr")KrakenOrderDescription description,@JsonProperty ("vol") BigDecimal volume, @JsonProperty ("vol_exec")BigDecimal volumeExecuted) {
    this.opentm = opentm;
    this.description = description;
    this.volume = volume;
    this.volumeExecuted = volumeExecuted;
  }

  public Double getOpentm() {
  
    return opentm;
  }
  
  public KrakenOrderDescription getDescription() {
  
    return description;
  }
  
  public BigDecimal getVolume() {
  
    return volume;
  }
  
  public BigDecimal getVolumeExecuted() {
  
    return volumeExecuted;
  }
  private Double opentm; 
  private KrakenOrderDescription description;
  private BigDecimal volume;
  private BigDecimal volumeExecuted;
  

}
