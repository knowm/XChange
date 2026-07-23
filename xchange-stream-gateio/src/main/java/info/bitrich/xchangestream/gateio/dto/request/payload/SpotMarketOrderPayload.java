package info.bitrich.xchangestream.gateio.dto.request.payload;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gateio.config.converter.CurrencyPairToStringConverter;

import java.math.BigDecimal;

@Data
@SuperBuilder
@Jacksonized
public class SpotMarketOrderPayload {

  @JsonSerialize(converter = CurrencyPairToStringConverter.class)
  private CurrencyPair currencyPair;
  private String type;
  private String account;
  private String side;
  private BigDecimal amount;
}