package info.bitrich.xchangestream.coinmate.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinmateWebsocketBalance {

  @JsonProperty("balance")
  private final BigDecimal balance;

  @JsonProperty("reserved")
  private final BigDecimal reserved;

  @JsonCreator
  public CoinmateWebsocketBalance(
      @JsonProperty("balance") BigDecimal balance, @JsonProperty("reserved") BigDecimal reserved) {
    this.balance = balance;
    this.reserved = reserved;
  }

  public BigDecimal getBalance() {
    return this.balance;
  }

  public BigDecimal getReserved() {
    return this.reserved;
  }

  @Override
  public String toString() {
    return "CoinmateWebsocketBalance{" + "balance=" + balance + ", reserved=" + reserved + '}';
  }
}
