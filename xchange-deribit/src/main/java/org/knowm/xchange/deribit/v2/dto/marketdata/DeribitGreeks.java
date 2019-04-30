package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitGreeks {

  @JsonProperty("vega")
  private BigDecimal vega;

  @JsonProperty("theta")
  private BigDecimal theta;

  @JsonProperty("rho")
  private BigDecimal rho;

  @JsonProperty("gamma")
  private BigDecimal gamma;

  @JsonProperty("delta")
  private BigDecimal delta;


  public BigDecimal getVega() {
    return vega;
  }

  public void setVega(BigDecimal vega) {
    this.vega = vega;
  }

  public BigDecimal getTheta() {
    return theta;
  }

  public void setTheta(BigDecimal theta) {
    this.theta = theta;
  }

  public BigDecimal getRho() {
    return rho;
  }

  public void setRho(BigDecimal rho) {
    this.rho = rho;
  }

  public BigDecimal getGamma() {
    return gamma;
  }

  public void setGamma(BigDecimal gamma) {
    this.gamma = gamma;
  }

  public BigDecimal getDelta() {
    return delta;
  }

  public void setDelta(BigDecimal delta) {
    this.delta = delta;
  }

  @Override
  public String toString() {
    return "DeribitGreeks{" +
            "vega=" + vega +
            ", theta=" + theta +
            ", rho=" + rho +
            ", gamma=" + gamma +
            ", delta=" + delta +
            '}';
  }
}
