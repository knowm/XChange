package org.knowm.xchange.kraken.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class KrakenAssetPair {

  @JsonProperty("altname")
  String altName;

  @JsonProperty("wsname")
  String wsName;

  @JsonProperty("aclass_base")
  String classBase;

  String base;

  @JsonProperty("aclass_quote")
  String classQuote;

  String quote;

  @JsonProperty("lot")
  String volumeLotSize;

  @JsonProperty("pair_decimals")
  int pairScale;

  @JsonProperty("lot_decimals")
  int volumeLotScale;

  @JsonProperty("lot_multiplier")
  BigDecimal volumeMultiplier;

  List<String> leverage_buy;
  List<String> leverage_sell;
  List<KrakenFee> fees;
  List<KrakenFee> fees_maker;

  @JsonProperty("fee_volume_currency")
  String feeVolumeCurrency;

  @JsonProperty("margin_call")
  BigDecimal marginCall;

  @JsonProperty("margin_stop")
  BigDecimal marginStop;

  @JsonProperty("ordermin")
  BigDecimal orderMin;
}
