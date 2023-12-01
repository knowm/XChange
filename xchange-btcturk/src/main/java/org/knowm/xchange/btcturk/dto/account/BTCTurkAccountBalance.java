package org.knowm.xchange.btcturk.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * @author mertguner
 */
public class BTCTurkAccountBalance {

  private final BigDecimal try_balance;
  private final BigDecimal btc_balance;
  private final BigDecimal eth_balance;
  private final BigDecimal xrp_balance;
  private final BigDecimal ltc_balance;
  private final BigDecimal usdt_balance;
  private final BigDecimal xlm_balance;
  private final BigDecimal try_reserved;
  private final BigDecimal btc_reserved;
  private final BigDecimal eth_reserved;
  private final BigDecimal xrp_reserved;
  private final BigDecimal ltc_reserved;
  private final BigDecimal usdt_reserved;
  private final BigDecimal xlm_reserved;
  private final BigDecimal try_available;
  private final BigDecimal btc_available;
  private final BigDecimal eth_available;
  private final BigDecimal xrp_available;
  private final BigDecimal ltc_available;
  private final BigDecimal usdt_available;
  private final BigDecimal xlm_available;
  private final BigDecimal btctry_taker_fee_percentage;
  private final BigDecimal btctry_maker_fee_percentage;
  private final BigDecimal ethtry_taker_fee_percentage;
  private final BigDecimal ethtry_maker_fee_percentage;
  private final BigDecimal ethbtc_taker_fee_percentage;
  private final BigDecimal ethbtc_maker_fee_percentage;
  private final BigDecimal xrptry_taker_fee_percentage;
  private final BigDecimal xrptry_maker_fee_percentage;
  private final BigDecimal ltctry_taker_fee_percentage;
  private final BigDecimal ltctry_maker_fee_percentage;
  private final BigDecimal usdttry_taker_fee_percentage;
  private final BigDecimal usdttry_maker_fee_percentage;
  private final BigDecimal xlmtry_taker_fee_percentage;
  private final BigDecimal xlmtry_maker_fee_percentage;

  public BTCTurkAccountBalance(
      @JsonProperty("try_balance") BigDecimal try_balance,
      @JsonProperty("btc_balance") BigDecimal btc_balance,
      @JsonProperty("eth_balance") BigDecimal eth_balance,
      @JsonProperty("xrp_balance") BigDecimal xrp_balance,
      @JsonProperty("ltc_balance") BigDecimal ltc_balance,
      @JsonProperty("usdt_balance") BigDecimal usdt_balance,
      @JsonProperty("xlm_balance") BigDecimal xlm_balance,
      @JsonProperty("try_reserved") BigDecimal try_reserved,
      @JsonProperty("btc_reserved") BigDecimal btc_reserved,
      @JsonProperty("eth_reserved") BigDecimal eth_reserved,
      @JsonProperty("xrp_reserved") BigDecimal xrp_reserved,
      @JsonProperty("ltc_reserved") BigDecimal ltc_reserved,
      @JsonProperty("usdt_reserved") BigDecimal usdt_reserved,
      @JsonProperty("xlm_reserved") BigDecimal xlm_reserved,
      @JsonProperty("try_available") BigDecimal try_available,
      @JsonProperty("btc_available") BigDecimal btc_available,
      @JsonProperty("eth_available") BigDecimal eth_available,
      @JsonProperty("xrp_available") BigDecimal xrp_available,
      @JsonProperty("ltc_available") BigDecimal ltc_available,
      @JsonProperty("usdt_available") BigDecimal usdt_available,
      @JsonProperty("xlm_available") BigDecimal xlm_available,
      @JsonProperty("btctry_taker_fee_percentage") BigDecimal btctry_taker_fee_percentage,
      @JsonProperty("btctry_maker_fee_percentage") BigDecimal btctry_maker_fee_percentage,
      @JsonProperty("ethtry_taker_fee_percentage") BigDecimal ethtry_taker_fee_percentage,
      @JsonProperty("ethtry_maker_fee_percentage") BigDecimal ethtry_maker_fee_percentage,
      @JsonProperty("ethbtc_taker_fee_percentage") BigDecimal ethbtc_taker_fee_percentage,
      @JsonProperty("ethbtc_maker_fee_percentage") BigDecimal ethbtc_maker_fee_percentage,
      @JsonProperty("xrptry_taker_fee_percentage") BigDecimal xrptry_taker_fee_percentage,
      @JsonProperty("xrptry_maker_fee_percentage") BigDecimal xrptry_maker_fee_percentage,
      @JsonProperty("ltctry_taker_fee_percentage") BigDecimal ltctry_taker_fee_percentage,
      @JsonProperty("ltctry_maker_fee_percentage") BigDecimal ltctry_maker_fee_percentage,
      @JsonProperty("usdttry_taker_fee_percentage") BigDecimal usdttry_taker_fee_percentage,
      @JsonProperty("usdttry_maker_fee_percentage") BigDecimal usdttry_maker_fee_percentage,
      @JsonProperty("xlmtry_taker_fee_percentage") BigDecimal xlmtry_taker_fee_percentage,
      @JsonProperty("xlmtry_maker_fee_percentage") BigDecimal xlmtry_maker_fee_percentage) {
    this.try_balance = try_balance;
    this.btc_balance = btc_balance;
    this.eth_balance = eth_balance;
    this.xrp_balance = xrp_balance;
    this.ltc_balance = ltc_balance;
    this.usdt_balance = usdt_balance;
    this.xlm_balance = xlm_balance;
    this.try_reserved = try_reserved;
    this.btc_reserved = btc_reserved;
    this.eth_reserved = eth_reserved;
    this.xrp_reserved = xrp_reserved;
    this.ltc_reserved = ltc_reserved;
    this.usdt_reserved = usdt_reserved;
    this.xlm_reserved = xlm_reserved;
    this.try_available = try_available;
    this.btc_available = btc_available;
    this.eth_available = eth_available;
    this.xrp_available = xrp_available;
    this.ltc_available = ltc_available;
    this.usdt_available = usdt_available;
    this.xlm_available = xlm_available;
    this.btctry_taker_fee_percentage = btctry_taker_fee_percentage;
    this.btctry_maker_fee_percentage = btctry_maker_fee_percentage;
    this.ethtry_taker_fee_percentage = ethtry_taker_fee_percentage;
    this.ethtry_maker_fee_percentage = ethtry_maker_fee_percentage;
    this.ethbtc_taker_fee_percentage = ethbtc_taker_fee_percentage;
    this.ethbtc_maker_fee_percentage = ethbtc_maker_fee_percentage;

    this.xrptry_taker_fee_percentage = xrptry_taker_fee_percentage;
    this.xrptry_maker_fee_percentage = xrptry_maker_fee_percentage;
    this.ltctry_taker_fee_percentage = ltctry_taker_fee_percentage;
    this.ltctry_maker_fee_percentage = ltctry_maker_fee_percentage;
    this.usdttry_taker_fee_percentage = usdttry_taker_fee_percentage;
    this.usdttry_maker_fee_percentage = usdttry_maker_fee_percentage;
    this.xlmtry_taker_fee_percentage = xlmtry_taker_fee_percentage;
    this.xlmtry_maker_fee_percentage = xlmtry_maker_fee_percentage;
  }

  public BigDecimal getTry_balance() {
    return try_balance;
  }

  public BigDecimal getBtc_balance() {
    return btc_balance;
  }

  public BigDecimal getEth_balance() {
    return eth_balance;
  }

  public BigDecimal getTry_reserved() {
    return try_reserved;
  }

  public BigDecimal getBtc_reserved() {
    return btc_reserved;
  }

  public BigDecimal getEth_reserved() {
    return eth_reserved;
  }

  public BigDecimal getTry_available() {
    return try_available;
  }

  public BigDecimal getBtc_available() {
    return btc_available;
  }

  public BigDecimal getEth_available() {
    return eth_available;
  }

  public BigDecimal getBtctry_taker_fee_percentage() {
    return btctry_taker_fee_percentage;
  }

  public BigDecimal getBtctry_maker_fee_percentage() {
    return btctry_maker_fee_percentage;
  }

  public BigDecimal getEthtry_taker_fee_percentage() {
    return ethtry_taker_fee_percentage;
  }

  public BigDecimal getEthtry_maker_fee_percentage() {
    return ethtry_maker_fee_percentage;
  }

  public BigDecimal getEthbtc_taker_fee_percentage() {
    return ethbtc_taker_fee_percentage;
  }

  public BigDecimal getEthbtc_maker_fee_percentage() {
    return ethbtc_maker_fee_percentage;
  }

  public BigDecimal getXrp_balance() {
    return xrp_balance;
  }

  public BigDecimal getLtc_balance() {
    return ltc_balance;
  }

  public BigDecimal getUsdt_balance() {
    return usdt_balance;
  }

  public BigDecimal getXlm_balance() {
    return xlm_balance;
  }

  public BigDecimal getXrp_reserved() {
    return xrp_reserved;
  }

  public BigDecimal getLtc_reserved() {
    return ltc_reserved;
  }

  public BigDecimal getUsdt_reserved() {
    return usdt_reserved;
  }

  public BigDecimal getXlm_reserved() {
    return xlm_reserved;
  }

  public BigDecimal getXrp_available() {
    return xrp_available;
  }

  public BigDecimal getLtc_available() {
    return ltc_available;
  }

  public BigDecimal getUsdt_available() {
    return usdt_available;
  }

  public BigDecimal getXlm_available() {
    return xlm_available;
  }

  public BigDecimal getXrptry_taker_fee_percentage() {
    return xrptry_taker_fee_percentage;
  }

  public BigDecimal getXrptry_maker_fee_percentage() {
    return xrptry_maker_fee_percentage;
  }

  public BigDecimal getLtctry_taker_fee_percentage() {
    return ltctry_taker_fee_percentage;
  }

  public BigDecimal getLtctry_maker_fee_percentage() {
    return ltctry_maker_fee_percentage;
  }

  public BigDecimal getUsdttry_taker_fee_percentage() {
    return usdttry_taker_fee_percentage;
  }

  public BigDecimal getUsdttry_maker_fee_percentage() {
    return usdttry_maker_fee_percentage;
  }

  public BigDecimal getXlmtry_taker_fee_percentage() {
    return xlmtry_taker_fee_percentage;
  }

  public BigDecimal getXlmtry_maker_fee_percentage() {
    return xlmtry_maker_fee_percentage;
  }

  @Override
  public String toString() {
    return "BTCTurkAccountBalance [try_balance="
        + try_balance
        + ", btc_balance="
        + btc_balance
        + ", eth_balance="
        + eth_balance
        + ", xrp_balance="
        + xrp_balance
        + ", ltc_balance="
        + ltc_balance
        + ", usdt_balance="
        + usdt_balance
        + ", xlm_balance="
        + xlm_balance
        + ", try_reserved="
        + try_reserved
        + ", btc_reserved="
        + btc_reserved
        + ", eth_reserved="
        + eth_reserved
        + ", xrp_reserved="
        + xrp_reserved
        + ", ltc_reserved="
        + ltc_reserved
        + ", usdt_reserved="
        + usdt_reserved
        + ", xlm_reserved="
        + xlm_reserved
        + ", try_available="
        + try_available
        + ", btc_available="
        + btc_available
        + ", eth_available="
        + eth_available
        + ", xrp_available="
        + xrp_available
        + ", ltc_available="
        + ltc_available
        + ", usdt_available="
        + usdt_available
        + ", xlm_available="
        + xlm_available
        + ", btctry_taker_fee_percentage="
        + btctry_taker_fee_percentage
        + ", btctry_maker_fee_percentage="
        + btctry_maker_fee_percentage
        + ", ethtry_taker_fee_percentage="
        + ethtry_taker_fee_percentage
        + ", ethtry_maker_fee_percentage="
        + ethtry_maker_fee_percentage
        + ", ethbtc_taker_fee_percentage="
        + ethbtc_taker_fee_percentage
        + ", ethbtc_maker_fee_percentage="
        + ethbtc_maker_fee_percentage
        + ", xrptry_taker_fee_percentage="
        + xrptry_taker_fee_percentage
        + ", xrptry_maker_fee_percentage="
        + xrptry_maker_fee_percentage
        + ", ltctry_taker_fee_percentage="
        + ltctry_taker_fee_percentage
        + ", ltctry_maker_fee_percentage="
        + ltctry_maker_fee_percentage
        + ", usdttry_taker_fee_percentage="
        + usdttry_taker_fee_percentage
        + ", usdttry_maker_fee_percentage="
        + usdttry_maker_fee_percentage
        + ", xlmtry_taker_fee_percentage="
        + xlmtry_taker_fee_percentage
        + ", xlmtry_maker_fee_percentage="
        + xlmtry_maker_fee_percentage
        + "]";
  }
}
