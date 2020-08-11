package info.bitrich.xchangestream.cexio;

import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;

public class CexioOrder extends LimitOrder {

  private BigDecimal remainingAmount;

  public CexioOrder(
      OrderType type,
      CurrencyPair currencyPair,
      BigDecimal originalAmount,
      String id,
      Date timestamp,
      BigDecimal limitPrice,
      BigDecimal fee,
      OrderStatus status) {
    super(type, originalAmount, currencyPair, id, timestamp, limitPrice, null, null, fee, status);
    this.remainingAmount = null;
  }

  public CexioOrder(
      CurrencyPair currencyPair, String id, OrderStatus status, BigDecimal remainingAmount) {
    this(null, currencyPair, null, id, null, null, null, status);
    this.remainingAmount = remainingAmount;
  }

  @Override
  public BigDecimal getRemainingAmount() {
    if (remainingAmount != null) {
      return remainingAmount;
    }

    return super.getRemainingAmount();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CexioOrder)) return false;
    if (!super.equals(o)) return false;

    CexioOrder that = (CexioOrder) o;

    return remainingAmount != null
        ? remainingAmount.compareTo(that.remainingAmount) == 0
        : that.remainingAmount == null;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (remainingAmount != null ? remainingAmount.hashCode() : 0);
    return result;
  }
}
