package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapCurrencyData;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapStatus;

public final class CoinMarketCapCurrencyResponse {

  private final CoinMarketCapCurrencyData currencyData;
  private final CoinMarketCapStatus status;

  public CoinMarketCapCurrencyResponse(
      @JsonProperty("data")
          @JsonDeserialize(
              using = CoinMarketCapCurrencyData.CoinMarketCapCurrencyDataDeserializer.class)
          CoinMarketCapCurrencyData currencyData,
      @JsonProperty("status") CoinMarketCapStatus status) {
    this.currencyData = currencyData;
    this.status = status;
  }

  public CoinMarketCapCurrencyData getCurrencyData() {
    return currencyData;
  }

  public CoinMarketCapStatus getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "CoinMarketCapCurrencyResponse{"
        + "currencyData="
        + currencyData
        + ", status="
        + status
        + '}';
  }
  //
  //    static class CoinMarketCapCurrencyResponseDeserializer
  //            extends JsonDeserializer<CoinMarketCapCurrencyResponse> {
  //
  //        @Override
  //        public CoinMarketCapCurrencyResponse deserialize(JsonParser jp, DeserializationContext
  // ctxt)
  //                throws IOException {
  //
  //
  //            JsonNode jsonNode = jp.getCodec().readTree(jp);
  //            JsonNode statusNode = jsonNode.get("status");
  //            ObjectMapper mapper = new ObjectMapper();
  //            CoinMarketCapStatus currencyStatus = mapper.readValue(statusNode.asText(),
  // CoinMarketCapStatus.class);
  //
  //
  //            JsonNode dataNode = jsonNode.get("data");
  //            CoinMarketCapCurrencyData currencyData =
  //
  // CoinMarketCapCurrencyData.CoinMarketCapCurrencyDataDeserializer.deserializeFromNode(dataNode);
  //
  //            return new CoinMarketCapCurrencyResponse(currencyData, currencyStatus);
  //        }
  //    }
}
