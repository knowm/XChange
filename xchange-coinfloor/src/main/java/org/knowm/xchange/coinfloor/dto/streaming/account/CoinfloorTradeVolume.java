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
public class CoinfloorTradeVolume {

  private final int tag;
  private final int errorCode;
  private final BigDecimal assetVol;

  /**
   * Constructor
   * 
   * @param balance
   * @param op
   * @param amount
   */
  public CoinfloorTradeVolume(@JsonProperty("tag") int tag, @JsonProperty("error_code") int errorCode, @JsonProperty("volume") int assetVol) {

    this.tag = tag;
    this.errorCode = errorCode;

    CoinfloorCurrency currency = (tag == 102) ? CoinfloorCurrency.BTC : CoinfloorCurrency.GBP;
    this.assetVol = CoinfloorUtils.scaleToBigDecimal(currency, assetVol);
  }

  public int getTag() {

    return tag;
  }

  public int getErrorCode() {

    return errorCode;
  }

  public BigDecimal getAssetVol() {

    return assetVol;
  }

  @Override
  public String toString() {

    return "CoinfloorTradeVolumeReturn{tag='" + tag + "', errorCode='" + errorCode + "', assetVol='" + assetVol + "}";
  }
}
