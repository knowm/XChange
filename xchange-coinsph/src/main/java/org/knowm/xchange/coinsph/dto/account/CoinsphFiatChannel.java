package org.knowm.xchange.coinsph.dto.account;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Represents a fiat channel from Coins.ph API Based on GET /openapi/fiat/v1/support-channel
 * endpoint
 */
@Value
@Jacksonized
@Builder
public class CoinsphFiatChannel {
  String id;
  String transactionChannel;
  String transactionChannelName;
  String transactionSubject;
  String transactionSubjectType;
  String transactionSubjectTypeLabel;
  String transactionSubjectName;
  int transactionType;
  String paymentMethod;
  String channelIcon;
  String subjectIcon;
  BigDecimal maximum;
  BigDecimal minimum;
  BigDecimal dailyLimit;
  BigDecimal monthlyLimit;
  BigDecimal annualLimit;
  BigDecimal remainingDailyLimit;
  BigDecimal remainingMonthlyLimit;
  BigDecimal remainingAnnualLimit;
  int precision;
  BigDecimal fee;
  String feeType;
  int status;
  BigDecimal maxWithdrawBalance;
}
