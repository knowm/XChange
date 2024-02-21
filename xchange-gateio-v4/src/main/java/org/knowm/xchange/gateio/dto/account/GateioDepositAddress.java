package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gateio.config.converter.StringToCurrencyConverter;

@Data
@Builder
@Jacksonized
public class GateioDepositAddress {

  @JsonProperty("currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  Currency currency;

  @JsonProperty("multichain_addresses")
  List<MultichainAddress> multichainAddresses;


  @Data
  @Builder
  @Jacksonized
  public static class MultichainAddress {

    @JsonProperty("chain")
    String chain;

    @JsonProperty("address")
    String address;

    @JsonProperty("payment_id")
    String memo;

    @JsonProperty("payment_name")
    String memoType;

    @JsonProperty("obtain_failed")
    Boolean failed;

  }


}
