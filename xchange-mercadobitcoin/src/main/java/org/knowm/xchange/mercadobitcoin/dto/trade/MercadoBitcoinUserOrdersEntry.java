package org.knowm.xchange.mercadobitcoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/** @author Felipe Micaroni Lalli */
public class MercadoBitcoinUserOrdersEntry {

  private final String status;
  private final Long created;
  private final BigDecimal price;
  private final BigDecimal volume;
  private final String pair;
  private final String type;
  private final Operations operations;

  public MercadoBitcoinUserOrdersEntry(
      @JsonProperty("status") String status,
      @JsonProperty("created") Long created,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("pair") String pair,
      @JsonProperty("type") String type,
      @JsonProperty("operations") Operations operations) {

    this.status = status;
    this.created = created;
    this.price = price;
    this.volume = volume;
    this.pair = pair;
    this.type = type;
    this.operations = operations;
  }

  public String getStatus() {

    return status;
  }

  public Long getCreated() {

    return created;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public String getPair() {

    return pair;
  }

  public String getType() {

    return type;
  }

  public Operations getOperations() {

    return operations;
  }

  @Override
  public String toString() {

    return "MercadoBitcoinUserOrdersEntry ["
        + "status='"
        + status
        + '\''
        + ", created="
        + created
        + ", price="
        + price
        + ", volume="
        + volume
        + ", pair='"
        + pair
        + '\''
        + ", type='"
        + type
        + '\''
        + ", operations="
        + operations
        + ']';
  }

  public static final class Operations extends HashMap<String, OperationEntry> {

    public Operations(int initialCapacity, float loadFactor) {

      super(initialCapacity, loadFactor);
    }

    public Operations(int initialCapacity) {

      super(initialCapacity);
    }

    public Operations() {}

    public Operations(Map<? extends String, ? extends OperationEntry> m) {

      super(m);
    }
  }
}
