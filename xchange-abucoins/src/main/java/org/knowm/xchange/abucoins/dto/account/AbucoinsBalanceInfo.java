package org.knowm.xchange.abucoins.dto.account;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = AbucoinsBalanceInfo.Deserializer.class)
public class AbucoinsBalanceInfo {

  private final String error;
  private final Long timestamp;
  private final String username;
  private final Map<String, AbucoinsBalance> balances;

  public AbucoinsBalanceInfo(String error, Long timestamp, String username, Map<String, AbucoinsBalance> balances) {
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

  public Map<String, AbucoinsBalance> getBalances() {
    return balances;
  }

  static class Deserializer extends JsonDeserializer<AbucoinsBalanceInfo> {

    @Override
    public AbucoinsBalanceInfo deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
      Long timestamp = null;
      String username = null;
      String error = null;
      Map<String, AbucoinsBalance> balances = new HashMap<>();

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
          balances.put(key, new AbucoinsBalance(available, orders, bonus));
        }
      }

      return new AbucoinsBalanceInfo(error, timestamp, username, balances);
    }
  }

}
