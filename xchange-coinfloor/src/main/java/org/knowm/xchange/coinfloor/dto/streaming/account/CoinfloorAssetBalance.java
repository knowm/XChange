package org.knowm.xchange.coinfloor.dto.streaming.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinfloor.CoinfloorUtils;
import org.knowm.xchange.coinfloor.CoinfloorUtils.CoinfloorCurrency;

/**
 * @author obsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinfloorAssetBalance {

  private final CoinfloorCurrency asset;
  private final BigDecimal balance;

  public CoinfloorAssetBalance(@JsonProperty("asset") int asset, @JsonProperty("balance") int balance) {

    this.asset = CoinfloorUtils.getCurrency(asset);
    this.balance = CoinfloorUtils.scaleToBigDecimal(this.asset, balance);
  }

  public CoinfloorCurrency getAsset() {

    return asset;
  }

  public BigDecimal getBalance() {

    return balance;
  }

  @Override
  public String toString() {

    return "CoinfloorAssetBalance{asset='" + asset + "', balance='" + balance + "'}";
  }
}
