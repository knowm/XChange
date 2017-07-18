package org.knowm.xchange.jubi.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Dzf on 2017/7/16.
 */
public class JubiOrderStatus {
  @JsonProperty("status") @JsonDeserialize(using = JubiStatusType.JubiStatusTypeDeserializer.class)
  private final JubiStatusType status;
  @JsonProperty("avg_price")
  private final BigDecimal avgPrice;
  private final JubiTradeResult result;
  private final JubiOrder order;
  private final static ObjectMapper mapper = new ObjectMapper();

  @JsonCreator
  public JubiOrderStatus(JsonNode jsonNode) {
    if (jsonNode.has("result")) {
      this.result = mapper.convertValue(jsonNode, JubiTradeResult.class);
      this.status = null;
      this.avgPrice = null;
      this.order = null;
    } else {
      this.result = new JubiTradeResult(true, 0, null);
      this.status = JubiStatusType.fromString(jsonNode.get("status").asText());
      this.avgPrice = new BigDecimal(jsonNode.get("avg_price").asText());
      this.order = mapper.convertValue(jsonNode, JubiOrder.class);
    }
  }

  public JubiTradeResult getResult() {
    return result;
  }

  public JubiStatusType getStatus() {
    return status;
  }

  public BigDecimal getAvgPrice() {
    return avgPrice;
  }

  public BigDecimal getId() {
    return this.order != null ? this.order.getId() : null;
  }

  public Date getDatetime() {
    return this.order != null ? this.order.getDatetime() : null;
  }

  public JubiOrderType getType() {
    return this.order != null ? this.order.getType() : null;
  }

  public BigDecimal getPrice() {
    return this.order != null ? this.order.getPrice() : null;
  }

  public BigDecimal getAmountOriginal() {
    return this.order != null ? this.order.getAmountOriginal() : null;
  }

  public BigDecimal getAmountOutstanding() {
    return this.order != null ? this.order.getAmountOutstanding() : null;
  }
  @Override
  public String toString() {
    return String.format("JubiOrderStatus{result=%s, status=%s, avg_price=%s, order=%s}",
            result, status, avgPrice, order);
  }
}
