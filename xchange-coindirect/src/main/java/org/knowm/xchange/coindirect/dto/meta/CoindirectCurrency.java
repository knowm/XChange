package org.knowm.xchange.coindirect.dto.meta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoindirectCurrency {
  public final long id;
  public final String code;
  public final boolean fiat;
  public final String icon;
  public final String name;
  public final List<Object> withdrawalParameters;
  public final Object options;
  public final BigDecimal withdrawalFee;
  public final BigDecimal depositFee;

  public CoindirectCurrency(
      @JsonProperty("id") long id,
      @JsonProperty("code") String code,
      @JsonProperty("fiat") boolean fiat,
      @JsonProperty("icon") String icon,
      @JsonProperty("name") String name,
      @JsonProperty("withdrawalParameters") List<Object> withdrawalParameters,
      @JsonProperty("options") Object options,
      @JsonProperty("withdrawalFee") BigDecimal withdrawalFee,
      @JsonProperty("depositFee") BigDecimal depositFee) {
    this.id = id;
    this.code = code;
    this.fiat = fiat;
    this.icon = icon;
    this.name = name;
    this.withdrawalParameters = withdrawalParameters;
    this.options = options;
    this.withdrawalFee = withdrawalFee;
    this.depositFee = depositFee;
  }

  @Override
  public String toString() {
    return "CoindirectCurrency{"
        + "id="
        + id
        + ", code='"
        + code
        + '\''
        + ", fiat="
        + fiat
        + ", icon='"
        + icon
        + '\''
        + ", name='"
        + name
        + '\''
        + ", withdrawalParameters="
        + withdrawalParameters
        + ", options="
        + options
        + ", withdrawalFee="
        + withdrawalFee
        + ", depositFee="
        + depositFee
        + '}';
  }
}
