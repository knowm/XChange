package org.knowm.xchange.coinsetter.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Quote.
 */
public class CoinsetterQuote {

  private final String symbol;
  private final BigDecimal quantity;
  private final BigDecimal vwapPrice;
  private final String vwapCurrency;
  private final BigDecimal totalPrice;

  public CoinsetterQuote(@JsonProperty("symbol") String symbol, @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("vwapPrice") BigDecimal vwapPrice, @JsonProperty("vwapCurrency") String vwapCurrency,
      @JsonProperty("totalPrice") BigDecimal totalPrice) {

    this.symbol = symbol;
    this.quantity = quantity;
    this.vwapPrice = vwapPrice;
    this.vwapCurrency = vwapCurrency;
    this.totalPrice = totalPrice;
  }

  /**
   * Returns the trade symbol.
   *
   * @return the trade symbol.
   */
  public String getSymbol() {

    return symbol;
  }

  /**
   * Quantity you specified (If more than 150 you will recieve the error message: {"error":"QUANTITY TOO HIGH. MUST BE LESS THAN 150"}, if not
   * divisible by 5 then you will recieve the message {"error":"QUANTITY MUST BE A UNIT OF 5"}).
   *
   * @return the quantity you specified.
   */
  public BigDecimal getQuantity() {

    return quantity;
  }

  /**
   * Returns the current BTC price as calculated by VWAP.
   *
   * @return the current BTC price as calculated by VWAP.
   */
  public BigDecimal getVwapPrice() {

    return vwapPrice;
  }

  /**
   * Returns the currency VWAP calculated in.
   *
   * @return the currency VWAP calculated in.
   */
  public String getVwapCurrency() {

    return vwapCurrency;
  }

  /**
   * Returns the total dollar amount for specified quantity.
   *
   * @return the total dollar amount for specified quantity.
   */
  public BigDecimal getTotalPrice() {

    return totalPrice;
  }

  @Override
  public String toString() {

    return "CoinsetterQuote [symbol=" + symbol + ", quantity=" + quantity + ", vwapPrice=" + vwapPrice + ", vwapCurrency=" + vwapCurrency
        + ", totalPrice=" + totalPrice + "]";
  }

}
