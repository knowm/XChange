package org.knowm.xchange.deribit.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class AccountSummary {

  /** Profit and loss */
  @JsonProperty("total_pl")
  private BigDecimal totalPl;

  /** Session unrealized profit and loss */
  @JsonProperty("session_upl")
  private BigDecimal sessionUpl;

  /** Session realized profit and loss */
  @JsonProperty("session_rpl")
  private BigDecimal sessionRpl;

  /** Session funding */
  @JsonProperty("session_funding")
  private BigDecimal sessionFunding;

  /** true when portfolio margining is enabled for user */
  @JsonProperty("portfolio_margining_enabled")
  private boolean portfolioMarginingEnabled;

  /** Options summary vega */
  @JsonProperty("options_vega")
  private BigDecimal optionsVega;

  /** Options summary theta */
  @JsonProperty("options_theta")
  private BigDecimal optionsTheta;

  /** Options session unrealized profit and Loss */
  @JsonProperty("options_session_upl")
  private BigDecimal optionsSessionUpl;

  /** Options session realized profit and Loss */
  @JsonProperty("options_session_rpl")
  private BigDecimal optionsSessionRpl;

  /** Options profit and Loss */
  @JsonProperty("options_pl")
  private BigDecimal optionsPl;

  /** Options summary gamma */
  @JsonProperty("options_gamma")
  private BigDecimal optionsGamma;

  /** Options summary delta */
  @JsonProperty("options_delta")
  private BigDecimal optionsDelta;

  /** The account's margin balance */
  @JsonProperty("margin_balance")
  private BigDecimal marginBalance;

  /** The maintenance margin. */
  @JsonProperty("maintenance_margin")
  private BigDecimal maintenanceMargin;

  /** The account's initial margin */
  @JsonProperty("initial_margin")
  private BigDecimal initialMargin;

  /** Futures session unrealized profit and Loss */
  @JsonProperty("futures_session_upl")
  private BigDecimal futuresSessionUpl;

  /** Futures session realized profit and Loss */
  @JsonProperty("futures_session_rpl")
  private BigDecimal futuresSessionRpl;

  /** Futures profit and Loss */
  @JsonProperty("futures_pl")
  private BigDecimal futuresPl;

  /** The account's current equity */
  @JsonProperty("equity")
  private BigDecimal equity;

  /** The deposit address for the account (if available) */
  @JsonProperty("deposit_address")
  private String depositAddress;

  /** The sum of position deltas */
  @JsonProperty("delta_total")
  private BigDecimal deltaTotal;

  /** The selected currency */
  @JsonProperty("currency")
  private String currency;

  /** The account's balance */
  @JsonProperty("balance")
  private BigDecimal balance;

  /** The account's available to withdrawal funds */
  @JsonProperty("available_withdrawal_funds")
  private BigDecimal availableWithdrawalFunds;

  /** The account's available funds */
  @JsonProperty("available_funds")
  private BigDecimal availableFunds;

  /** Account name (given by user) (available when parameter extended = true) */
  @JsonProperty("username")
  private String username;

  /** Account type (available when parameter extended = true) */
  @JsonProperty("type")
  private String type;

  /** Whether two factor authentication is enabled (available when parameter extended = true) */
  @JsonProperty("tfa_enabled")
  private Boolean tfaEnabled;

  /** System generated user nickname (available when parameter extended = true) */
  @JsonProperty("system_name")
  private String systemName;

  /** Account id (available when parameter extended = true) */
  @JsonProperty("id")
  private Long id;

  /** User email (available when parameter extended = true) */
  @JsonProperty("email")
  private String email;
}
