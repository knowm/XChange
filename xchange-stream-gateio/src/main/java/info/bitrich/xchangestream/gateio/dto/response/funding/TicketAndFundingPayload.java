package info.bitrich.xchangestream.gateio.dto.response.funding;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.knowm.xchange.gateio.config.converter.StringToFutureContractConverter;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;

@Data
public class TicketAndFundingPayload {

  @JsonProperty("contract")
  @JsonDeserialize(converter = StringToFutureContractConverter.class)
  private Instrument contract;

  @JsonProperty("last")
  private BigDecimal last;

  @JsonProperty("change_percentage")
  private BigDecimal changePercentage;

  @JsonProperty("funding_rate")
  private BigDecimal fundingRate;

  @JsonProperty("funding_rate_indicative")
  private BigDecimal fundingRateIndicative;

  @JsonProperty("mark_price")
  private BigDecimal markPrice;

  @JsonProperty("index_price")
  private BigDecimal indexPrice;

  @JsonProperty("total_size")
  private BigDecimal totalSize;

  @JsonProperty("volume_24h")
  private BigDecimal volume24h;

  @JsonProperty("quanto_base_rate")
  private BigDecimal quantoBaseRate;

  @JsonProperty("volume_24h_btc")
  private BigDecimal volume24hBtc;

  @JsonProperty("volume_24h_usd")
  private BigDecimal volume24hUsd;

  @JsonProperty("volume_24h_quote")
  private BigDecimal volume24hQuote;

  @JsonProperty("volume_24h_settle")
  private BigDecimal volume24hSettle;

  @JsonProperty("volume_24h_base")
  private BigDecimal volume24hBase;

  @JsonProperty("low_24h")
  private BigDecimal low24h;

  @JsonProperty("high_24h")
  private BigDecimal high24h;
}
