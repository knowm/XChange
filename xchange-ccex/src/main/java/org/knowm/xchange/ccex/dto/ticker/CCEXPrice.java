package org.knowm.xchange.ccex.dto.ticker;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.ccex.dto.ticker.CCEXPrice.CCEXPriceDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CCEXPriceDeserializer.class)
public class CCEXPrice {

  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal avg;
  private final BigDecimal lastbuy;
  private final BigDecimal lastsell;
  private final BigDecimal buy;
  private final BigDecimal sell;
  private final BigDecimal lastprice;
  private final int updated;

  private CCEXPrice(BigDecimal high, BigDecimal low, BigDecimal avg, BigDecimal lastbuy, BigDecimal lastsell, BigDecimal buy, BigDecimal sell,
      BigDecimal lastprice, int updated) {

    this.high = high;
    this.low = low;
    this.avg = avg;
    this.lastbuy = lastbuy;
    this.lastsell = lastsell;
    this.buy = buy;
    this.sell = sell;
    this.lastprice = lastprice;
    this.updated = updated;

  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getAvg() {
    return avg;
  }

  public BigDecimal getLastbuy() {
    return lastbuy;
  }

  public BigDecimal getLastsell() {
    return lastsell;
  }

  public BigDecimal getBuy() {
    return buy;
  }

  public BigDecimal getSell() {
    return sell;
  }

  public BigDecimal getLastprice() {
    return lastprice;
  }

  public int getUpdated() {
    return updated;
  }

  @Override
  public String toString() {
    return "CCEXPrice [high=" + high + ", low=" + low + ", avg=" + avg + ", lastbuy=" + lastbuy + ", lastsell=" + lastsell + ", buy=" + buy
        + ", sell=" + sell + ", lastprice=" + lastprice + ", updated=" + updated + "]";
  }

  static class CCEXPriceDeserializer extends JsonDeserializer<CCEXPrice> {

    private static BigDecimal getNumberIfPresent(JsonNode numberNode) {

      final String numberString = numberNode.asText();
      return numberString.isEmpty() ? null : new BigDecimal(numberString);
    }

    public static CCEXPrice deserializeFromNode(JsonNode tickerNode) {

      final BigDecimal high = getNumberIfPresent(tickerNode.path("high"));
      final BigDecimal low = getNumberIfPresent(tickerNode.path("low"));
      final BigDecimal avg = getNumberIfPresent(tickerNode.path("avg"));
      final BigDecimal lastbuy = getNumberIfPresent(tickerNode.path("lastbuy"));
      final BigDecimal lastsell = getNumberIfPresent(tickerNode.path("lastsell"));
      final BigDecimal buy = getNumberIfPresent(tickerNode.path("buy"));
      final BigDecimal sell = getNumberIfPresent(tickerNode.path("sell"));
      final BigDecimal lastprice = getNumberIfPresent(tickerNode.path("lastprice"));
      final int updated = tickerNode.path("updated").asInt();

      return new CCEXPrice(high, low, avg, lastbuy, lastsell, buy, sell, lastprice, updated);
    }

    @Override
    public CCEXPrice deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jp.getCodec();
      final JsonNode tickerNode = oc.readTree(jp);

      return deserializeFromNode(tickerNode);
    }

  }
}
