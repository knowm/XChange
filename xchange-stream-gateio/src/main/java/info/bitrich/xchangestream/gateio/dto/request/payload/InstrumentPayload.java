package info.bitrich.xchangestream.gateio.dto.request.payload;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.gateio.config.converter.CurrencyPairToStringConverter;
import org.knowm.xchange.instrument.Instrument;

@Data
@SuperBuilder
@Jacksonized
public class InstrumentPayload {
  @JsonSerialize(converter = CurrencyPairToStringConverter.class)
  private Instrument instrument;
}
