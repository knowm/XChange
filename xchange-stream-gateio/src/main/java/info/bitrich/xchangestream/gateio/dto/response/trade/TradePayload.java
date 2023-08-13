package info.bitrich.xchangestream.gateio.dto.response.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gateio.config.converter.DoubleToInstantConverter;
import org.knowm.xchange.gateio.config.converter.StringToCurrencyPairConverter;
import org.knowm.xchange.gateio.config.converter.TimestampSecondsToInstantConverter;

@Data
public class TradePayload {

  @JsonProperty("id")
  Long id;

  @JsonProperty("create_time")
  @JsonDeserialize(converter = TimestampSecondsToInstantConverter.class)
  private Instant time;

  @JsonProperty("create_time_ms")
  @JsonDeserialize(converter = DoubleToInstantConverter.class)
  private Instant timeMs;

  @JsonProperty("side")
  private String side;

  @JsonProperty("currency_pair")
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  CurrencyPair currencyPair;

  @JsonProperty("amount")
  BigDecimal amount;

  @JsonProperty("price")
  BigDecimal price;

}
