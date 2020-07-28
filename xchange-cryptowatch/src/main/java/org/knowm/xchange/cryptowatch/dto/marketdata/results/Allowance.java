package org.knowm.xchange.cryptowatch.dto.marketdata.results;

/** @author massi.gerardi */
public class Allowance {

  private long cost;
  private long remaining;
  private long remainingPaid;
  private String upgrade;

  public long getCost() {
    return cost;
  }

  public long getRemaining() {
    return remaining;
  }

  public long getRemainingPaid() {
    return remainingPaid;
  }

  public String getUpgrade() {
    return upgrade;
  }
}
