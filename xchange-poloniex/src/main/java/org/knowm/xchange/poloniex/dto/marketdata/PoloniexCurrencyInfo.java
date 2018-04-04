package org.knowm.xchange.poloniex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class PoloniexCurrencyInfo {

  private final int id;
  private final String name;
  private final BigDecimal txFee;
  private final int minConf;
  private final String depositAddress;
  private final boolean disabled;
  private final boolean frozen;
  private final boolean delisted;

  public PoloniexCurrencyInfo(
      @JsonProperty("id") int id,
      @JsonProperty("name") String name,
      @JsonProperty("txFee") BigDecimal txFee,
      @JsonProperty("minConf") int minConf,
      @JsonProperty("depositAddress") String depositAddress,
      @JsonProperty("disabled") boolean disabled,
      @JsonProperty("frozen") boolean frozen,
      @JsonProperty("delisted") boolean delisted) {

    this.id = id;
    this.name = name;
    this.txFee = txFee;
    this.minConf = minConf;
    this.depositAddress = depositAddress;
    this.disabled = disabled;
    this.frozen = frozen;
    this.delisted = delisted;
  }

  public String getDepositAddress() {

    return depositAddress;
  }

  public String getName() {

    return name;
  }

  public int getId() {

    return id;
  }

  public BigDecimal getTxFee() {

    return txFee;
  }

  public int getMinConf() {

    return minConf;
  }

  public boolean isDisabled() {

    return disabled;
  }

  public boolean isFrozen() {

    return frozen;
  }

  public boolean isDelisted() {

    return delisted;
  }

  @Override
  public String toString() {

    return "PoloniexCurrencyInfo [id="
        + id
        + ", name="
        + name
        + ", txFee="
        + txFee
        + ", minConf="
        + minConf
        + ", depositAddress="
        + depositAddress
        + ", disabled="
        + disabled
        + ", frozen="
        + frozen
        + ", delisted="
        + delisted
        + "]";
  }
}
