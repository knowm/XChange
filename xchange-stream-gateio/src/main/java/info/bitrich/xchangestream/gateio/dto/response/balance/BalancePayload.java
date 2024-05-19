package info.bitrich.xchangestream.gateio.dto.response.balance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gateio.config.converter.TimestampSecondsToInstantConverter;

@Data
public class BalancePayload {

  @JsonProperty("timestamp")
  @JsonDeserialize(converter = TimestampSecondsToInstantConverter.class)
  private Instant time;

  @JsonProperty("timestamp_ms")
  private Instant timeMs;

  @JsonProperty("user")
  Long userId;

  @JsonProperty("currency")
  Currency currency;

  @JsonProperty("change")
  BigDecimal change;

  @JsonProperty("total")
  BigDecimal total;

  @JsonProperty("available")
  BigDecimal available;

  @JsonProperty("freeze")
  BigDecimal freeze;

  @JsonProperty("freeze_change")
  BigDecimal freezeChange;


  @JsonProperty("change_type")
  String changeType;

}
