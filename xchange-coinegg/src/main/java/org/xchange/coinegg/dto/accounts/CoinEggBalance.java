package org.xchange.coinegg.dto.accounts;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinEggBalance {
  
  private final int id;
  private final int xasLock;
  private final int ethLock;
  private final int btcLock;
  
  private final BigDecimal xasBalance;
  private final BigDecimal ethBalance;
  private final BigDecimal btcBalance;
  
  public CoinEggBalance(@JsonProperty("uid") int id, 
      @JsonProperty("xas_balance") BigDecimal xasBalance, @JsonProperty("xas_lock") int xasLock,
      @JsonProperty("eth_balance") BigDecimal ethBalance, @JsonProperty("eth_lock") int ethLock,
      @JsonProperty("btc_balance") BigDecimal btcBalance, @JsonProperty("btc_lock") int btcLock) {
    
    this.id = id;
    this.xasLock = xasLock;
    this.ethLock = ethLock;
    this.btcLock = btcLock;
    this.xasBalance = xasBalance;
    this.ethBalance = ethBalance;
    this.btcBalance = btcBalance;
  }

  public int getID() {
    return id;
  }

  public boolean isXASLocked() {
    return xasLock == 1;
  }

  public boolean isETHLocked() {
    return ethLock == 1;
  }

  public boolean isBTCLocked() {
    return btcLock == 1;
  }

  public BigDecimal getXASBalance() {
    return xasBalance;
  }

  public BigDecimal getETHBalance() {
    return ethBalance;
  }

  public BigDecimal getBTCBalance() {
    return btcBalance;
  }
}
