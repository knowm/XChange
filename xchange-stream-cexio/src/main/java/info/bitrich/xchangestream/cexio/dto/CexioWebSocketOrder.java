package info.bitrich.xchangestream.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class CexioWebSocketOrder {

  private final String id;
  private final BigDecimal remains;
  private final BigDecimal fremains;
  private final boolean cancel;
  private final CexioWebSocketPair pair;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final Date time;
  private final String type;
  private final BigDecimal fee;

  public CexioWebSocketOrder(
      @JsonProperty("id") String id,
      @JsonProperty("remains") BigDecimal remains,
      @JsonProperty("fremains") BigDecimal fremains,
      @JsonProperty("cancel") boolean cancel,
      @JsonProperty("pair") CexioWebSocketPair pair,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("time") Date time,
      @JsonProperty("type") String type,
      @JsonProperty("fee") BigDecimal fee) {
    this.id = id;
    this.remains = remains;
    this.fremains = fremains;
    this.cancel = cancel;
    this.pair = pair;
    this.price = price;
    this.amount = amount;
    this.time = time;
    this.type = type;
    this.fee = fee;
  }

  public String getId() {
    return id;
  }

  public BigDecimal getRemains() {
    return remains;
  }

  public BigDecimal getFremains() {
    return fremains;
  }

  public boolean isCancel() {
    return cancel;
  }

  public CexioWebSocketPair getPair() {
    return pair;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Date getTime() {
    return time;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getFee() {
    return fee;
  }

  @Override
  public String toString() {
    return "CexioWebSocketOrder{"
        + "id='"
        + id
        + '\''
        + ", remains="
        + remains
        + ", fremains="
        + fremains
        + ", cancel="
        + cancel
        + ", pair="
        + pair
        + ", price="
        + price
        + ", amount="
        + amount
        + ", time="
        + time
        + ", type='"
        + type
        + '\''
        + ", fee="
        + fee
        + '}';
  }
}
