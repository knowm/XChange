package org.knowm.xchange.coinmarketcap.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.math.BigDecimal;

public class CoinMarketCapQuote {
  private final BigDecimal price;
  private final BigDecimal volume24h;
  private final BigDecimal marketCap;
  private final BigDecimal pctChange1h;
  private final BigDecimal pctChange24h;
  private final BigDecimal pctChange7d;

  private CoinMarketCapQuote(
      final BigDecimal price,
      final BigDecimal volume24h,
      final BigDecimal marketCap,
      final BigDecimal pctChange1h,
      final BigDecimal pctChange24h,
      final BigDecimal pctChange7d) {

    this.price = price;
    this.volume24h = volume24h;
    this.marketCap = marketCap;
    this.pctChange1h = pctChange1h;
    this.pctChange24h = pctChange24h;
    this.pctChange7d = pctChange7d;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getVolume24h() {
    return volume24h;
  }

  public BigDecimal getMarketCap() {
    return marketCap;
  }

  public BigDecimal getPctChange1h() {
    return pctChange1h;
  }

  public BigDecimal getPctChange24h() {
    return pctChange24h;
  }

  public BigDecimal getPctChange7d() {
    return pctChange7d;
  }

  @Override
  public String toString() {

    return "CoinMarketCapQuote [price="
        + price
        + ", volume24h="
        + volume24h
        + ", marketCap="
        + marketCap
        + "]";
  }

  static class CoinMarketCapQuoteDeserializer extends JsonDeserializer<CoinMarketCapQuote> {

    @Override
    public CoinMarketCapQuote deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);

      if (node.isObject()) {
        BigDecimal price = new BigDecimal(node.get("price").asDouble());
        BigDecimal volume24h = new BigDecimal(node.get("volume_24h").asDouble());
        BigDecimal marketCap = new BigDecimal(node.get("market_cap").asDouble());

        // TODO use these to create CoinMarketCapHistoricalSpotPrice instances
        BigDecimal pctChange1h = new BigDecimal(node.get("percent_change_1h").asDouble());
        BigDecimal pctChange24h = new BigDecimal(node.get("percent_change_24h").asDouble());
        BigDecimal pctChange7d = new BigDecimal(node.get("percent_change_7d").asDouble());

        return new CoinMarketCapQuote(
            price, volume24h, marketCap, pctChange1h, pctChange24h, pctChange7d);
      }
      return null;
    }
  }
}
