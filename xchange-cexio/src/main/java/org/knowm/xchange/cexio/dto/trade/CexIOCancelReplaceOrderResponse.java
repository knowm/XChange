package org.knowm.xchange.cexio.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CexIOCancelReplaceOrderResponse {

  private Boolean complete;
  private BigDecimal price;
  private BigDecimal amount;
  private Date time;
  private String type;
  private String id;
  private BigDecimal pending;

  private String error;

  public Boolean getComplete() {
    return complete;
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

  public String getId() {
    return id;
  }

  public BigDecimal getPending() {
    return pending;
  }

  public String getError() {
    return error;
  }

  @Override
  public String toString() {
    final StringBuffer buffer = new StringBuffer("{");
    buffer.append("id='").append(id).append('\'');
    buffer.append(", complete=").append(complete);
    buffer.append(", price=").append(price);
    buffer.append(", amount=").append(amount);
    buffer.append(", time=").append(time);
    buffer.append(", type='").append(type).append('\'');
    buffer.append(", pending=").append(pending);
    buffer.append(", error=").append(error);
    buffer.append('}');
    return buffer.toString();
  }
}
