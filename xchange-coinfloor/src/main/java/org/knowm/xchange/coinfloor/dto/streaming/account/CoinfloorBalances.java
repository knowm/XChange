package org.knowm.xchange.coinfloor.dto.streaming.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author obsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinfloorBalances {

  private final int tag;
  private final int errorCode;
  private final List<CoinfloorAssetBalance> balances;

  /**
   * Constructor
   * 
   * @param balance
   * @param op
   * @param amount
   */
  public CoinfloorBalances(@JsonProperty("tag") int tag, @JsonProperty("error_code") int errorCode,
      @JsonProperty("balances") List<CoinfloorAssetBalance> balances) {

    this.tag = tag;
    this.errorCode = errorCode;
    this.balances = balances;
  }

  public int getTag() {

    return tag;
  }

  public int getErrorCode() {

    return errorCode;
  }

  public List<CoinfloorAssetBalance> getBalances() {

    return balances;
  }

  @Override
  public String toString() {

    return "CoinfloorBalancesReturn{tag='" + tag + "',errorCode='" + errorCode + "',balances='" + balances + "}";
  }
}
