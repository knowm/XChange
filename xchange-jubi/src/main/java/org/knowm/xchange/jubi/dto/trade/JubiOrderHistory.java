package org.knowm.xchange.jubi.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;

/**
 * Created by Dzf on 2017/7/17.
 */
public class JubiOrderHistory {
  private final JubiTradeResult result;
  private final JubiOrder[] orderList;
  private final static ObjectMapper mapper = new ObjectMapper();

  @JsonCreator
  public JubiOrderHistory(JsonNode jsonNode) {
    if (jsonNode.isObject()) {
      this.orderList = null;
      this.result = mapper.convertValue(jsonNode, JubiTradeResult.class);
    } else if (jsonNode.isArray()){
      this.orderList = mapper.convertValue(jsonNode, JubiOrder[].class);
      this.result = new JubiTradeResult(true, 0, null);
    } else {
      throw new NotAvailableFromExchangeException();
    }
  }

  public JubiTradeResult getResult() {
    return result;
  }

  public JubiOrder[] getOrderList() {
    return orderList;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    for (JubiOrder jubiOrder : orderList) {
      sb.append(jubiOrder.toString() + ", ");
    }
    sb.setLength(sb.length() - 2);
    return String.format("JubiOrderHistory{result=%s, orders=[%s]}", result, sb.toString());
  }
}
