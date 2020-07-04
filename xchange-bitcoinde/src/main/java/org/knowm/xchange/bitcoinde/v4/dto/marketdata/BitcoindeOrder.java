package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import lombok.Value;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeOrderRequirements;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeTradingPartnerInformation;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeOrder {

  String orderId;
  CurrencyPair tradingPair;
  Boolean externalWalletOrder;
  BitcoindeType type;
  BigDecimal maxAmount;
  BigDecimal minAmount;
  BigDecimal price;
  BigDecimal maxVolume;
  BigDecimal minVolume;
  Boolean requirementsFullfilled;
  BitcoindeTradingPartnerInformation tradingPartnerInformation;
  BitcoindeOrderRequirements orderRequirements;

  @JsonCreator
  public BitcoindeOrder(
      @JsonProperty("order_id") String orderId,
      @JsonProperty("trading_pair") @JsonDeserialize(using = CurrencyPairDeserializer.class)
          CurrencyPair tradingPair,
      @JsonProperty("is_external_wallet_order") Boolean externalWalletOrder,
      @JsonProperty("type") BitcoindeType type,
      @JsonProperty("max_amount_currency_to_trade") BigDecimal maxAmount,
      @JsonProperty("min_amount_currency_to_trade") BigDecimal minAmount,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("max_volume_currency_to_pay") BigDecimal maxVolume,
      @JsonProperty("min_volume_currency_to_pay") BigDecimal minVolume,
      @JsonProperty("order_requirements_fullfilled") Boolean requirementsFullfilled,
      @JsonProperty("trading_partner_information")
          BitcoindeTradingPartnerInformation tradingPartnerInformation,
      @JsonProperty("order_requirements") BitcoindeOrderRequirements orderRequirements) {
    this.orderId = orderId;
    this.tradingPair = tradingPair;
    this.externalWalletOrder = externalWalletOrder;
    this.type = type;
    this.maxAmount = maxAmount;
    this.minAmount = minAmount;
    this.price = price;
    this.maxVolume = maxVolume;
    this.minVolume = minVolume;
    this.requirementsFullfilled = requirementsFullfilled;
    this.tradingPartnerInformation = tradingPartnerInformation;
    this.orderRequirements = orderRequirements;
  }
}
