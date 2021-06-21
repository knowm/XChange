package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.knowm.xchange.exceptions.ExchangeException;

@JsonDeserialize(using = GateioCoinInfoWrapper.BTERGateioCoinInfoDeserializer.class)
public class GateioCoinInfoWrapper {

  private final Map<String, GateioCoin> coins;

  public GateioCoinInfoWrapper(Map<String, GateioCoin> coins) {
    this.coins = coins;
  }

  public Map<String, GateioCoin> getCoins() {
    return coins;
  }

  @Override
  public String toString() {
    return "GateioCoinInfo{" + "coins=" + coins + '}';
  }

  static class BTERGateioCoinInfoDeserializer extends JsonDeserializer<GateioCoinInfoWrapper> {

    private static final String DELISTED = "delisted";
    private static final String WITHDRAW_DISABLED = "withdraw_disabled";
    private static final String WITHDRAW_DELAYED = "withdraw_delayed";
    private static final String DEPOSIT_DISABLED = "deposit_disabled";
    private static final String TRADE_DISABLED = "trade_disabled";

    @Override
    public GateioCoinInfoWrapper deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException {

      Map<String, GateioCoin> gateioCoinInfoMap = new HashMap<>();

      ObjectCodec oc = jp.getCodec();
      JsonNode coinsNodeWrapper = oc.readTree(jp);
      JsonNode marketNodeList = coinsNodeWrapper.path("coins");

      if (marketNodeList.isArray()) {
        for (JsonNode marketNode : marketNodeList) {
          Iterator<Map.Entry<String, JsonNode>> iter = marketNode.fields();
          if (iter.hasNext()) {
            Map.Entry<String, JsonNode> entry = iter.next();
            String coin = entry.getKey();
            JsonNode marketInfoData = entry.getValue();
            boolean delisted = marketInfoData.path(DELISTED).asInt() == 1;
            boolean withdrawDisabled = marketInfoData.path(WITHDRAW_DISABLED).asInt() == 1;
            boolean withdrawDelayed = marketInfoData.path(WITHDRAW_DELAYED).asInt() == 1;
            boolean depositDisabled = marketInfoData.path(DEPOSIT_DISABLED).asInt() == 1;
            boolean tradeDisabled = marketInfoData.path(TRADE_DISABLED).asInt() == 1;
            GateioCoin gateioCoin =
                new GateioCoin(
                    delisted, withdrawDisabled, withdrawDelayed, depositDisabled, tradeDisabled);
            gateioCoinInfoMap.put(coin, gateioCoin);
          } else {
            throw new ExchangeException(
                "Invalid coin info response received from Gateio." + coinsNodeWrapper);
          }
        }
      } else {
        throw new ExchangeException(
            "Invalid coin info response received from Gateio." + coinsNodeWrapper);
      }

      return new GateioCoinInfoWrapper(gateioCoinInfoMap);
    }
  }
}
