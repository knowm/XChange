package org.knowm.xchange.bitfinex.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
/** see https://docs.bitfinex.com/reference#rest-auth-transfer */
@JsonInclude(Include.NON_NULL)
public class TransferBetweenWalletsRequest {
  /**
   * Select the wallet from which to transfer (exchange, margin, funding (can also use the old
   * labels which are exchange, trading and deposit respectively))
   */
  @NonNull private String from;
  /**
   * Select the wallet from which to transfer (exchange, margin, funding (can also use the old
   * labels which are exchange, trading and deposit respectively))
   */
  @NonNull private String to;
  /** Select the currency that you would like to transfer (USD, UST, BTC, ....) */
  @NonNull private String currency;
  /**
   * Select the currency that you would like to exchange to (USTF0 === USDT for derivatives pairs)
   */
  @JsonProperty("currency_to")
  private String currencyTo;
  /** Select the amount to transfer */
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @NonNull
  private BigDecimal amount;
  /**
   * Allows transfer of funds to a sub- or master-account identified by the associated email
   * address.
   */
  @JsonProperty("email_dst")
  private String emailDestination;
}
