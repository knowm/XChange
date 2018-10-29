package org.knowm.xchange.bitmex.dto.trade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.bitmex.Bitmex;

/** See {@link Bitmex#placeOrder} */
@SuppressWarnings({"unused", "WeakerAccess"})
public class BitmexPlaceOrderParameters {

  private final String symbol;
  @Nullable private final BigDecimal orderQuantity;
  @Nullable private final BigDecimal simpleOrderQuantity;
  @Nullable private final BigDecimal displayQuantity;
  @Nullable private final BigDecimal price;
  @Nullable private final BigDecimal stopPrice;
  @Nullable private final BitmexSide side;
  @Nullable private final BitmexOrderType orderType;
  @Nullable private final String clOrdId;
  @Nullable private final List<BitmexExecutionInstruction> executionInstructions;
  @Nullable private final String clOrdLinkId;
  @Nullable private final BitmexContingencyType contingencyType;
  @Nullable private final BigDecimal pegOffsetValue;
  @Nullable private final BitmexPegPriceType pegPriceType;
  @Nullable private final BitmexTimeInForce timeInForce;
  @Nullable private final String text;

  public BitmexPlaceOrderParameters(
      @Nonnull String symbol,
      @Nullable BigDecimal orderQuantity,
      @Nullable BigDecimal simpleOrderQuantity,
      @Nullable BigDecimal displayQuantity,
      @Nullable BigDecimal price,
      @Nullable BigDecimal stopPrice,
      @Nullable BitmexSide side,
      @Nullable BitmexOrderType orderType,
      @Nullable String clOrdId,
      @Nullable List<BitmexExecutionInstruction> executionInstructions,
      @Nullable String clOrdLinkId,
      @Nullable BitmexContingencyType contingencyType,
      @Nullable BigDecimal pegOffsetValue,
      @Nullable BitmexPegPriceType pegPriceType,
      @Nullable BitmexTimeInForce timeInForce,
      @Nullable String text) {
    this.symbol = symbol;
    this.orderQuantity = orderQuantity;
    this.simpleOrderQuantity = simpleOrderQuantity;
    this.displayQuantity = displayQuantity;
    this.price = price;
    this.stopPrice = stopPrice;
    this.side = side;
    this.orderType = orderType;
    this.clOrdId = clOrdId;
    this.executionInstructions = executionInstructions;
    this.clOrdLinkId = clOrdLinkId;
    this.contingencyType = contingencyType;
    this.pegOffsetValue = pegOffsetValue;
    this.pegPriceType = pegPriceType;
    this.timeInForce = timeInForce;
    this.text = text;
  }

  @Nonnull
  public String getSymbol() {
    return symbol;
  }

  @Nullable
  public BigDecimal getOrderQuantity() {
    return orderQuantity;
  }

  @Nullable
  public BigDecimal getSimpleOrderQuantity() {
    return simpleOrderQuantity;
  }

  @Nullable
  public BigDecimal getDisplayQuantity() {
    return displayQuantity;
  }

  @Nullable
  public BigDecimal getPrice() {
    return price;
  }

  @Nullable
  public BigDecimal getStopPrice() {
    return stopPrice;
  }

  @Nullable
  public BitmexSide getSide() {
    return side;
  }

  @Nullable
  public BitmexOrderType getOrderType() {
    return orderType;
  }

  @Nullable
  public String getClOrdId() {
    return clOrdId;
  }

  @Nullable
  public List<BitmexExecutionInstruction> getExecutionInstructions() {
    return executionInstructions;
  }

  @Nullable
  public String getExecutionInstructionsAsParameter() {
    return executionInstructions != null ? StringUtils.join(executionInstructions, ", ") : null;
  }

  @Nullable
  public String getClOrdLinkId() {
    return clOrdLinkId;
  }

  @Nullable
  public BitmexContingencyType getContingencyType() {
    return contingencyType;
  }

  @Nullable
  public BigDecimal getPegOffsetValue() {
    return pegOffsetValue;
  }

  @Nullable
  public BitmexPegPriceType getPegPriceType() {
    return pegPriceType;
  }

  @Nullable
  public BitmexTimeInForce getTimeInForce() {
    return timeInForce;
  }

  @Nullable
  public String getText() {
    return text;
  }

  /** See {@link Bitmex#placeOrder} */
  public static class Builder {

    private final String symbol;
    @Nullable private BigDecimal orderQuantity;
    @Nullable private BigDecimal simpleOrderQuantity;
    @Nullable private BigDecimal displayQuantity;
    @Nullable private BigDecimal price;
    @Nullable private BigDecimal stopPrice;
    @Nullable private BitmexSide side;
    @Nullable private BitmexOrderType orderType;
    @Nullable private String clOrdId;
    @Nullable private List<BitmexExecutionInstruction> executionInstructions;
    @Nullable private String clOrdLinkId;
    @Nullable private BitmexContingencyType contingencyType;
    @Nullable private BigDecimal pegOffsetValue;
    @Nullable private BitmexPegPriceType pegPriceType;
    @Nullable private BitmexTimeInForce timeInForce;
    @Nullable private String text;

    public Builder(@Nonnull String symbol) {
      this.symbol = symbol;
    }

    @Nonnull
    public BitmexPlaceOrderParameters build() {
      return new BitmexPlaceOrderParameters(
          symbol,
          orderQuantity,
          simpleOrderQuantity,
          displayQuantity,
          price,
          stopPrice,
          side,
          orderType,
          clOrdId,
          executionInstructions,
          clOrdLinkId,
          contingencyType,
          pegOffsetValue,
          pegPriceType,
          timeInForce,
          text);
    }

    @Nonnull
    public Builder setOrderQuantity(@Nullable BigDecimal orderQuantity) {
      this.orderQuantity = orderQuantity;
      return this;
    }

    @Nonnull
    public Builder setSimpleOrderQuantity(@Nullable BigDecimal simpleOrderQuantity) {
      this.simpleOrderQuantity = simpleOrderQuantity;
      return this;
    }

    @Nonnull
    public Builder setDisplayQuantity(@Nullable BigDecimal displayQuantity) {
      this.displayQuantity = displayQuantity;
      return this;
    }

    @Nonnull
    public Builder setPrice(@Nullable BigDecimal price) {
      this.price = price;
      return this;
    }

    @Nonnull
    public Builder setStopPrice(@Nullable BigDecimal stopPrice) {
      this.stopPrice = stopPrice;
      return this;
    }

    @Nonnull
    public Builder setSide(@Nullable BitmexSide side) {
      this.side = side;
      return this;
    }

    @Nonnull
    public Builder setOrderType(@Nullable BitmexOrderType orderType) {
      this.orderType = orderType;
      return this;
    }

    @Nonnull
    public Builder setClOrdId(@Nullable String clOrdId) {
      this.clOrdId = clOrdId;
      return this;
    }

    @Nonnull
    public Builder setExecutionInstructions(
        @Nullable List<BitmexExecutionInstruction> executionInstructions) {
      this.executionInstructions = executionInstructions;
      return this;
    }

    @Nonnull
    public Builder addExecutionInstruction(@Nullable BitmexExecutionInstruction execInst) {
      if (this.executionInstructions == null) {
        this.executionInstructions = new ArrayList<>();
      }
      this.executionInstructions.add(execInst);
      return this;
    }

    @Nonnull
    public Builder setClOrdLinkId(@Nullable String clOrdLinkId) {
      this.clOrdLinkId = clOrdLinkId;
      return this;
    }

    @Nonnull
    public Builder setContingencyType(@Nullable BitmexContingencyType contingencyType) {
      this.contingencyType = contingencyType;
      return this;
    }

    @Nonnull
    public Builder setPegOffsetValue(@Nullable BigDecimal pegOffsetValue) {
      this.pegOffsetValue = pegOffsetValue;
      return this;
    }

    @Nonnull
    public Builder setPegPriceType(@Nullable BitmexPegPriceType pegPriceType) {
      this.pegPriceType = pegPriceType;
      return this;
    }

    @Nonnull
    public Builder setTimeInForce(@Nullable BitmexTimeInForce timeInForce) {
      this.timeInForce = timeInForce;
      return this;
    }

    @Nonnull
    public Builder setText(@Nullable String text) {
      this.text = text;
      return this;
    }
  }

  @Override
  public String toString() {
    return "BitmexPlaceOrderParameters{"
        + "symbol="
        + symbol
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
        + ", side="
        + side
        + ", orderType="
        + orderType
        + ", clOrdId='"
        + clOrdId
        + "', executionInstructions="
        + StringUtils.join(executionInstructions)
        + ", clOrdLinkId='"
        + clOrdLinkId
        + "', contingencyType="
        + contingencyType
        + ", pegOffsetValue="
        + pegOffsetValue
        + ", pegPriceType="
        + pegPriceType
        + ", timeInForce='"
        + timeInForce
        + ", text='"
        + text
        + "'}";
  }
}
