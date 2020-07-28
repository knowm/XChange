package org.knowm.xchange.bitcoinde.v4.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Value;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeOrderRequirements;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeOrderState;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeMyOrder {

  String orderId;
  CurrencyPair tradingPair;
  Boolean externalWalletOrder;
  BitcoindeType type;
  BigDecimal maxAmount;
  BigDecimal minAmount;
  BigDecimal price;
  BigDecimal maxVolume;
  BigDecimal minVolume;
  Date endDatetime;
  Boolean newOrderForRemainingAmount;
  BitcoindeOrderState state;
  BitcoindeOrderRequirements orderRequirements;
  Date createdAt;

  @JsonCreator
  public BitcoindeMyOrder(
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
      @JsonProperty("end_datetime") Date endDatetime,
      @JsonProperty("new_order_for_remaining_amount") Boolean newOrderForRemainingAmount,
      @JsonProperty("state") BitcoindeOrderState state,
      @JsonProperty("order_requirements") BitcoindeOrderRequirements orderRequirements,
      @JsonProperty("created_at") Date createdAt) {
    this.orderId = orderId;
    this.tradingPair = tradingPair;
    this.externalWalletOrder = externalWalletOrder;
    this.type = type;
    this.maxAmount = maxAmount;
    this.minAmount = minAmount;
    this.price = price;
    this.maxVolume = maxVolume;
    this.minVolume = minVolume;
    this.endDatetime = endDatetime;
    this.newOrderForRemainingAmount = newOrderForRemainingAmount;
    this.state = state;
    this.orderRequirements = orderRequirements;
    this.createdAt = createdAt;
  }
}
