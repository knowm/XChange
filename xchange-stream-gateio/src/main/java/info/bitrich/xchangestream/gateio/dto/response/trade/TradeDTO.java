package info.bitrich.xchangestream.gateio.dto.response.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.config.StringToCurrencyPairConverter;
import info.bitrich.xchangestream.gateio.config.TimestampSecondsToInstantConverter;
import info.bitrich.xchangestream.gateio.dto.response.SuffixedMessage;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;
import org.knowm.xchange.currency.CurrencyPair;

@Data
public class TradeDTO implements SuffixedMessage {

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

  @Override
  public String getSuffix() {
    return currencyPair != null ? Config.CHANNEL_NAME_DELIMITER + currencyPair : "";
  }
}
