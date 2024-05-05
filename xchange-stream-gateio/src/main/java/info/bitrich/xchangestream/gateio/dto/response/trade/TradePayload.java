package info.bitrich.xchangestream.gateio.dto.response.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.gateio.config.converter.DoubleToInstantConverter;
import org.knowm.xchange.gateio.config.converter.OrderTypeToStringConverter;
import org.knowm.xchange.gateio.config.converter.StringToCurrencyPairConverter;
import org.knowm.xchange.gateio.config.converter.StringToOrderTypeConverter;
import org.knowm.xchange.gateio.config.converter.TimestampSecondsToInstantConverter;

@Data
public class TradePayload {

  @JsonProperty("id")
  Long id;

  @JsonProperty("create_time")
  @JsonDeserialize(converter = TimestampSecondsToInstantConverter.class)
  Instant time;

  @JsonProperty("create_time_ms")
  @JsonDeserialize(converter = DoubleToInstantConverter.class)
  Instant timeMs;

  @JsonProperty("side")
  @JsonDeserialize(converter = StringToOrderTypeConverter.class)
  @JsonSerialize(converter = OrderTypeToStringConverter.class)
  OrderType side;

  @JsonProperty("currency_pair")
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  CurrencyPair currencyPair;

  @JsonProperty("amount")
  BigDecimal amount;

  @JsonProperty("price")
  BigDecimal price;

}
