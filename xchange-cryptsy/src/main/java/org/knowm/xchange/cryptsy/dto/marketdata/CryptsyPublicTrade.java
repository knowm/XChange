package org.knowm.xchange.cryptsy.dto.marketdata;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.CryptsyUtils;

@JsonIgnoreProperties("id")
public class CryptsyPublicTrade {

  private final long id;
  private final Date time;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final BigDecimal total;
  private final String type;

  /**
   * Constructor
   * 
   * @throws ParseException
   */
  @JsonCreator
  public CryptsyPublicTrade(@JsonProperty("id") long id, @JsonProperty("time") String time, @JsonProperty("price") BigDecimal price,
      @JsonProperty("quantity") BigDecimal quantity, @JsonProperty("total") BigDecimal total, @JsonProperty("type") String type)
      throws ParseException {

    this.id = id;
    this.time = time == null ? null : CryptsyUtils.convertDateTime(time);
    this.price = price;
    this.quantity = quantity;
    this.total = total;
    this.type = type;
  }

  public long getId() {

    return id;
  }

  public Date getTime() {

    return time;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getQuantity() {

    return quantity;
  }

  public BigDecimal getTotal() {

    return total;
  }

  public String getType() {

    return type;
  }

  @Override
  public String toString() {

    return "CryptsyPublicTrade [id=" + id + ", time=" + time + ", price=" + price + ", quantity=" + quantity + ", total=" + total + "]";
  }
}
