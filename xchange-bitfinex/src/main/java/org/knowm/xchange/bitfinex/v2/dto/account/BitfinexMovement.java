package org.knowm.xchange.bitfinex.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitfinex.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;

/** https://docs.bitfinex.com/reference#rest-auth-movements */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class BitfinexMovement {
  /* Movement identifier */
  String id;

  /* The symbol of the currency (ex. "BTC") */
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  Currency currency;

  /* String	The extended name of the currency (ex. "BITCOIN") */
  String currencyName;

  Object placeHolder3;
  Object placeHolder4;

  /* Movement started at */
  Instant mtsStarted;

  /* Movement last updated at */
  Instant mtsUpdated;

  Object placeHolder7;
  Object placeHolder8;

  /* Current status */
  String status;

  Object placeHolder10;
  Object placeHolder11;

  /* Amount of funds moved (positive for deposits, negative for withdrawals) */
  BigDecimal amount;

  /* Tx Fees applied */
  BigDecimal fees;

  Object placeHolder14;
  Object placeHolder15;

  /* Destination address */
  String destinationAddress;

  String paymentId;

  Object placeHolder18;
  Object placeHolder19;

  /* Transaction identifier */
  String transactionId;

  String withdrawTransactionNote;
}
