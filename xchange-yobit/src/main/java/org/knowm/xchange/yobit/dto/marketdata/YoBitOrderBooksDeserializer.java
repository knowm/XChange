package org.knowm.xchange.yobit.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class YoBitOrderBooksDeserializer extends JsonDeserializer<YoBitOrderBooksReturn> {

  @Override
  public YoBitOrderBooksReturn deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {

    JsonNode node = p.readValueAsTree();
    Map<String, YoBitOrderBook> tickers = new HashMap<>();

    if (node.isObject()) {
      Iterator<Map.Entry<String, JsonNode>> priceEntryIter = node.fields();

      while (priceEntryIter.hasNext()) {
        Map.Entry<String, JsonNode> priceEntryNode = priceEntryIter.next();

        JsonNode jsonNode = priceEntryNode.getValue();
        ObjectReader jsonObjectReader = new ObjectMapper().readerFor(YoBitOrderBook.class);
        YoBitOrderBook orderBook = jsonObjectReader.readValue(jsonNode);
        String ccy = priceEntryNode.getKey();

        tickers.put(ccy, orderBook);
      }
    }

    return new YoBitOrderBooksReturn(tickers);
  }
}
