package info.bitrich.xchangestream.gateio.dto.request.payload;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import info.bitrich.xchangestream.gateio.config.converter.CurrencyPairToStringConverter;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.CurrencyPair;

@Data
@SuperBuilder
@Jacksonized
public class CurrencyPairPayload {

  @JsonSerialize(converter = CurrencyPairToStringConverter.class)
  private CurrencyPair currencyPair;

}
