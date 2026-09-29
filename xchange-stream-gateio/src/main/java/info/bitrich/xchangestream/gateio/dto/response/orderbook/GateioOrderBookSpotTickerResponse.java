package info.bitrich.xchangestream.gateio.dto.response.orderbook;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.knowm.xchange.gateio.config.converter.StringToCurrencyPairConverter;
import org.knowm.xchange.instrument.Instrument;

@Data
public class GateioOrderBookSpotTickerResponse extends GateioOrderBookTickerResponse {

  @JsonProperty("s")
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  private Instrument contract;

}
