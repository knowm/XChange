package org.knowm.xchange.huobi.dto.account.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.huobi.dto.HuobiResultV2;
import org.knowm.xchange.huobi.dto.account.HuobiDepositAddress;

public class HuobiDepositAddressV2Result extends HuobiResultV2<HuobiDepositAddress[]> {
  @JsonCreator
  public HuobiDepositAddressV2Result(
      @JsonProperty("code") String code,
      @JsonProperty("message") String message,
      @JsonProperty("data") HuobiDepositAddress[] data) {
    super(code, message, data);
  }
}
