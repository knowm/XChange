package org.knowm.xchange.bitcoinde.v4.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeError;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeMaintenance;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindePage;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindePagedResponse;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeMyTradesWrapper extends BitcoindePagedResponse {

  List<BitcoindeMyTrade> trades;

  @JsonCreator
  public BitcoindeMyTradesWrapper(
      @JsonProperty("trades") List<BitcoindeMyTrade> trades,
      @JsonProperty("page") BitcoindePage page,
      @JsonProperty("credits") Integer credits,
      @JsonProperty("errors") BitcoindeError[] errors,
      @JsonProperty("maintenance") BitcoindeMaintenance maintenance,
      @JsonProperty("nonce") Long nonce) {
    super(page, credits, errors, maintenance, nonce);
    this.trades = trades;
  }
}
