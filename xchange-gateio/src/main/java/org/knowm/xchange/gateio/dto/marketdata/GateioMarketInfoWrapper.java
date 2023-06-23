package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Value;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.dto.marketdata.GateioMarketInfoWrapper.BTERMarketInfoWrapperDeserializer;

@JsonDeserialize(using = BTERMarketInfoWrapperDeserializer.class)
public class GateioMarketInfoWrapper {

  private final Map<CurrencyPair, GateioMarketInfo> marketInfoMap;

  private GateioMarketInfoWrapper(Map<CurrencyPair, GateioMarketInfo> marketInfoMap) {

    this.marketInfoMap = marketInfoMap;
  }

  public Map<CurrencyPair, GateioMarketInfo> getMarketInfoMap() {

    return marketInfoMap;
  }

  @Override
  public String toString() {

    return "GateioMarketInfoWrapper [marketInfoMap=" + marketInfoMap + "]";
  }

  @Value
  public static class GateioMarketInfo {

    CurrencyPair currencyPair;
    int decimalPlaces;
    BigDecimal minAmount;
    BigDecimal fee;

  }

  static class BTERMarketInfoWrapperDeserializer extends JsonDeserializer<GateioMarketInfoWrapper> {

    @Override
    public GateioMarketInfoWrapper deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      Map<CurrencyPair, GateioMarketInfo> marketInfoMap = new HashMap<>();

      ObjectCodec oc = jp.getCodec();
      JsonNode marketsNodeWrapper = oc.readTree(jp);
      JsonNode marketNodeList = marketsNodeWrapper.path("pairs");

      if (marketNodeList.isArray()) {
        for (JsonNode marketNode : marketNodeList) {
          Iterator<Map.Entry<String, JsonNode>> iter = marketNode.fields();
          if (iter.hasNext()) {
            Entry<String, JsonNode> entry = iter.next();
            CurrencyPair currencyPair = GateioAdapters.adaptCurrencyPair(entry.getKey());
            JsonNode marketInfoData = entry.getValue();
            int decimalPlaces = marketInfoData.path("decimal_places").asInt();
            BigDecimal minAmount = new BigDecimal(marketInfoData.path("min_amount").asText());
            BigDecimal fee = new BigDecimal(marketInfoData.path("fee").asText());
            GateioMarketInfo marketInfoObject =
                new GateioMarketInfo(currencyPair, decimalPlaces, minAmount, fee);

            marketInfoMap.put(currencyPair, marketInfoObject);
          } else {
            throw new ExchangeException(
                "Invalid market info response received from Gateio." + marketsNodeWrapper);
          }
        }
      } else {
        throw new ExchangeException(
            "Invalid market info response received from Gateio." + marketsNodeWrapper);
      }

      return new GateioMarketInfoWrapper(marketInfoMap);
    }
  }
}
