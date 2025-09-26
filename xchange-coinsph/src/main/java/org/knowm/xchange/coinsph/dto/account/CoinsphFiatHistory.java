package org.knowm.xchange.coinsph.dto.account;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CoinsphFiatHistory {
  String externalOrderId;
  String internalOrderId;
  String paymentOrderId;
  String fiatCurrency;
  BigDecimal fiatAmount;
  Integer transactionType;
  String transactionChannel;
  String transactionSubject;
  String transactionChannelName;
  String transactionSubjectName;
  String transactionSubjectType;
  String feeCurrency;
  BigDecimal channelFee;
  BigDecimal platformFee;
  String status;
  String errorCode;
  String errorMessage;
  Instant completedTime;
  String source;
  Instant createdAt;
  OrderExtendedMap orderExtendedMap;
  Boolean dealCancel;

  @Value
  @Builder
  @Jacksonized
  public static class OrderExtendedMap {
    String channelSubject;
    String tfrAcctNo;
    String tfrName;
  }
}
