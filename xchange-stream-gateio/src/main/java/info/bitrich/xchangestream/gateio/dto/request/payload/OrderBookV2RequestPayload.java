package info.bitrich.xchangestream.gateio.dto.request.payload;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.instrument.Instrument;

@Data
@Builder
@Jacksonized
public class OrderBookV2RequestPayload {

  private Instrument instrument;

  private Integer orderBookLevel;

  @JsonValue
  public String[] toPayload() {
    CurrencyPair currencyPair =
        instrument instanceof FuturesContract
            ? ((FuturesContract) instrument).getCurrencyPair()
            : (CurrencyPair) instrument;

    return new String[]{
        String.format(
            "ob.%s_%s.%s",
            currencyPair.getBase().getCurrencyCode(),
            currencyPair.getCounter().getCurrencyCode(),
            orderBookLevel)
    };
  }
}
