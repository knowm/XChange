package info.bitrich.xchangestream.bitfinex.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class BitfinexWebSocketAuthOrder {
  private long id;
  private long groupId;
  private long cid;
  private String symbol;
  private long mtsCreate;
  private long mtsUpdate;
  private BigDecimal amount;
  private BigDecimal amountOrig;
  private String type;
  private String typePrev;
  private String orderStatus;
  private BigDecimal price;
  private BigDecimal priceAvg;
  private BigDecimal priceTrailing;
  private BigDecimal priceAuxLimit;
  private long placedId;
  private int flags;

  public BitfinexWebSocketAuthOrder(
      long id,
      long groupId,
      long cid,
      String symbol,
      long mtsCreate,
      long mtsUpdate,
      BigDecimal amount,
      BigDecimal amountOrig,
      String type,
      String typePrev,
      String orderStatus,
      BigDecimal price,
      BigDecimal priceAvg,
      BigDecimal priceTrailing,
      BigDecimal priceAuxLimit,
      long placedId,
      int flags) {
    this.id = id;
    this.groupId = groupId;
    this.cid = cid;
    this.symbol = symbol;
    this.mtsCreate = mtsCreate;
    this.mtsUpdate = mtsUpdate;
    this.amount = amount;
    this.amountOrig = amountOrig;
    this.type = type;
    this.typePrev = typePrev;
    this.orderStatus = orderStatus;
    this.price = price;
    this.priceAvg = priceAvg;
    this.priceTrailing = priceTrailing;
    this.priceAuxLimit = priceAuxLimit;
    this.placedId = placedId;
    this.flags = flags;
  }

  @Override
  public String toString() {
    return "BitfinexWebSocketAuthenticatedOrder{"
        + "id="
        + id
        + ", groupId="
        + groupId
        + ", cid="
        + cid
        + ", symbol='"
        + symbol
        + '\''
        + ", mtsCreate="
        + mtsCreate
        + ", mtsUpdate="
        + mtsUpdate
        + ", amount="
        + amount
        + ", amountOrig="
        + amountOrig
        + ", type='"
        + type
        + '\''
        + ", typePrev='"
        + typePrev
        + '\''
        + ", orderStatus='"
        + orderStatus
        + '\''
        + ", price="
        + price
        + ", priceAvg="
        + priceAvg
        + ", priceTrailing="
        + priceTrailing
        + ", priceAuxLimit="
        + priceAuxLimit
        + ", placedId="
        + placedId
        + ", flags="
        + flags
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BitfinexWebSocketAuthOrder)) return false;
    BitfinexWebSocketAuthOrder that = (BitfinexWebSocketAuthOrder) o;
    return id == that.id
        && groupId == that.groupId
        && cid == that.cid
        && mtsCreate == that.mtsCreate
        && mtsUpdate == that.mtsUpdate
        && placedId == that.placedId
        && flags == that.flags
        && Objects.equals(symbol, that.symbol)
        && Objects.equals(amount, that.amount)
        && Objects.equals(amountOrig, that.amountOrig)
        && Objects.equals(type, that.type)
        && Objects.equals(typePrev, that.typePrev)
        && Objects.equals(orderStatus, that.orderStatus)
        && Objects.equals(price, that.price)
        && Objects.equals(priceAvg, that.priceAvg)
        && Objects.equals(priceTrailing, that.priceTrailing)
        && Objects.equals(priceAuxLimit, that.priceAuxLimit);
  }

  @Override
  public int hashCode() {

    return Objects.hash(
        id,
        groupId,
        cid,
        symbol,
        mtsCreate,
        mtsUpdate,
        amount,
        amountOrig,
        type,
        typePrev,
        orderStatus,
        price,
        priceAvg,
        priceTrailing,
        priceAuxLimit,
        placedId,
        flags);
  }

  public long getId() {
    return id;
  }

  public long getGroupId() {
    return groupId;
  }

  public long getCid() {
    return cid;
  }

  public String getSymbol() {
    return symbol;
  }

  public long getMtsCreate() {
    return mtsCreate;
  }

  public long getMtsUpdate() {
    return mtsUpdate;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getAmountOrig() {
    return amountOrig;
  }

  public String getType() {
    return type;
  }

  public String getTypePrev() {
    return typePrev;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getPriceAvg() {
    return priceAvg;
  }

  public BigDecimal getPriceTrailing() {
    return priceTrailing;
  }

  public BigDecimal getPriceAuxLimit() {
    return priceAuxLimit;
  }

  public long getPlacedId() {
    return placedId;
  }

  public int getFlags() {
    return flags;
  }
}
