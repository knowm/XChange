package org.knowm.xchange.cryptonit2.dto.trade;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class CryptonitErrorDeserializer extends JsonDeserializer<String> {

  @Override
  public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {

    ObjectCodec oc = jsonParser.getCodec();
    JsonNode node = oc.readTree(jsonParser);
    if (node.isTextual()) {
      return node.textValue();
    } else if (node.isObject()) {
      JsonNode allNode = node.get("__all__");
      if (allNode != null && allNode.isArray()) {
        StringBuilder buf = new StringBuilder();
        for (JsonNode msgNode : allNode) {
          buf.append(msgNode.textValue());
          buf.append(",");
        }

        return buf.length() > 0 ? buf.substring(0, buf.length() - 1) : buf.toString();
      }

      return node.toString();
    }

    return null;
  }
}
