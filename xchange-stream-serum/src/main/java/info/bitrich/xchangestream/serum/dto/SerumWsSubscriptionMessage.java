package info.bitrich.xchangestream.serum.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.knowm.xchange.serum.SerumConfigs.Commitment;
import com.knowm.xchange.serum.SerumConfigs.SubscriptionType;

public class SerumWsSubscriptionMessage {

  public final String JSON_RPC = "jsonrpc";
  public final String ID = "id";
  public final String METHOD = "method";
  public final String PARAMS = "params";

  public final String ENCODING = "encoding";
  public final String COMMITMENT = "commitment";

  public final JsonNode msg;

  public SerumWsSubscriptionMessage(
      final Commitment commitment,
      final SubscriptionType subscriptionType,
      final String publicKey,
      int reqID) {
    final ObjectNode param1 = JsonNodeFactory.instance.objectNode();
    param1.put(ENCODING, "base64");
    param1.put(COMMITMENT, commitment.name());

    final ArrayNode params = JsonNodeFactory.instance.arrayNode();
    params.add(publicKey);
    params.add(param1);

    final ObjectNode msg = JsonNodeFactory.instance.objectNode();
    msg.put(JSON_RPC, "2.0");
    msg.put(ID, reqID);
    msg.put(METHOD, subscriptionType.name());
    msg.set(PARAMS, params);

    this.msg = msg;
  }

  public String buildMsg() {
    return msg.toString();
  }
}
