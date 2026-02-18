package info.bitrich.xchangestream.kraken.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.bitrich.xchangestream.kraken.config.converters.StringToCurrencyPairConverter;
import info.bitrich.xchangestream.kraken.config.converters.StringToOrderTypeConverter;
import info.bitrich.xchangestream.kraken.dto.response.KrakenTradeMessage.Payload;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
public class KrakenTradeMessage extends KrakenDataMessage<Payload> {

  @Override
  public String getChannelId() {
    return super.getChannelId() + "_" + getPayload().getCurrencyPair();
  }

  @Data
  @Builder
  @Jacksonized
  public static class Payload {

    @JsonProperty("symbol")
    @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
    private CurrencyPair currencyPair;

    @JsonProperty("side")
    @JsonDeserialize(converter = StringToOrderTypeConverter.class)
    private OrderType orderSide;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("qty")
    private BigDecimal assetAmount;

    @JsonProperty("ord_type")
    private KrakenOrderType orderType;

    @JsonProperty("trade_id")
    private String id;

    @JsonProperty("timestamp")
    private Instant createdAt;
  }

  public enum KrakenOrderType {
    @JsonProperty("limit")
    LIMIT,

    @JsonProperty("market")
    MARKET,
  }
}
