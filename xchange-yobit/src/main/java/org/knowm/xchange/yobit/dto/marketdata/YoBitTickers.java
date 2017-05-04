package org.knowm.xchange.yobit.dto.marketdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.knowm.xchange.yobit.dto.marketdata.YoBitTickers.YoBitTickersDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = YoBitTickersDeserializer.class)
public class YoBitTickers {

  private List<YoBitTicker> tickers;

  public YoBitTickers(List<YoBitTicker> tickers) {
    super();
    this.tickers = tickers;
  }

  public List<YoBitTicker> getTickers() {
    return tickers;
  }

  static class YoBitTickersDeserializer extends JsonDeserializer<YoBitTickers> {

    private List<YoBitTicker> tickers = new ArrayList<YoBitTicker>();

    @Override
    public YoBitTickers deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      ObjectCodec oc = p.getCodec();
      JsonNode node = oc.readTree(p);

      System.out.println(node);

      if (node.isObject()) {
        Iterator<Entry<String, JsonNode>> priceEntryIter = node.fields();
        while (priceEntryIter.hasNext()) {
          Entry<String, JsonNode> priceEntryNode = priceEntryIter.next();

          JsonNode jsonNode = priceEntryNode.getValue();

          System.out.println(jsonNode);

          ObjectMapper jsonObjectMapper = new ObjectMapper();
          YoBitTicker yoBitTicker = jsonObjectMapper.convertValue(jsonNode, YoBitTicker.class);
          tickers.add(yoBitTicker);
        }
      }

      return new YoBitTickers(tickers);
    }

  }
}
