package info.bitrich.xchangestream.kraken.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.kraken.dto.common.Method;
import info.bitrich.xchangestream.kraken.dto.response.KrakenMessage;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.CurrencyPair;

@Data
@SuperBuilder
@Jacksonized
public class KrakenUnsubscribeMessage extends KrakenMessage {

  @JsonProperty("params")
  private Params params;

  @JsonProperty("method")
  public Method getMethod() {
    return Method.UNSUBSCRIBE;
  }

  @Data
  @Builder
  @Jacksonized
  public static class Params {
    @JsonProperty("channel")
    private String channel;

    @JsonIgnore private CurrencyPair currencyPair;

    @JsonProperty("token")
    private String token;

    @JsonProperty("symbol")
    public List<CurrencyPair> getSymbol() {
      return currencyPair == null ? Collections.emptyList() : List.of(currencyPair);
    }
  }
}
