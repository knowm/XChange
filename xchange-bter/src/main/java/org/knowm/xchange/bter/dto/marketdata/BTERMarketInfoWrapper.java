package org.knowm.xchange.bter.dto.marketdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.knowm.xchange.bter.BTERAdapters;
import org.knowm.xchange.bter.dto.marketdata.BTERMarketInfoWrapper.BTERMarketInfoWrapperDeserializer;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = BTERMarketInfoWrapperDeserializer.class)
public class BTERMarketInfoWrapper {

  private final Map<CurrencyPair, BTERMarketInfo> marketInfoMap;

  private BTERMarketInfoWrapper(Map<CurrencyPair, BTERMarketInfo> marketInfoMap) {

    this.marketInfoMap = marketInfoMap;
  }

  public Map<CurrencyPair, BTERMarketInfo> getMarketInfoMap() {

    return marketInfoMap;
  }

  @Override
  public String toString() {

    return "BTERMarketInfoWrapper [marketInfoMap=" + marketInfoMap + "]";
  }

  public static class BTERMarketInfo {

    private final CurrencyPair currencyPair;
    private final int decimalPlaces;
    private final BigDecimal minAmount;
    private final BigDecimal fee;

    public BTERMarketInfo(CurrencyPair currencyPair, int decimalPlaces, BigDecimal minAmount, BigDecimal fee) {

      this.currencyPair = currencyPair;
      this.decimalPlaces = decimalPlaces;
      this.minAmount = minAmount;
      this.fee = fee;
    }

    public CurrencyPair getCurrencyPair() {

      return currencyPair;
    }

    public int getDecimalPlaces() {

      return decimalPlaces;
    }

    public BigDecimal getMinAmount() {

      return minAmount;
    }

    public BigDecimal getFee() {

      return fee;
    }

    @Override
    public String toString() {

      return "BTERMarketInfo [currencyPair=" + currencyPair + ", decimalPlaces=" + decimalPlaces + ", minAmount=" + minAmount + ", fee=" + fee + "]";
    }

  }

  static class BTERMarketInfoWrapperDeserializer extends JsonDeserializer<BTERMarketInfoWrapper> {

    @Override
    public BTERMarketInfoWrapper deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      Map<CurrencyPair, BTERMarketInfo> marketInfoMap = new HashMap<>();

      ObjectCodec oc = jp.getCodec();
      JsonNode marketsNodeWrapper = oc.readTree(jp);
      JsonNode marketNodeList = marketsNodeWrapper.path("pairs");

      if (marketNodeList.isArray()) {
        for (JsonNode marketNode : marketNodeList) {
          Iterator<Map.Entry<String, JsonNode>> iter = marketNode.fields();
          if (iter.hasNext()) {
            Entry<String, JsonNode> entry = iter.next();
            CurrencyPair currencyPair = BTERAdapters.adaptCurrencyPair(entry.getKey());
            JsonNode marketInfoData = entry.getValue();
            int decimalPlaces = marketInfoData.path("decimal_places").asInt();
            BigDecimal minAmount = new BigDecimal(marketInfoData.path("min_amount").asText());
            BigDecimal fee = new BigDecimal(marketInfoData.path("fee").asText());
            BTERMarketInfo marketInfoObject = new BTERMarketInfo(currencyPair, decimalPlaces, minAmount, fee);

            marketInfoMap.put(currencyPair, marketInfoObject);
          } else {
            throw new ExchangeException("Invalid market info response received from BTER." + marketsNodeWrapper);
          }
        }
      } else {
        throw new ExchangeException("Invalid market info response received from BTER." + marketsNodeWrapper);
      }

      return new BTERMarketInfoWrapper(marketInfoMap);
    }
  }
}
