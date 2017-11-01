package org.knowm.xchange.coinmarketcap.dto.marketdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author allenday
 */
@JsonDeserialize(using = CoinMarketCapTicker.CoinMarketCapTickerDeserializer.class)
public final class CoinMarketCapTicker {

  private final String id;
  private final String name;
  private final String isoCode;
  private final BigDecimal priceUSD;
  private final BigDecimal priceBTC;
  private final BigDecimal volume24hUSD;
  private final BigDecimal marketCapUSD;
  private final BigDecimal availableSupply;
  private final BigDecimal totalSupply;
  private final BigDecimal pctChange1h;
  private final BigDecimal pctChange24h;
  private final BigDecimal pctChange7d;
  private final Date lastUpdated;
  private final CoinMarketCapCurrency baseCurrency;

  private CoinMarketCapTicker(
      final String id,
      final String name,
      final String isoCode,
      final BigDecimal priceUSD,
      final BigDecimal priceBTC,
      final BigDecimal volume24hUSD,
      final BigDecimal marketCapUSD,
      final BigDecimal availableSupply,
      final BigDecimal totalSupply,
      final BigDecimal pctChange1h,
      final BigDecimal pctChange24h,
      final BigDecimal pctChange7d,
      final Date lastUpdated
  ) {
    this.id = id;
    this.name = name;
    this.baseCurrency = new CoinMarketCapCurrency(isoCode);
    this.isoCode = isoCode;
    this.priceUSD = priceUSD;
    this.priceBTC = priceBTC;
    this.volume24hUSD = volume24hUSD;
    this.marketCapUSD = marketCapUSD;
    this.availableSupply = availableSupply;
    this.totalSupply = totalSupply;
    this.pctChange1h = pctChange1h;
    this.pctChange24h = pctChange24h;
    this.pctChange7d = pctChange7d;
    this.lastUpdated = lastUpdated;
  }

  public String getID() { return id; }
  public String getName() { return name; }
  public CoinMarketCapCurrency getBaseCurrency() { return baseCurrency; }
  public String getIsoCode() { return isoCode; }
  public BigDecimal getPriceUSD() { return priceUSD; }
  public BigDecimal getPriceBTC() { return priceBTC; }
  public BigDecimal getVolume24hUSD() { return volume24hUSD; }
  public BigDecimal getMarketCapUSD() { return marketCapUSD; }
  public BigDecimal getAvailableSupply() { return availableSupply; }
  public BigDecimal getTotalSupply() { return totalSupply; }
  public BigDecimal getPctChange1h() { return pctChange1h; }
  public BigDecimal getPctChange24h() { return pctChange24h; }
  public BigDecimal getPctChange7d() { return pctChange7d; }
  public Date getLastUpdated() { return lastUpdated; }

  @Override
  public String toString() {

    return "CoinMarketCapCurrency [name=" + name + ", isoCode=" + isoCode + "]";
  }

  static class CoinMarketCapTickerDeserializer extends JsonDeserializer<CoinMarketCapTicker> {

    @Override
    public CoinMarketCapTicker deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);

      if (node.isObject()) {
        String id = node.get("id").asText();
        String name = node.get("name").asText();
        String symbol = node.get("symbol").asText();
        Date lastUpdated = new Date(node.get("last_updated").asLong()*1000);
        BigDecimal rank = new BigDecimal(node.get("rank").asInt());
        BigDecimal priceUSD = new BigDecimal(node.get("price_usd").asDouble());
        BigDecimal priceBTC = new BigDecimal(node.get("price_btc").asDouble());
        BigDecimal volume24hUSD = new BigDecimal(node.get("24h_volume_usd").asDouble());
        BigDecimal marketCapUSD = new BigDecimal(node.get("market_cap_usd").asDouble());
        BigDecimal availableSupply = new BigDecimal(node.get("available_supply").asDouble());
        BigDecimal totalSupply = new BigDecimal(node.get("total_supply").asDouble());

        //TODO use these to create CoinMarketCapHistoricalSpotPrice instances
        BigDecimal pctChange1h = new BigDecimal(node.get("percent_change_1h").asDouble());
        BigDecimal pctChange24h = new BigDecimal(node.get("percent_change_24h").asDouble());
        BigDecimal pctChange7d = new BigDecimal(node.get("percent_change_7d").asDouble());

        CoinMarketCapTicker ticker = new CoinMarketCapTicker(
            id, name, symbol, priceUSD, priceBTC, volume24hUSD, marketCapUSD,
            availableSupply, totalSupply,
            pctChange1h, pctChange24h, pctChange7d, lastUpdated);
        return ticker;
      }
      return null;
    }
  }
}
