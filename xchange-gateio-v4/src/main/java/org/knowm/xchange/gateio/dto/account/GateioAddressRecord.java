package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gateio.config.converter.StringToBooleanConverter;
import org.knowm.xchange.gateio.config.converter.StringToCurrencyConverter;

@Data
@Builder
@Jacksonized
public class GateioAddressRecord {

  @JsonProperty("currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  Currency currency;

  @JsonProperty("chain")
  String chain;

  @JsonProperty("address")
  String address;

  @JsonProperty("name")
  String name;

  @JsonProperty("tag")
  String tag;

  @JsonProperty("verified")
  @JsonDeserialize(converter = StringToBooleanConverter.class)
  Boolean verified;

}
