package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.config.converter.InstrumentToStringConverter;
import org.knowm.xchange.bitmex.config.converter.OrderTypeToStringConverter;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.instrument.Instrument;

@Data
@Builder
@AllArgsConstructor
public class BitmexPlaceOrderParameters {

  @JsonProperty("symbol")
  @JsonSerialize(converter = InstrumentToStringConverter.class)
  private Instrument instrument;

  @JsonProperty("side")
  @JsonSerialize(converter = OrderTypeToStringConverter.class)
  private OrderType side;

  @JsonProperty("orderQty")
  private BigDecimal orderQuantity;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("displayQty")
  private BigDecimal displayQuantity;

  @JsonProperty("stopPx")
  private BigDecimal stopPrice;

  @JsonProperty("clOrdID")
  private String clientOid;

  @JsonProperty("clOrdLinkID")
  private String clientOrderLinkId;

  @JsonProperty("pegOffsetValue")
  private BigDecimal pegOffsetValue;

  @JsonProperty("pegPriceType")
  private BitmexPegPriceType pegPriceType;

  @JsonProperty("ordType")
  private BitmexOrderType orderType;

  @JsonProperty("timeInForce")
  private BitmexTimeInForce timeInForce;

  @Singular
  @JsonProperty("execInst")
  private List<BitmexExecutionInstruction> executionInstructions;

  @JsonProperty("contingencyType")
  private BitmexContingencyType contingencyType;

  @JsonProperty("text")
  private String text;

  @JsonGetter("orderQty")
  public BigDecimal getOrderQuantityScaled() {
    return BitmexAdapters.scaleToExchangeAmount(orderQuantity, instrument.getBase());
  }

  @JsonGetter("displayQty")
  public BigDecimal getDisplayQuantityScaled() {
    return BitmexAdapters.scaleToExchangeAmount(displayQuantity, instrument.getBase());
  }


}
