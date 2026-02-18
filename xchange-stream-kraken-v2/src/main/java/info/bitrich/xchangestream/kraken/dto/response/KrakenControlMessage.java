package info.bitrich.xchangestream.kraken.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.bitrich.xchangestream.kraken.config.converters.StringToCurrencyPairConverter;
import info.bitrich.xchangestream.kraken.dto.common.Method;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.CurrencyPair;

@Data
@SuperBuilder
@Jacksonized
public class KrakenControlMessage extends KrakenMessage {

  @JsonProperty("method")
  private Method method;

  @JsonProperty("result")
  private Payload result;

  @JsonProperty("success")
  private Boolean isSuccess;

  @JsonProperty("time_in")
  Instant timeIn;

  @JsonProperty("time_out")
  Instant timeOut;

  @Data
  @Builder
  @Jacksonized
  public static class Payload {
    @JsonProperty("channel")
    private String channel;

    @JsonProperty("snapshot")
    private Boolean needSnapshot;

    @JsonProperty("symbol")
    @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
    private CurrencyPair currencyPair;
  }
}
