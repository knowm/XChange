package org.knowm.xchange.bitget.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitget.config.converter.StringToBooleanConverter;
import org.knowm.xchange.bitget.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@Jacksonized
public class BitgetCoinDto {

  @JsonProperty("coinId")
  private String coinId;

  @JsonProperty("coin")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency currency;

  @JsonProperty("transfer")
  private Boolean canTransfer;

  @JsonProperty("areaCoin")
  @JsonDeserialize(converter = StringToBooleanConverter.class)
  private Boolean isAreaCoin;

  @JsonProperty("chains")
  private List<BitgetChainDto> chains;
}
