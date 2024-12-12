package org.knowm.xchange.bitmex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.bitmex.AbstractHttpResponseAware;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.bitmex.config.converter.StringToOrderTypeConverter;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.instrument.Instrument;

@Data
@Builder
@AllArgsConstructor
public class BitmexPrivateOrder extends AbstractHttpResponseAware {

  @JsonProperty("account")
  private Integer account;

  @JsonProperty("price")
  private BigDecimal originalPrice;

  @JsonProperty("avgPx")
  private BigDecimal averagePrice;

  @JsonProperty("currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency currency;

  @JsonProperty("ordStatus")
  private OrderStatus orderStatus;

  @JsonProperty("ordType")
  private OrderClass orderClass;

  @JsonProperty("orderID")
  private String id;

  @JsonProperty("side")
  @JsonDeserialize(converter = StringToOrderTypeConverter.class)
  private OrderType orderType;

  @JsonProperty("text")
  private String text;

  @JsonProperty("timeInForce")
  private String timeInForce;

  @JsonProperty("transactTime")
  private ZonedDateTime createdAt;

  @JsonProperty("timestamp")
  private ZonedDateTime updatedAt;

  @JsonProperty("workingIndicator")
  private Boolean workingIndicator;

  private BigDecimal cumulativeAmount;
  private BigDecimal originalAmount;
  private BigDecimal notFilledAmount;

  private Instrument instrument;

  @JsonCreator
  public BitmexPrivateOrder(@JsonProperty("cumQty") BigDecimal cumulativeAmount,
      @JsonProperty("orderQty") BigDecimal originalAmount,
      @JsonProperty("leavesQty") BigDecimal notFilledAmount,
      @JsonProperty("symbol") String symbol
  ) {
    this.instrument = BitmexAdapters.toInstrument(symbol);
    this.cumulativeAmount = BitmexAdapters.scaleToLocalAmount(cumulativeAmount, instrument.getBase());
    this.originalAmount = BitmexAdapters.scaleToLocalAmount(originalAmount, instrument.getBase());
    this.notFilledAmount = BitmexAdapters.scaleToLocalAmount(notFilledAmount, instrument.getBase());
  }


  public enum OrderStatus {
    @JsonProperty("New")
    NEW,

    @JsonProperty("PartiallyFilled")
    PARTIALLY_FILLED,

    @JsonProperty("Filled")
    FILLED,

    @JsonProperty("Canceled")
    CANCELED,

    @JsonProperty("Rejected")
    REJECTED,

    @JsonProperty("Replaced")
    REPLACED
  }

  public static enum OrderClass {
    @JsonProperty("Limit")
    LIMIT,

    @JsonProperty("Market")
    MARKET
  }

}
