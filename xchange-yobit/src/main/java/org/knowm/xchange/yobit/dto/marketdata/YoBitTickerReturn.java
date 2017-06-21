package org.knowm.xchange.yobit.dto.marketdata;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = YoBitTickerDeserializer.class)
public class YoBitTickerReturn {

  private YoBitTicker ticker;

  public YoBitTickerReturn(YoBitTicker ticker) {
    super();
    this.ticker = ticker;
  }

  public YoBitTicker getTicker() {
    return ticker;
  }
}

class YoBitTickerDeserializer extends JsonDeserializer<YoBitTickerReturn> {

  private YoBitTicker ticker;

  @Override
  public YoBitTickerReturn deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    
    JsonNode node = p.readValueAsTree();
    ticker = null;

    if (node.isObject()) {
      Iterator<Entry<String, JsonNode>> priceEntryIter = node.fields();
      while (priceEntryIter.hasNext()) {
        Entry<String, JsonNode> priceEntryNode = priceEntryIter.next();
        JsonNode jsonNode = priceEntryNode.getValue();
        ObjectReader jsonObjectReader = new ObjectMapper().readerFor(YoBitTicker.class);
        ticker = jsonObjectReader.readValue(jsonNode);
      }
    }

    return new YoBitTickerReturn(ticker);
  }

}
