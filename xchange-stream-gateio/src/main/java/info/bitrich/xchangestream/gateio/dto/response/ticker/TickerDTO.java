package info.bitrich.xchangestream.gateio.dto.response.ticker;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.config.StringToCurrencyPairConverter;
import info.bitrich.xchangestream.gateio.dto.response.SuffixedMessage;
import java.math.BigDecimal;
import lombok.Data;
import org.knowm.xchange.currency.CurrencyPair;

@Data
public class TickerDTO implements SuffixedMessage {

  @JsonProperty("currency_pair")
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  CurrencyPair currencyPair;

  @JsonProperty("last")
  BigDecimal lastPrice;

  @JsonProperty("lowest_ask")
  BigDecimal lowestAsk;

  @JsonProperty("highest_bid")
  BigDecimal highestBid;

  @JsonProperty("change_percentage")
  BigDecimal changePercent24h;

  @JsonProperty("base_volume")
  BigDecimal baseVolume;

  @JsonProperty("quote_volume")
  BigDecimal quoteVolume;

  @JsonProperty("high_24h")
  BigDecimal highPrice24h;

  @JsonProperty("low_24h")
  BigDecimal lowPrice24h;

  @Override
  public String getSuffix() {
    return currencyPair != null ? Config.CHANNEL_NAME_DELIMITER + currencyPair : "";
  }
}
