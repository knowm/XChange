package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.knowm.xchange.bitmex.Bitmex;

public class PlaceOrderCommand {

  @JsonProperty("symbol")
  public final String symbol;

  @Nullable
  @JsonProperty("side")
  public final String side;

  @Nullable
  @JsonProperty("orderQty")
  public final BigDecimal orderQuantity;

  @Nullable
  @JsonProperty("simpleOrderQuantity")
  public final BigDecimal simpleOrderQuantity;

  @Nullable
  @JsonProperty("displayQuantity")
  public final BigDecimal displayQuantity;

  @Nullable
  @JsonProperty("price")
  public final BigDecimal price;

  @Nullable
  @JsonProperty("stopPx")
  public final BigDecimal stopPrice;

  @Nullable
  @JsonProperty("ordType")
  public final String orderType;

  @Nullable
  @JsonProperty("clOrdID")
  public final String clOrdID;

  @Nullable
  @JsonProperty("execInst")
  public final String executionInstructions;

  @Nullable
  @JsonProperty("clOrdLinkID")
  public final String clOrdLinkID;

  @Nullable
  @JsonProperty("contingencyType")
  public final String contingencyType;

  @Nullable
  @JsonProperty("pegOffsetValue")
  public final BigDecimal pegOffsetValue;

  @Nullable
  @JsonProperty("pegPriceType")
  public final String pegPriceType;

  @Nullable
  @JsonProperty("timeInForce")
  public final String timeInForce;

  @Nullable
  @JsonProperty("text")
  public final String text;

  /** See {@link Bitmex#placeOrder}. */
  public PlaceOrderCommand(final BitmexPlaceOrderParameters parameters) {
    this.symbol = parameters.getSymbol();
    this.side = parameters.getSide() != null ? parameters.getSide().getValue() : null;
    this.orderQuantity = parameters.getOrderQuantity();
    this.simpleOrderQuantity = parameters.getSimpleOrderQuantity();
    this.displayQuantity = parameters.getDisplayQuantity();
    this.price = parameters.getPrice();
    this.stopPrice = parameters.getStopPrice();
    this.orderType =
        parameters.getOrderType() != null ? parameters.getOrderType().toApiParameter() : null;
    this.clOrdID = parameters.getClOrdId();
    this.executionInstructions = parameters.getExecutionInstructionsAsParameter();
    this.clOrdLinkID = parameters.getClOrdLinkId();
    this.contingencyType =
        parameters.getContingencyType() != null
            ? parameters.getContingencyType().toApiParameter()
            : null;
    this.pegOffsetValue = parameters.getPegOffsetValue();
    this.pegPriceType =
        parameters.getPegPriceType() != null ? parameters.getPegPriceType().toApiParameter() : null;
    this.timeInForce =
        parameters.getTimeInForce() != null ? parameters.getTimeInForce().toApiParameter() : null;
    this.text = parameters.getText();
  }

  @Override
  public String toString() {
    return "PlaceOrderCommand{"
        + "symbol='"
        + symbol
        + '\''
        + ", side='"
        + side
        + '\''
        + ", orderQuantity="
        + orderQuantity
        + ", simpleOrderQuantity="
        + simpleOrderQuantity
        + ", displayQuantity="
        + displayQuantity
        + ", price="
        + price
        + ", stopPrice="
        + stopPrice
        + ", orderType='"
        + orderType
        + '\''
        + ", clOrdID='"
        + clOrdID
        + '\''
        + ", executionInstructions='"
        + executionInstructions
        + '\''
        + ", clOrdLinkID='"
        + clOrdLinkID
        + '\''
        + ", contingencyType='"
        + contingencyType
        + '\''
        + ", pegOffsetValue="
        + pegOffsetValue
        + ", pegPriceType='"
        + pegPriceType
        + '\''
        + ", timeInForce='"
        + timeInForce
        + '\''
        + ", text='"
        + text
        + '\''
        + '}';
  }
}
