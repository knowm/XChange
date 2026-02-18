package info.bitrich.xchangestream.kraken.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.bitrich.xchangestream.kraken.config.converters.StringToCurrencyConverter;
import info.bitrich.xchangestream.kraken.dto.response.KrakenBalancesMessage.Payload;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
public class KrakenBalancesMessage extends KrakenDataMessage<Payload> {

  @Data
  @Builder
  @Jacksonized
  public static class Payload {

    @JsonProperty("asset")
    @JsonDeserialize(converter = StringToCurrencyConverter.class)
    private Currency currency;

    @JsonProperty("asset_class")
    private String assetClass;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("balance")
    private BigDecimal balance;

    @JsonProperty("ledger_id")
    private String ledgerId;

    @JsonProperty("ref_id")
    private String refId;

    @JsonProperty("timestamp")
    private Instant timestamp;

    @JsonProperty("type")
    private String type;

    @JsonProperty("subtype")
    private String subtype;

    @JsonProperty("category")
    private String category;

    @JsonProperty("wallet_type")
    private String walletType;

    @JsonProperty("wallet_id")
    private String walletId;
  }
}
