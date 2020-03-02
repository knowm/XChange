package info.bitrich.xchangestream.lgo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class LgoTrade {

  private final String id;
  private final String side;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final Date creationTime;

  public LgoTrade(
      @JsonProperty("trade_id") String id,
      @JsonProperty("side") String side,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("trade_creation_time")
          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          Date creationTime) {
    this.id = id;
    this.side = side;
    this.price = price;
    this.quantity = quantity;
    this.creationTime = creationTime;
  }

  public String getId() {
    return id;
  }

  public String getSide() {
    return side;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public Date getCreationTime() {
    return creationTime;
  }
}
