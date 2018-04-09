package org.knowm.xchange.gdax.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.NumericNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

public class GDAXCandleDeserializer extends StdDeserializer<GDAXCandle> {

  private static final long serialVersionUID = 1L;

  public GDAXCandleDeserializer() {
    this(null);
  }

  public GDAXCandleDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public GDAXCandle deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    TreeNode jo = mapper.readTree(jp);
    if (!jo.isArray() || jo.size() != 6) {
      throw new JsonMappingException(jp, "An array of length 6 expected: " + jo.toString());
    }
    for (int i = 0; i < 6; i++) {
      if (!(jo.get(i) instanceof NumericNode)) {
        throw new JsonMappingException(jp, "Numeric values expected: " + jo.toString());
      }
    }
    return new GDAXCandle(
        new Date(((NumericNode) jo.get(0)).asLong() * 1000),
        new BigDecimal(((NumericNode) jo.get(3)).asText()),
        new BigDecimal(((NumericNode) jo.get(2)).asText()),
        new BigDecimal(((NumericNode) jo.get(1)).asText()),
        new BigDecimal(((NumericNode) jo.get(4)).asText()),
        new BigDecimal(((NumericNode) jo.get(5)).asText()));
  }
}
