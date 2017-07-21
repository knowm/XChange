package org.knowm.xchange.jubi.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.jubi.JubiUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Dzf on 2017/7/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JubiOrder {
  private final BigDecimal id;
  private final Date datetime;
  private final JubiOrderType type;
  private final BigDecimal price;
  private final BigDecimal amountOriginal;
  private final BigDecimal amountOutstanding;

  public JubiOrder(@JsonProperty("id") BigDecimal id,
                   @JsonProperty("datetime") String datetime,
                   @JsonProperty("type") @JsonDeserialize(using = JubiOrderType.JubiOrderTypeDeserializer.class) JubiOrderType type,
                   @JsonProperty("price") BigDecimal price,
                   @JsonProperty("amount_original") BigDecimal amountOriginal,
                   @JsonProperty("amount_outstanding") BigDecimal amountOutstanding) {
    this.id = id;
    this.datetime = JubiUtils.parseDate(datetime);
    this.type = type;
    this.price = price;
    this.amountOriginal = amountOriginal;
    this.amountOutstanding = amountOutstanding;
  }

  public BigDecimal getId() {
    return id;
  }

  public Date getDatetime() {
    return datetime;
  }

  public JubiOrderType getType() {
    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmountOriginal() {
    return amountOriginal;
  }

  public BigDecimal getAmountOutstanding() {
    return amountOutstanding;
  }

  @Override
  public String toString() {
    return String.format("JubiOrder{id=%s, datetime=%s, type=%s, price=%s, original amount=%s, outstanding amount=%s}",
            id, datetime, type, price, amountOriginal, amountOutstanding);
  }
}
