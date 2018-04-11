package org.knowm.xchange.bitso.dto.trade;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class BitsoErrorDeserializer extends JsonDeserializer<String> {

  @Override
  public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {

    ObjectCodec oc = jsonParser.getCodec();
    JsonNode node = oc.readTree(jsonParser);
    if (node.isTextual()) {
      return node.textValue();
    } else if (node.isObject()) {
      JsonNode allNode = node.get("__all__");
      if (allNode != null && allNode.isArray()) {
        StringBuffer buf = new StringBuffer();
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
