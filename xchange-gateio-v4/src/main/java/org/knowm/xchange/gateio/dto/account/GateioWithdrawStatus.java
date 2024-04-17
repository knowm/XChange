package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gateio.config.converter.StringToCurrencyConverter;

@Data
@Builder
@Jacksonized
public class GateioWithdrawStatus {

  @JsonProperty("currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  Currency currency;

  @JsonProperty("name")
  String name;

  @JsonProperty("name_cn")
  String nameCN;

  @JsonProperty("deposit")
  BigDecimal depositFee;

  @JsonProperty("withdraw_percent")
  String withdrawPercent;

  @JsonProperty("withdraw_fix")
  BigDecimal withdrawFee;

  @JsonProperty("withdraw_day_limit")
  BigDecimal withdrawDailyLimit;

  @JsonProperty("withdraw_day_limit_remain")
  BigDecimal withdrawDailyLimitLeft;

  @JsonProperty("withdraw_amount_mini")
  BigDecimal minWithdrawAmount;

  @JsonProperty("withdraw_eachtime_limit")
  BigDecimal maxWithdrawAmount;

  @JsonProperty("withdraw_fix_on_chains")
  Map<String, BigDecimal> withdrawFeeByChain;


  /**
   * @return withdraw rate converted from percentage
   */
  public BigDecimal getWithdrawRate() {
    return new BigDecimal(StringUtils.removeEnd(withdrawPercent, "%")).multiply(new BigDecimal("0.01"));
  }

}
