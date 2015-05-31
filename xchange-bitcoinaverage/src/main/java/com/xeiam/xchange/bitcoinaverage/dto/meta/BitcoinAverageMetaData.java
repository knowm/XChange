package com.xeiam.xchange.bitcoinaverage.dto.meta;

/**
 * @author Rafał Krupiński
 */
public class BitcoinAverageMetaData {
  public int priceScale;

  @Override
  public String toString() {
    return "BitcoinAverageMetaData{" +
        "priceScale=" + priceScale +
        '}';
  }
}
