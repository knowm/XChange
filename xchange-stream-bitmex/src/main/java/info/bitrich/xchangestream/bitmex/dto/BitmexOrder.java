package info.bitrich.xchangestream.bitmex.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.bitmex.config.converter.StringToOrderTypeConverter;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder.OrderStatus;
import org.knowm.xchange.bitmex.dto.trade.BitmexOrderType;
import org.knowm.xchange.bitmex.dto.trade.BitmexTimeInForce;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.instrument.Instrument;

@Data
@Builder
@AllArgsConstructor
public class BitmexOrder {

  @JsonProperty("account")
  private Integer account;

  @JsonProperty("price")
  private BigDecimal originalPrice;

  @JsonProperty("avgPx")
  private BigDecimal averagePrice;

  @JsonProperty("stopPx")
  private BigDecimal stopPrice;

  @JsonProperty("currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency currency;

  @JsonProperty("ordStatus")
  private OrderStatus orderStatus;

  @JsonProperty("ordType")
  private BitmexOrderType bitmexOrderType;

  @JsonProperty("orderID")
  private String orderId;

  @JsonProperty("clOrdID")
  private String clientOid;

  @JsonProperty("side")
  @JsonDeserialize(converter = StringToOrderTypeConverter.class)
  private OrderType orderType;

  @JsonProperty("text")
  private String text;

  @JsonProperty("timeInForce")
  private BitmexTimeInForce timeInForce;

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
  public BitmexOrder(@JsonProperty("cumQty") BigDecimal cumulativeAmount,
      @JsonProperty("orderQty") BigDecimal originalAmount,
      @JsonProperty("leavesQty") BigDecimal notFilledAmount,
      @JsonProperty("symbol") String symbol) {
    this.instrument = BitmexAdapters.toInstrument(symbol);
    this.cumulativeAmount = BitmexAdapters.scaleToLocalAmount(cumulativeAmount, instrument.getBase());
    this.originalAmount = BitmexAdapters.scaleToLocalAmount(originalAmount, instrument.getBase());
    this.notFilledAmount = BitmexAdapters.scaleToLocalAmount(notFilledAmount, instrument.getBase());
  }


}
