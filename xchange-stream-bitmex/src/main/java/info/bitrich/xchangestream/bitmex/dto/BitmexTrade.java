package info.bitrich.xchangestream.bitmex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.bitrich.xchangestream.bitmex.config.converter.StringToCurrencyPairConverter;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitmex.config.converter.StringToOrderTypeConverter;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;

@Data
@Builder
@Jacksonized
public class BitmexTrade {

  @JsonProperty("trdMatchID")
  private String id;

  @JsonProperty("timestamp")
  private ZonedDateTime timestamp;

  @JsonProperty("symbol")
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  private CurrencyPair currencyPair;

  @JsonProperty("side")
  @JsonDeserialize(converter = StringToOrderTypeConverter.class)
  private OrderType side;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("homeNotional")
  private BigDecimal assetAmount;

  @JsonProperty("foreignNotional")
  private BigDecimal quoteAmount;
}
