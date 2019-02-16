package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapStatus;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapTicker;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public final class CoinMarketCapTickerListResponse {

  private final List<CoinMarketCapTicker> tickerData;
  private final CoinMarketCapStatus status;

  public CoinMarketCapTickerListResponse(
      @JsonProperty("data")
          @JsonDeserialize(
              using = CoinMarketCapTickerListDataDeserializer.class)
              List<CoinMarketCapTicker> tickerData,
      @JsonProperty("status") CoinMarketCapStatus status) {
    this.tickerData = tickerData;
    this.status = status;
  }

  public List<CoinMarketCapTicker> getTickerData() {
    return tickerData;
  }

  public CoinMarketCapStatus getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "CoinMarketCapCurrencyInfoResponse{"
        + "tickerData="
        + tickerData
        + ", status="
        + status
        + '}';
  }

  public static class CoinMarketCapTickerListDataDeserializer
          extends JsonDeserializer<List<CoinMarketCapTicker>> {

    @Override
    public List<CoinMarketCapTicker> deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {

      ObjectMapper objectMapper = new ObjectMapper();
      CoinMarketCapTicker[] tickerArray = objectMapper.readValue(jp, CoinMarketCapTicker[].class);
      return Arrays.asList(tickerArray);
    }
  }
}
