package org.knowm.xchange.coinbase.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinbase.v2.dto.CoinbaseAmount;
import org.knowm.xchange.coinbase.v2.dto.CoinbasePrice;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class CoinbaseWalletResponseData<
    T extends CoinbaseWalletResponseData.CoinbaseWalletResponse> {

  private final T data;

  CoinbaseWalletResponseData(@JsonProperty("data") T data) {
    this.data = data;
  }

  public T getData() {
    return data;
  }

  @Override
  public String toString() {
    return "" + data;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  static class CoinbaseWalletResponse {

    private final String id;
    private final String status;
    private final String transaction;
    private final boolean committed;

    private CoinbaseAmount amount;
    private CoinbasePrice fee;
    private CoinbasePrice total;
    private CoinbasePrice subtotal;

    @JsonCreator
    CoinbaseWalletResponse(
        @JsonProperty("id") String id,
        @JsonProperty("status") String status,
        @JsonProperty("transaction") String transaction,
        @JsonProperty("commited") boolean committed) {
      this.id = id;
      this.status = status;
      this.transaction = transaction;
      this.committed = committed;
    }

    public String getId() {
      return id;
    }

    public String getStatus() {
      return status;
    }

    public String getTransaction() {
      return transaction;
    }

    public boolean isCommitted() {
      return committed;
    }

    public CoinbasePrice getFee() {
      return fee;
    }

    void setFee(CoinbasePrice fee) {
      this.fee = fee;
    }

    public CoinbaseAmount getAmount() {
      return amount;
    }

    void setAmount(CoinbaseAmount amount) {
      this.amount = amount;
    }

    public CoinbasePrice getTotal() {
      return total;
    }

    void setTotal(CoinbasePrice total) {
      this.total = total;
    }

    public CoinbasePrice getSubtotal() {
      return subtotal;
    }

    void setSubtotal(CoinbasePrice subtotal) {
      this.subtotal = subtotal;
    }

    @Override
    public String toString() {
      String curr = amount.getCurrency();
      int scale = "EUR".equals(curr) || "USD".equals(curr) ? 2 : 8;
      String astr = String.format("amount=%." + scale + "f %s", amount.getAmount(), curr);
      String prices = "fee=" + fee + ",subtotal=" + subtotal + ",total=" + total;
      return getClass().getSimpleName()
          + "[id="
          + id
          + ",status="
          + status
          + ",committed="
          + committed
          + ","
          + astr
          + ","
          + prices
          + "]";
    }
  }
}
