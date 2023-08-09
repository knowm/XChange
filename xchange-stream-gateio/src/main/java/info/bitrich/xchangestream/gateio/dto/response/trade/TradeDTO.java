package info.bitrich.xchangestream.gateio.dto.response.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.bitrich.xchangestream.gateio.config.StringToCurrencyPairConverter;
import info.bitrich.xchangestream.gateio.config.TimestampSecondsToInstantConverter;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;
import org.knowm.xchange.currency.CurrencyPair;

@Data
public class TradeDTO {

  @JsonProperty("id")
  Long id;

  @JsonProperty("create_time")
  @JsonDeserialize(converter = TimestampSecondsToInstantConverter.class)
  private Instant time;

  @JsonProperty("create_time_ms")
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
