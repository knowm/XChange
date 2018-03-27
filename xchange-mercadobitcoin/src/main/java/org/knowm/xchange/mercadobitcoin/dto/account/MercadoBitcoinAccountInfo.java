package org.knowm.xchange.mercadobitcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author Felipe Micaroni Lalli */
public class MercadoBitcoinAccountInfo {

  private final Funds funds;
  private final Long serverTime;
  private final Long openOrders;

  public MercadoBitcoinAccountInfo(
      @JsonProperty("funds") MercadoBitcoinAccountInfo.Funds funds,
      @JsonProperty("server_time") Long serverTime,
      @JsonProperty("open_orders") Long openOrders) {

    this.funds = funds;
    this.serverTime = serverTime;
    this.openOrders = openOrders;
  }

  @Override
  public String toString() {

    return "MercadoBitcoinAccountInfo ["
        + "funds="
        + funds
        + ", serverTime="
        + serverTime
        + ", openOrders="
        + openOrders
        + ']';
  }

  public Long getOpenOrders() {

    return openOrders;
  }

  public Long getServerTime() {

    return serverTime;
  }

  public Funds getFunds() {

    return funds;
  }

  public static class Funds {

    private final BigDecimal ltc;
    private final BigDecimal brl;
    private final BigDecimal btc;

    public Funds(
        @JsonProperty("ltc") BigDecimal ltc,
        @JsonProperty("brl") BigDecimal brl,
        @JsonProperty("btc") BigDecimal btc) {

      this.ltc = ltc;
      this.brl = brl;
      this.btc = btc;
    }

    public BigDecimal getLtc() {

      return ltc;
    }

    public BigDecimal getBrl() {

      return brl;
    }

    public BigDecimal getBtc() {

      return btc;
    }

    @Override
    public String toString() {

      return "Funds [" + "ltc=" + ltc + ", brl=" + brl + ", btc=" + btc + ']';
    }
  }
}
