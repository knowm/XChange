package info.bitrich.xchangestream.gateio.dto.request.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.Duration;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gateio.config.converter.CurrencyPairToStringConverter;

@Data
@SuperBuilder
@Jacksonized
public class CurrencyPairLevelIntervalPayload {

  @JsonSerialize(converter = CurrencyPairToStringConverter.class)
  private CurrencyPair currencyPair;

  @JsonFormat(shape = Shape.STRING)
  private Integer orderBookLevel;

  private Duration updateSpeed;

  @JsonGetter("updateSpeed")
  public String renderUpdateSpeed() {
    return updateSpeed.toMillis() + "ms";
  }

}
