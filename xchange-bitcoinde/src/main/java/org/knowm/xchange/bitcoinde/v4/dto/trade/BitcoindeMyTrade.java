package org.knowm.xchange.bitcoinde.v4.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Value;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeTradingPartnerInformation;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeMyTrade {

  String tradeId;
  Boolean isExternalWalletTrade;
  CurrencyPair tradingPair;
  BitcoindeType type;
  BigDecimal amountCurrencyToTrade;
  BigDecimal amountCurrencyToTradeAfterFee;
  BigDecimal price;
  BigDecimal volumeCurrencyToPay;
  BigDecimal volumeCurrencyToPayAfterFee;
  BigDecimal feeCurrencyToPay;
  BigDecimal feeCurrencyToTrade;
  String newOrderIdForRemainingAmount;
  State state;
  Boolean isTradeMarkedAsPaid;
  Date tradeMarkedAsPaidAt;
  Rating myRatingForTradingPartner;
  BitcoindeTradingPartnerInformation tradingPartnerInformation;
  Date createdAt;
  Date successfullyFinishedAt;
  Date cancelledAt;
  PaymentMethod payment_method;

  @JsonCreator
  public BitcoindeMyTrade(
      @JsonProperty("trade_id") String tradeId,
      @JsonProperty("is_external_wallet_trade") Boolean isExternalWalletTrade,
      @JsonProperty("trading_pair") @JsonDeserialize(using = CurrencyPairDeserializer.class)
          CurrencyPair tradingPair,
      @JsonProperty("type") BitcoindeType type,
      @JsonProperty("amount_currency_to_trade") BigDecimal amountCurrencyToTrade,
      @JsonProperty("amount_currency_to_trade_after_fee") BigDecimal amountCurrencyToTradeAfterFee,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("volume_currency_to_pay") BigDecimal volumeCurrencyToPay,
      @JsonProperty("volume_currency_to_pay_after_fee") BigDecimal volumeCurrencyToPayAfterFee,
      @JsonProperty("fee_currency_to_pay") BigDecimal feeCurrencyToPay,
      @JsonProperty("fee_currency_to_trade") BigDecimal feeCurrencyToTrade,
      @JsonProperty("new_order_id_for_remaining_amount") String newOrderIdForRemainingAmount,
      @JsonProperty("state") State state,
      @JsonProperty("is_trade_marked_as_paid") Boolean isTradeMarkedAsPaid,
      @JsonProperty("trade_marked_as_paid_at") Date tradeMarkedAsPaidAt,
      @JsonProperty("my_rating_for_trading_partner") Rating myRatingForTradingPartner,
      @JsonProperty("trading_partner_information")
          BitcoindeTradingPartnerInformation tradingPartnerInformation,
      @JsonProperty("created_at") Date createdAt,
      @JsonProperty("successfully_finished_at") Date successfullyFinishedAt,
      @JsonProperty("cancelled_at") Date cancelledAt,
      @JsonProperty("payment_method") PaymentMethod payment_method) {
    this.tradeId = tradeId;
    this.isExternalWalletTrade = isExternalWalletTrade;
    this.tradingPair = tradingPair;
    this.type = type;
    this.amountCurrencyToTrade = amountCurrencyToTrade;
    this.price = price;
    this.volumeCurrencyToPay = volumeCurrencyToPay;
    this.volumeCurrencyToPayAfterFee = volumeCurrencyToPayAfterFee;
    this.amountCurrencyToTradeAfterFee = amountCurrencyToTradeAfterFee;
    this.feeCurrencyToPay = feeCurrencyToPay;
    this.feeCurrencyToTrade = feeCurrencyToTrade;
    this.newOrderIdForRemainingAmount = newOrderIdForRemainingAmount;
    this.state = state;
    this.isTradeMarkedAsPaid = isTradeMarkedAsPaid;
    this.tradeMarkedAsPaidAt = tradeMarkedAsPaidAt;
    this.myRatingForTradingPartner = myRatingForTradingPartner;
    this.tradingPartnerInformation = tradingPartnerInformation;
    this.createdAt = createdAt;
    this.successfullyFinishedAt = successfullyFinishedAt;
    this.cancelledAt = cancelledAt;
    this.payment_method = payment_method;
  }

  public enum State {
    CANCELLED(-1),
    PENDING(0),
    SUCCESSFUL(1);

    private final int value;

    State(int value) {
      this.value = value;
    }

    @JsonValue
    public int getValue() {
      return value;
    }

    @JsonCreator
    public static State fromValue(final int value) {
      for (State state : State.values()) {
        if (value == state.getValue()) {
          return state;
        }
      }

      throw new IllegalArgumentException("Unknown BitcoindeMyTrade.State value: " + value);
    }
  }

  public enum PaymentMethod {
    SEPA(1),
    EXPRESS(2);

    private final int value;

    PaymentMethod(int value) {
      this.value = value;
    }

    @JsonValue
    public int getValue() {
      return value;
    }

    @JsonCreator
    public static PaymentMethod fromValue(final int value) {
      for (PaymentMethod paymentMethod : PaymentMethod.values()) {
        if (value == paymentMethod.getValue()) {
          return paymentMethod;
        }
      }

      throw new IllegalArgumentException("Unknown BitcoindeMyTrade.PaymentMethod value: " + value);
    }
  }

  public enum Rating {
    PENDING("pending"),
    NEGATIVE("negative"),
    NEUTRAL("neutral"),
    POSITIVE("positive");

    private final String value;

    Rating(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }
  }
}
