package com.knowm.xchange.serum.core;

import com.knowm.xchange.serum.dto.PublicKey;
import com.knowm.xchange.serum.structures.MarketStat;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Market {

  public final MarketStat decoded;
  public final int baseMintDecimals;
  public final int quoteMintDecimals;
  public final MarketOptions options;
  public final PublicKey programId;

  public Market(
      final MarketStat decoded,
      int baseMintDecimals,
      int quoteMintDecimals,
      final MarketOptions options,
      final PublicKey programId) {
    this.decoded = decoded;
    this.baseMintDecimals = baseMintDecimals;
    this.quoteMintDecimals = quoteMintDecimals;
    this.options = options;
    this.programId = programId;
  }

  public BigInteger maskn(final BigInteger num, final int mask) {
    BigInteger currNum = num;
    for (int i = num.bitLength() - 1; i >= mask; i--) {
      currNum = num.clearBit(i);
    }
    return currNum;
  }

  public double getMinOrderSize() {
    return baseSizeLotsToNumber(new BigDecimal(1));
  }

  public double getTickSize() {
    return priceLotsToNumber(new BigDecimal(1));
  }

  public double baseSizeLotsToNumber(final BigDecimal size) {
    return divideBNToNumber(
        size.multiply(BigDecimal.valueOf(this.decoded.getBaseLotSize())), baseSplTokenMultiplier());
  }

  public double priceLotsToNumber(final BigDecimal price) {
    return divideBNToNumber(
        price.multiply(
            BigDecimal.valueOf(this.decoded.getQuoteLotSize()).multiply(baseSplTokenMultiplier())),
        BigDecimal.valueOf(this.decoded.getBaseLotSize()).multiply(quoteSplTokenMultiplier()));
  }

  public double divideBNToNumber(final BigDecimal numerator, final BigDecimal denominator) {
    final BigInteger quotient = numerator.divide(denominator, RoundingMode.HALF_UP).toBigInteger();
    final BigDecimal rem = numerator.divideAndRemainder(denominator)[1];
    final BigInteger gcd = rem.toBigInteger().gcd(denominator.toBigInteger());
    return quotient.doubleValue()
        + rem.divide(new BigDecimal(gcd), RoundingMode.HALF_UP).doubleValue()
            / denominator.toBigInteger().divide(gcd).doubleValue();
  }

  public BigDecimal baseSplTokenMultiplier() {
    return new BigDecimal(10).pow(baseMintDecimals);
  }

  public BigDecimal quoteSplTokenMultiplier() {
    return new BigDecimal(10).pow(quoteMintDecimals);
  }

  public BigDecimal baseSizeLotsToNumber(final BigDecimal size, final int decimalPlaces) {
    return size.multiply(new BigDecimal(decoded.getBaseLotSize()))
        .divide(baseSplTokenMultiplier(), RoundingMode.HALF_UP)
        .setScale(decimalPlaces, RoundingMode.HALF_UP);
  }

  public BigDecimal priceNumberToLots(final BigInteger price, final int decimalPlaces) {
    final BigDecimal a =
        new BigDecimal(price)
            .multiply(quoteSplTokenMultiplier())
            .multiply(new BigDecimal(decoded.getBaseLotSize()));
    final BigDecimal b =
        baseSplTokenMultiplier().multiply(new BigDecimal(decoded.getQuoteLotSize()));
    final BigDecimal c = a.divide(b, RoundingMode.HALF_UP);
    return c.setScale(decimalPlaces, RoundingMode.HALF_UP);
  }

  public BigInteger extractPrice(final byte[] orderId) {
    return new BigInteger(orderId).shiftRight(64);
  }
}
