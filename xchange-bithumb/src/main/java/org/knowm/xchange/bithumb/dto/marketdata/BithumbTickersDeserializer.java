package org.knowm.xchange.bithumb.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class BithumbTickersDeserializer extends JsonDeserializer<BithumbTickersReturn> {

  private final ObjectReader jsonObjectReader =
      new ObjectMapper()
          .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
          .readerFor(BithumbTicker.class);

  @Override
  public BithumbTickersReturn deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {

    JsonNode node = p.readValueAsTree();
    Map<String, BithumbTicker> tickers = new HashMap<>();

    if (node.isObject()) {
      final long date = node.findValue("date").asLong();

      Iterator<Map.Entry<String, JsonNode>> priceEntryIter = node.fields();
      while (priceEntryIter.hasNext()) {
        Map.Entry<String, JsonNode> priceEntryNode = priceEntryIter.next();
        final String key = priceEntryNode.getKey();
        final JsonNode value = priceEntryNode.getValue();

        if (StringUtils.equals(key, "date")) {
          continue;
        }

        try {
          BithumbTicker ticker = jsonObjectReader.readValue(value);
          ticker.setDate(date);
          tickers.put(key, ticker);
        } catch (MismatchedInputException ignore) {
          // ignore
        }
      }
    }

    return new BithumbTickersReturn(tickers);
  }
}
