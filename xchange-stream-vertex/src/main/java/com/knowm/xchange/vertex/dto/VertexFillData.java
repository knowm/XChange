package com.knowm.xchange.vertex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.knowm.xchange.vertex.NanoSecondsDeserializer;
import static com.knowm.xchange.vertex.dto.VertexModelUtils.convertToDecimal;
import java.math.BigInteger;
import java.time.Instant;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trade;

@Getter
@ToString
public class VertexFillData {


  private final Instant timestamp;
  private final String productId;
  private final String orderId;
  private final String subAccount;

  private final BigInteger filledQty;
  private final BigInteger remainingQty;
  private final BigInteger originalQty;
  private final BigInteger price;
  private final Boolean isBid;
  private final Boolean isTaker;


  public VertexFillData(@JsonProperty("timestamp") @JsonDeserialize(using = NanoSecondsDeserializer.class) Instant timestamp,
                        @JsonProperty("product_id") String productId,
                        @JsonProperty("order_digest") String orderId,
                        @JsonProperty("subaccount") String subAccount,
                        @JsonProperty("filled_qty") BigInteger filledQty,
                        @JsonProperty("remaining_qty") BigInteger remainingQty,
                        @JsonProperty("original_qty") BigInteger originalQty,
                        @JsonProperty("price") BigInteger price,
                        @JsonProperty("") Boolean isBid,
                        @JsonProperty("is_taker") Boolean isTaker) {
    this.timestamp = timestamp;
    this.productId = productId;
    this.orderId = orderId;
    this.subAccount = subAccount;
    this.filledQty = filledQty;
    this.remainingQty = remainingQty;
    this.originalQty = originalQty;
    this.price = price;
    this.isBid = isBid;
    this.isTaker = isTaker;
  }

  public Trade toTrade(CurrencyPair currencyPair) {
    Trade.Builder builder = new Trade.Builder()
        .instrument(currencyPair)
        .price(convertToDecimal(price));
    if (isTaker) {
      builder.takerOrderId(orderId);
    } else {
      builder.makerOrderId(orderId);
    }
    builder.originalAmount(convertToDecimal(filledQty));
    return builder.build();
  }
}
