package org.knowm.xchange.yobit.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.yobit.dto.marketdata.YoBitPair.YoBitPairDeserializer;

@JsonDeserialize(using = YoBitPairDeserializer.class)
public class YoBitPair {
  private Integer decimal_places;
  private BigDecimal min_price;
  private BigDecimal max_price;
  private BigDecimal min_amount;
  private BigDecimal min_total;
  private Integer hidden;
  private BigDecimal fee;
  private BigDecimal fee_buyer;
  private BigDecimal fee_seller;

  public YoBitPair(
      Integer decimal_places,
      BigDecimal min_price,
      BigDecimal max_price,
      BigDecimal min_amount,
      BigDecimal min_total,
      Integer hidden,
      BigDecimal fee,
      BigDecimal fee_buyer,
      BigDecimal fee_seller) {
    super();
    this.decimal_places = decimal_places;
    this.min_price = min_price;
    this.max_price = max_price;
    this.min_amount = min_amount;
    this.min_total = min_total;
    this.hidden = hidden;
    this.fee = fee;
    this.fee_buyer = fee_buyer;
    this.fee_seller = fee_seller;
  }

  public Integer getDecimal_places() {
    return decimal_places;
  }

  public BigDecimal getMin_price() {
    return min_price;
  }

  public BigDecimal getMax_price() {
    return max_price;
  }

  public BigDecimal getMin_amount() {
    return min_amount;
  }

  public BigDecimal getMin_total() {
    return min_total;
  }

  public Integer getHidden() {
    return hidden;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public BigDecimal getFee_buyer() {
    return fee_buyer;
  }

  public BigDecimal getFee_seller() {
    return fee_seller;
  }

  @Override
  public String toString() {
    return "YoBitPair [decimal_places="
        + decimal_places
        + ", min_price="
        + min_price
        + ", max_price="
        + max_price
        + ", min_amount="
        + min_amount
        + ", min_total="
        + min_total
        + ", hidden="
        + hidden
        + ", fee="
        + fee
        + ", fee_buyer="
        + fee_buyer
        + ", fee_seller="
        + fee_seller
        + "]";
  }

  static class YoBitPairDeserializer extends JsonDeserializer<YoBitPair> {

    private static BigDecimal getNumberIfPresent(JsonNode numberNode) {

      final String numberString = numberNode.asText();
      return numberString.isEmpty() ? null : new BigDecimal(numberString);
    }

    public static YoBitPair deserializeFromNode(JsonNode tickerNode) {

      final Integer decimal_places = tickerNode.path("decimal_places").asInt();
      final BigDecimal min_price = getNumberIfPresent(tickerNode.path("min_price"));
      final BigDecimal max_price = getNumberIfPresent(tickerNode.path("max_price"));
      final BigDecimal min_amount = getNumberIfPresent(tickerNode.path("min_amount"));
      final BigDecimal min_total = getNumberIfPresent(tickerNode.path("min_total"));
      final Integer hidden = tickerNode.path("hidden").asInt();
      final BigDecimal fee = getNumberIfPresent(tickerNode.path("fee"));
      final BigDecimal fee_buyer = getNumberIfPresent(tickerNode.path("fee_buyer"));
      final BigDecimal fee_seller = getNumberIfPresent(tickerNode.path("fee_seller"));

      return new YoBitPair(
          decimal_places,
          min_price,
          max_price,
          min_amount,
          min_total,
          hidden,
          fee,
          fee_buyer,
          fee_seller);
    }

    @Override
    public YoBitPair deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      final ObjectCodec oc = jp.getCodec();
      final JsonNode tickerNode = oc.readTree(jp);

      return deserializeFromNode(tickerNode);
    }
  }
}
