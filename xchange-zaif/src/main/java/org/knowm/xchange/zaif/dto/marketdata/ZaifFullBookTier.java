package org.knowm.xchange.zaif.dto.marketdata;

import java.math.BigDecimal;

import org.knowm.xchange.zaif.dto.ZaifException;

public class ZaifFullBookTier {
  private final BigDecimal price;
  private final BigDecimal volume;

  public ZaifFullBookTier(Object[] tier) {

    if (tier != null && tier.length == 2) {
      this.price = new BigDecimal(tier[0].toString());
      this.volume = new BigDecimal(tier[1].toString());
    } else
      throw new ZaifException("Invalid Tier");
  }

  public BigDecimal getPrice() {
    return this.price;
  }

  public BigDecimal getVolume() {
    return this.volume;
  }

  @Override
  public String toString() {
    return "ZaifFullBookTier [price=" + price + ", volume=" + volume + "]";
  }
}
