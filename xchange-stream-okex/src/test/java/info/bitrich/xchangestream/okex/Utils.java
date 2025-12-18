package info.bitrich.xchangestream.okex;

import org.knowm.xchange.dto.marketdata.Ticker;

import java.math.BigDecimal;

import static java.math.RoundingMode.UP;

public class Utils {
  static BigDecimal getMinAmount(
      BigDecimal usdtMin, BigDecimal amount, Ticker ticker, int volumeScale) {
    // minimal trade size - 5 USDT
    if (amount.multiply(ticker.getLast()).compareTo(usdtMin) <= 0) {
      amount = usdtMin.divide(ticker.getLast(), volumeScale, UP);
    }
    return amount;
  }
}
