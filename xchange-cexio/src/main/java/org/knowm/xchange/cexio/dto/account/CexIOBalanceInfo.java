package org.knowm.xchange.cexio.dto.account;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@JsonDeserialize(using = CexIOBalanceInfo.Deserializer.class)
public class CexIOBalanceInfo {

  private final String error;
  private final long timestamp;
  private final String username;
  private final Map<String, CexIOBalance> balances;

  public CexIOBalanceInfo(String error, long timestamp, String username, Map<String, CexIOBalance> balances) {
    this.error = error;
    this.timestamp = timestamp;
    this.username = username;
    this.balances = balances;
  }

  public String getError() {
    return error;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public String getUsername() {
    return username;
  }

  public Map<String, CexIOBalance> getBalances() {
    return balances;
  }

  static class Deserializer extends JsonDeserializer<CexIOBalanceInfo> {

    @Override
    public CexIOBalanceInfo deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
      Long timestamp = null;
      String username = null;
      String error = null;
      Map<String, CexIOBalance> balances = new HashMap<>();

      JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
      Iterator<Map.Entry<String, JsonNode>> tradesResultIterator = jsonNode.fields();
      while (tradesResultIterator.hasNext()) {
        Map.Entry<String, JsonNode> entry = tradesResultIterator.next();

        String key = entry.getKey();
        JsonNode node = entry.getValue();

        if (key.equalsIgnoreCase("timestamp")) {
          timestamp = node.asLong();
        } else if (key.equalsIgnoreCase("username")) {
          username = node.asText();
        } else if (key.equalsIgnoreCase("error")) {
          error = node.asText();
        } else if (node.isObject()) {
          BigDecimal available = node.has("available") ? new BigDecimal(node.get("available").asText()) : null;
          BigDecimal orders = node.has("orders") ? new BigDecimal(node.get("orders").asText()) : null;
          BigDecimal bonus = node.has("bonus") ? new BigDecimal(node.get("bonus").asText()) : null;
          balances.put(key, new CexIOBalance(available, orders, bonus));
        }
      }

      return new CexIOBalanceInfo(error, timestamp, username, balances);
    }
  }

}
